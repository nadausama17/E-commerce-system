package com.example.e_commerce;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText addressText;
    LocationManager locmanager;
    myLocationListener locListener;
    Button getLocation;
    Button confirm_order;
    public ecommerceDB edb;
    public String useremail;
    public String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        addressText = (EditText) findViewById(R.id.location_edittext);
        getLocation = (Button) findViewById(R.id.getlocation);
        locListener = new myLocationListener(getApplicationContext());
        locmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        confirm_order=(Button)findViewById(R.id.confirm_order);
        edb=new ecommerceDB(getApplicationContext());
        useremail=getIntent().getExtras().getString("useremail");
        date=getIntent().getExtras().getString("date");
        Button backtoShoppingCart=(Button)findViewById(R.id.backto_SC);
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, locListener);
        } catch (SecurityException ex) {
            Toast.makeText(getApplicationContext(), "you are not allowed", Toast.LENGTH_SHORT).show();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(addressText.getText().toString().equals("")))
                {
                    int custid=edb.getcustomerID(useremail);
                    int sc_id=edb.getcustShoppingcart_ID(String.valueOf(custid));
                    edb.make_order(String.valueOf(sc_id),String.valueOf(custid),date,addressText.getText().toString());
                    Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You Should get the location first",Toast.LENGTH_LONG).show();
                }
            }
        });
        backtoShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MapsActivity.this,shoppingCart.class);
                i.putExtra("useremail",useremail);
                startActivity(i);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960, 31.235711600), 8));
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc = null;
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    loc = locmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }catch(SecurityException ex){
                    Toast.makeText(getApplicationContext(),"you didn't allow to access current location",Toast.LENGTH_LONG).show();
                }
                if(loc!=null)
                {
                    LatLng myposition=new LatLng(loc.getLatitude(),loc.getLongitude());
                    try{
                        addressList=coder.getFromLocation(myposition.latitude,myposition.longitude,1);
                        if(!addressList.isEmpty())
                        {
                            String address="";
                            for(int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++)
                                address+=addressList.get(0).getAddressLine(i)+", ";

                            mMap.addMarker(new MarkerOptions().position(myposition).title("My Location").snippet(address)).setDraggable(true);
                            addressText.setText(address);
                        }
                    }catch (IOException e)
                    {
                        mMap.addMarker(new MarkerOptions().position(myposition).title("My location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition,15));
                }else
                {
                    Toast.makeText(getApplicationContext(),"Please wait until your position is determined",Toast.LENGTH_LONG).show();
                }

            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Geocoder coder=new Geocoder(getApplicationContext());
                List<Address> addresslist;
                try {
                    addresslist=coder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                    if(!addresslist.isEmpty())
                    {
                        String address="";
                        for(int i=0;i<addresslist.get(0).getMaxAddressLineIndex();i++)
                            address+=addresslist.get(0).getAddressLine(i)+", ";
                        addressText.setText(address);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"No address for this location",Toast.LENGTH_LONG).show();
                        addressText.getText().clear();
                    }
                }catch (IOException e)
                {
                    Toast.makeText(getApplicationContext(),"Can't get the address, check your network",Toast.LENGTH_LONG).show();
                }
            }
        });
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
}