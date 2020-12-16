package com.sanmed.appgatetest.data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkConnection {


    public static String request(String uri) throws IOException {
        Log.v("request","URL: "+uri);
        URL url = new URL(uri);
        StringBuilder response = new StringBuilder();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            conn.disconnect();
        }
        Log.v("request","Response: "+response.toString());
        return response.toString();
    }
}
