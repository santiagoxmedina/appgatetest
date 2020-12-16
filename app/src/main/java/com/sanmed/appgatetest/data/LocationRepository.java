package com.sanmed.appgatetest.data;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.sanmed.appgatetest.data.model.SignInUser;

public class LocationRepository implements ILocationRepository {

    private static LocationRepository instance;
    private final ILocationDataSource mLocationDataSource;
    public LocationRepository(ILocationDataSource dataSource){
        mLocationDataSource = dataSource;

    }

    public static ILocationRepository getInstance(ILocationDataSource dataSource) {
        if (instance == null) {
            instance = new LocationRepository(dataSource);
        }
        return instance;
    }


    @Override
    public void getUserLocation(IActionObject<Result<LatLng>> callBack) {
            mLocationDataSource.getDeviceLatLng(callBack);
    }
}
