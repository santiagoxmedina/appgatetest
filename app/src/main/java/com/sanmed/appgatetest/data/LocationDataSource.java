package com.sanmed.appgatetest.data;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.RequiresPermission;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class LocationDataSource implements ILocationDataSource{
    private final FusedLocationProviderClient fusedLocationProviderClient;
    private final UserLocationCallback mUserLocationCallback;
    private IActionObject<Result<LatLng>>  mLocationCallBack;
    private final LocationRequest mLocationRequest;
    public LocationDataSource(Context context) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        mUserLocationCallback = new UserLocationCallback();
        mLocationRequest = createLocationRequest();
    }

    public class UserLocationCallback extends LocationCallback {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                if(location!=null){
                    fusedLocationProviderClient.removeLocationUpdates(this);
                    onGetLocation(location);
                    return;
                }
            }
        }
    }
    protected LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }


    @RequiresPermission(
            anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
    )
    public void getDeviceLatLng(IActionObject<Result<LatLng>> callBack){
        mLocationCallBack = callBack;
        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                mUserLocationCallback,null);
    }

    private void onGetLocation(Location location){
        if(mLocationCallBack!=null){
            Log.v("onGetLocation","lat: "+location.getLatitude()+" lng: "+location.getLongitude());
            mLocationCallBack.onAction(new Result.Success<>(new LatLng(location.getLatitude(),location.getLongitude())));
        }
    }
}
