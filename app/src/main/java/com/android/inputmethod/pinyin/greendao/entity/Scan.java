package com.android.inputmethod.pinyin.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by HuangBing on 2017/6/24.
 */
@Entity
public class Scan {
    @Unique
    private String code;

    private long time;

    private String backOne;

    private String backTwo;

    private String backThree;

    @Generated(hash = 281010337)
    @Keep
    public Scan( String code, long time, String backOne, String backTwo,
                String backThree) {
        this.code = code;
        this.time = time;
        this.backOne = backOne;
        this.backTwo = backTwo;
        this.backThree = backThree;
    }


    @Generated(hash = 997159697)
    public Scan() {
    }


    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBackOne() {
        return this.backOne;
    }

    public void setBackOne(String backOne) {
        this.backOne = backOne;
    }

    public String getBackTwo() {
        return this.backTwo;
    }

    public void setBackTwo(String backTwo) {
        this.backTwo = backTwo;
    }

    public String getBackThree() {
        return this.backThree;
    }

    public void setBackThree(String backThree) {
        this.backThree = backThree;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
