package com.sanmed.appgatetest.data;

import java.io.IOException;

public interface IApiClient {
    Result<String> loadDate(double deviceLatitude, double deviceLongitude) throws IOException;
}
