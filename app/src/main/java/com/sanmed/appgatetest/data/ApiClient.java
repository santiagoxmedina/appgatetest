package com.sanmed.appgatetest.data;

import com.sanmed.appgatetest.data.model.DateResponse;

import java.io.IOException;

public class ApiClient implements IApiClient{
    private IDataSerializer mDataSerializer;
    public ApiClient(){
        mDataSerializer = new DataSerializer();
    }


    @Override
    public Result<String> loadDate(double deviceLatitude, double deviceLongitude) throws IOException {
        String jsonResponse = AppGateAPI.getDate(deviceLatitude,deviceLongitude);
        DateResponse response = mDataSerializer.getObject(jsonResponse,DateResponse.class);
        return new Result.Success<String>(response.time);
    }
}
