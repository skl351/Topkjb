package com.top.kjb.net;


import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.top.kjb.bean.Result;
import com.top.kjb.bean.ResultException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private Gson gson;
    private Type type;

    public MyGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }


    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        System.out.println("接口相关："+response);
        try {
            Result baseBean = gson.fromJson(response, Result.class);
            Logger.v(baseBean.toString());

            return gson.fromJson(response, type);
        } catch (Exception e) {
         throw e;

        } finally {
            value.close();
        }
    }

}