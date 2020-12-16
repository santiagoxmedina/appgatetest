package com.sanmed.appgatetest.data;

import com.google.android.gms.maps.model.LatLng;

public interface ILocationRepository {
    void getUserLocation(IActionObject<Result<LatLng>> callBack);
}
