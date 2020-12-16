package com.sanmed.appgatetest.data;

import com.google.gson.Gson;

public interface IDataSerializer {


     <T> T getObject(String data,Class<T> tClass);

     String getData(Object object);
}
