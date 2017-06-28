package com.android.inputmethod.pinyin.usb;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by HuangBing on 2017/5/29.
 */
public class SocketServer implements Runnable {
    private static final String TAG = "SocketServer";

    private static final int MSG_TO_CLIENT = 1;

    private static final int MSG_RESTART_THREATH = 0x10;

    //d端口号
    private static final int SERVER_PORT = 10000;

    private ServerSocket mSocketServer = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;

    Handler mMessageHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_TO_CLIENT:
                    try {
                        outputStream.write((msg.obj + "\n").getBytes());
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case MSG_RESTART_THREATH:
                    this.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new Thread(SocketServer.this).start();
                        }
                    }, 5000);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 发送数据
     *
     * @param msg
     */
    public void sendMsg(String msg) {
        try {
            outputStream.write((msg + "\n").getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过handler 发送数据
     *
     * @param msg
     */
    public void sendMsgByHandler(String msg) {
//        Message temp_msg = Message.obtain();
//        temp_msg.what = MSG_TO_CLIENT;
//        temp_msg.obj = msg;
//        mMessageHandler.sendMessage(temp_msg);

        mMessageHandler.obtainMessage(MSG_TO_CLIENT, msg).sendToTarget();
    }

    @Override
    public void run() {
        try {
            closeSocket();
            Log.d(TAG, "start socket server...");
            if (mSocketServer == null) {
                mSocketServer = new ServerSocket(); // <-- create an unbound socket first
                mSocketServer.setReuseAddress(true);
                mSocketServer.bind(new InetSocketAddress(SERVER_PORT)); // <-- now bind it

                Log.d(TAG, "connecting...");
                Socket client = mSocketServer.accept();
                inputStream = client.getInputStream();
                outputStream = client.getOutputStream();
            }

            while (true) {
                String code = readFromSocket();
                if (code == null)
                    break;
                Log.d(TAG, "receive :" + code);
                if (socketCallBack != null) {
                    socketCallBack.onSuccess(code);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (socketCallBack != null) {
                socketCallBack.onFailed(e.getMessage());
            }
        } finally {
            try {
                closeSocket();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mMessageHandler.obtainMessage(MSG_RESTART_THREATH, null).sendToTarget();
        }
    }

    //关闭socket 服务
    public void closeSocket() throws IOException {
        if (outputStream != null) {
            outputStream.close();
            outputStream = null;
        }
        if (inputStream != null) {
            inputStream.close();
            inputStream = null;
        }
        if (mSocketServer != null) {
            mSocketServer.close();
            mSocketServer = null;
        }
    }

    /* 读取命令 */
    private String readFromSocket() throws Exception {
        byte[] buffer = new byte[128];

        if (inputStream == null)
            return null;
        int numBytes = inputStream.read(buffer, 0, buffer.length);

        return DataProtocol.getCheckCode(buffer, numBytes);
    }

    private SocketCallBack socketCallBack;

    public void setSocketCallBack(SocketCallBack socketCallBack) {
        this.socketCallBack = socketCallBack;
    }

    public interface SocketCallBack {
        void onSuccess(String code);

        void onFailed(String msg);
    }
}

