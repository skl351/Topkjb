package com.top.kjb.bean;

import java.util.ArrayList;

/**
 * Created by oceanzhang on 16/3/24.
 */
public class Result<T> {
    private String flag;
    private T result;
    private ArrayList<String> message;

    public String getFlag() {
        return flag;
    }

    public T getResult() {
        return result;
    }

    public Result(String flag, T result) {
        this.flag = flag;
        this.result = result;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ArrayList<String> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<String> message) {
        this.message = message;
    }
}
