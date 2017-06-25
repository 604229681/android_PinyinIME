package com.android.inputmethod.pinyin.net;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.android.inputmethod.pinyin.bean.ApkInformationBean;
import com.android.inputmethod.pinyin.greendao.DaoMaster;
import com.android.inputmethod.pinyin.greendao.DaoSession;
import com.android.inputmethod.pinyin.greendao.ScanDao;
import com.android.inputmethod.pinyin.greendao.entity.Scan;
import com.android.inputmethod.pinyin.util.DateUtil;
import com.android.inputmethod.pinyin.util.JsonUtil;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.download.DownLoadCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HuangBing on 2017/6/23.
 */

public class NetworkManager {
    private static final String TAG = "PinyinIME_NetworkManager";
    private static Map<String, String> headers = new HashMap<>();

    private static Novate novate;

    private static Context mContext;

    private static NetHandler netHandler;

    private static ScanDao scanDao;

    public static NetHandler with(Context context) {
        if (novate == null) {
            mContext = context;
            headers.put("Accept", "application/json");
            novate = new Novate.Builder(context)
                    //.addParameters(parameters)
                    .connectTimeout(20)
                    .writeTimeout(15)
                    .baseUrl("http://download.fir.im/")
                    .addHeader(headers)
                    .addCache(true)
                    .addLog(true)
                    .build();
            netHandler = new NetHandler();
            initDbHelp();
        }

        return netHandler;
    }

    //http://download.fir.im/v2/app/install/594b7071959d691cb400025e?download_token=342152ddc9580a251cbfce09c7eb0d88\u0026source=update

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
                novate.download(informationBean.getUpdate_url(), "pinyinIME.apk", new DownLoadCallBack() {

                    @Override
                    public void onStart(String s) {
                        super.onStart(s);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onProgress(String key, int progress, long fileSizeDownloaded, long totalSize) {
                        super.onProgress(key, progress, fileSizeDownloaded, totalSize);
                        Log.v("test", fileSizeDownloaded + "");

                    }

                    @Override
                    public void onSucess(String key, String path, String name, long fileSize) {
                        Log.e(TAG, key + "   onSucess:   " + path + "  " + fileSize + "    " + name);
                        installApk(path);
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();

                    }

                });
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
    private static void initDbHelp() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "Pinyin-IME.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        scanDao = daoSession.getScanDao();
    }

    /**
     * 插入数据
     *
     * @param scan
     */
    public static void insertScan(Scan scan) {
        scanDao.insert(scan);
    }

    /**
     * 获取当天单号的条数
     *
     * @return
     */
    public static long getCurrentDayCodeCount() {
        return scanDao.queryBuilder().where(ScanDao.Properties.Time.gt(DateUtil.getZeroOtherDayMillise(1))).count();
    }

}
