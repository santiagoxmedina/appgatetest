package com.sanmed.appgatetest.data;

import com.google.android.gms.maps.model.LatLng;

public interface ILocationDataSource {
     void getDeviceLatLng(IActionObject<Result<LatLng>> callBack);
}
