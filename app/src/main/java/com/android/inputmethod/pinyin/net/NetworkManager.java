package com.android.inputmethod.pinyin.net;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.android.inputmethod.pinyin.bean.ApkInformationBean;
import com.android.inputmethod.pinyin.greendao.DaoMaster;
import com.android.inputmethod.pinyin.greendao.DaoSession;
import com.android.inputmethod.pinyin.greendao.ScanDao;
import com.android.inputmethod.pinyin.greendao.entity.Scan;
import com.android.inputmethod.pinyin.util.DateUtil;
import com.android.inputmethod.pinyin.util.JsonUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HuangBing on 2017/6/23.
 */

public class NetworkManager {
    private static final String TAG = "PinyinIME_NetworkManager";
    private static Map<String, String> headers = new HashMap<>();


    private static Context mContext;

    private static NetHandler netHandler;

    private static ScanDao scanDao;

    public static NetHandler with(Context context) {
        if (netHandler == null) {
            mContext = context;
            netHandler = new NetHandler();
            FileDownloader.setup(mContext);
        }

        return netHandler;
    }

    public static class NetHandler {

        /**
         * 下载apk
         *
         * @param data
         */
        public void downloadApk(String data) {
            ApkInformationBean informationBean = getUrl(data);
            if (informationBean == null) {
                new Exception("url is null");
            }
            int versionCode = getVersionCode(mContext);
            if (Integer.parseInt(informationBean.getVersion()) > versionCode && versionCode != -1) {
                Log.e(TAG, "downloadApk: =======" + informationBean.getInstall_url() + "      " + versionCode);
                FileDownloader.getImpl().create(informationBean.getInstall_url())
                        .setPath(getOutputPath())
                        .setListener(new FileDownloadListener() {
                            @Override
                            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            }

                            @Override
                            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                            }

                            @Override
                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            }

                            @Override
                            protected void blockComplete(BaseDownloadTask task) {
                            }

                            @Override
                            protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                            }

                            @Override
                            protected void completed(BaseDownloadTask task) {
                                installApk(task.getTargetFilePath());
                            }

                            @Override
                            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            }

                            @Override
                            protected void error(BaseDownloadTask task, Throwable e) {
                            }

                            @Override
                            protected void warn(BaseDownloadTask task) {
                            }
                        }).start();
            }

        }

        //获取安装的url
        private ApkInformationBean getUrl(String data) {
            if (data == null)
                return null;
            return (ApkInformationBean) JsonUtil.getClazzByGson(data, ApkInformationBean.class);
        }

        /**
         * 获取版本号
         *
         * @return 当前应用的版本号
         */
        public int getVersionCode(Context context) {
            int versionCode = -1;

            try {
                PackageManager manager = context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                versionCode = info.versionCode;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return versionCode;
        }

        private void installApk(final String apkPath) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
                    mContext.startActivity(intent);
                }
            }, 1000);

        }
    }


    /*初始化数据库相关*/
    public static void initDbHelp(Context context) {
        try {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "Pinyin-IME.db");
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();
            scanDao = daoSession.getScanDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //apk 路径
    private static String getOutputPath() {
        if (!Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return null;
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "PinyinIME");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        return mediaStorageDir.getPath() + File.separator +
                "pinyin" + timeStamp + ".apk";
    }

    /**
     * 插入数据
     *
     * @param scan
     */
    public static void insertScan(Scan scan) {
        try {
            scanDao.insertOrReplace(scan);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当天单号的条数
     *
     * @return
     */
    public static long getCurrentDayCodeCount() {
        try {
            return scanDao.queryBuilder().where(ScanDao.Properties.Time.gt(DateUtil.getZeroOtherDayMillise(0))).count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 删除3天前的数据
     */
    public static void deleteHistoryData() {
        scanDao.queryBuilder().where(ScanDao.Properties.Time.gt(DateUtil.getZeroOtherDayMillise(-3))).buildDelete();
    }

}
