package com.android.inputmethod.pinyin.usb;

import java.io.UnsupportedEncodingException;

/**
 * Created by HuangBing on 2017/6/15.
 */
public class DataProtocol {

    public static String getCheckCode(byte[] buffer, int len) {
        String scanCode = null;
        if (buffer.length > 6) {
            char crc = 0;
            int startFlag = (int) buffer[5] & 0xFF;
            short codeLen = buffer[6];
            if (startFlag == (char) 0xA1 && len >= codeLen + 6) {
                byte[] codeBuff = new byte[codeLen];
                crc += startFlag;
                crc += codeLen;
                for (int i = 0; i < codeLen; i++) {
                    codeBuff[i] = buffer[7 + i];
                    crc += codeBuff[i];
                }

                if (buffer[codeLen + 7] == (crc & 0xFF)) {
                    try {
                        scanCode = new String(codeBuff, 0, codeBuff.length, "utf-8");
                        System.out.println(scanCode + "=========================");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return scanCode;
    }

}
