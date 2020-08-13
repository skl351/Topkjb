package com.top.kjb.bean;

import java.io.IOException;

public class ResultException extends IOException {
    private String flag;
    private String result;

    public String getFlag() {
        return flag;
    }

    public String getResult() {
        return result;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

