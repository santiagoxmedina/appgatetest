package com.sanmed.appgatetest.data;

import java.io.IOException;

public class AppGateAPI {

    public static String getDate(double deviceLatitude,double deviceLongitude) throws IOException {
        String date_uri = "http://api.geonames.org/timezoneJSON?formatted=true&lat=%1$s&lng=%2$s&username=qa_mobile_easy&style=full";
        String uri = String.format(date_uri,deviceLatitude,deviceLongitude);
        return NetworkConnection.request(uri);
    }
}
