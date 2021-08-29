package com.example.e_commerce;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class myLocationListener implements LocationListener {
    private Context activitycontext;
    public myLocationListener(Context cont)
    {
        activitycontext=cont;
    }
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(activitycontext,location.getLatitude()+", "+location.getLongitude(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(activitycontext,"GPS enabled",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(activitycontext,"GPS disabled",Toast.LENGTH_LONG).show();
    }
}
