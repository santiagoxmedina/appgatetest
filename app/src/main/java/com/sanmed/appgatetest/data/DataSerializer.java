package com.sanmed.appgatetest.data;

import com.google.gson.Gson;

public class DataSerializer {
    private Gson gson ;

    public DataSerializer(){
        gson = new Gson();
    }

    public <T> T getObject(String data,Class<T> tClass){
        return gson.fromJson(data,tClass);
    }

    public String getData(Object object){
        return gson.toJson(object);
    }
}
