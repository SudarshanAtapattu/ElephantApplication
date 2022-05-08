package com.newapp.elephantapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.SphericalUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyLocationActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    LocationManager locationManager;

    DatabaseReference db;
    Data_Adapter data_adapter;
    ArrayList<Data_Model> list;
    private GoogleMap mMap;
    double distance;
    Double distanceKm;
    TextView Location, Distance_tv, distanceM;
    private  static  final  int LOCATION_REQUEST = 500;
    private List<LatLng> villageArea;
    Marker currentManrker;

    String address;


    LatLng EleLocation, latLng;

    double f_latitude,f_longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        db = FirebaseDatabase.getInstance().getReference("GPS");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        list = new ArrayList<>();
        data_adapter = new Data_Adapter(this, list);


        Location = findViewById(R.id.tvLocation);


        runtimePermission();

        getLocation();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Data_Model data_model = dataSnapshot.getValue(Data_Model.class);
                    list.add(data_model);
                }
                data_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void initArea() {
        villageArea = new ArrayList<>();
        villageArea.add(new LatLng(7.426537905820147, 80.28138200001929));
        villageArea.add(new LatLng(7.440190092541254, 80.26815938916904));// 7.440190092541254, 80.26815938916904
        villageArea.add(new LatLng(7.437388714623366, 80.27801759096522)); //7.437388714623366, 80.27801759096522

        for (LatLng latLng : villageArea) {
            mMap.addCircle(new CircleOptions().center(latLng)
                    .radius(500) //500m
                    .strokeColor(Color.RED)
                    .fillColor(0x220000FF)
                    .strokeWidth(5.0f)
            );

        }

    }


    private void runtimePermission() {

        try {
            if (ContextCompat.checkSelfPermission(MyLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MyLocationActivity.this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, MyLocationActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLastLocation() {

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                return;
            }
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentLocation = location;

                        Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT);
//                    SupportMapFragment supportMapFragment = (SupportMapFragment)
//                            getSupportFragmentManager().findFragmentById(R.id.MyLocMap);
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.MyLocMap);
                        mapFragment.getMapAsync(MyLocationActivity.this);
                        mapFragment.getMapAsync(MyLocationActivity.this);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        try {
            latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            //MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I m here");
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            //googleMap.addMarker(markerOptions);

            //googleMap.setMapType(googleMap.MAP_TYPE_SATELLITE);
            mMap = googleMap;
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Test");
            ValueEventListener listener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                     f_latitude = snapshot.child("f_latitude").getValue(Double.class);
                     f_longitude = snapshot.child("f_longitude").getValue(Double.class);

                if(currentManrker != null){
                    currentManrker.remove();
                }
                mMap.clear();

                    EleLocation = new LatLng(f_latitude, f_longitude);
                    currentManrker=  mMap.addMarker(new MarkerOptions().position(EleLocation).title("ELE00001").icon(bitmapDescriptorFactory(getApplicationContext(),R.drawable.ele_marker)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(EleLocation));
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    initArea();

                    //startActivity(getIntent());



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        } catch (Exception e) {

            e.printStackTrace();

        }


        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);

    }
    private BitmapDescriptor bitmapDescriptorFactory(Context context , int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return  BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case REQUEST_CODE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, "" + location.getLongitude() + "," + location.getLatitude(), Toast.LENGTH_SHORT).show();
        try {


            Geocoder geocoder = new Geocoder(MyLocationActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);

            Location.setText(address.toString());
           // Toast.makeText(this, "addresses" + address, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Searching location........." , Toast.LENGTH_SHORT).show();


            double Lat = location.getLatitude();
            double Long = location.getLongitude();

            Log.e("lat and Long ", "lat and Long = " + Lat + " " + Long);
            LatLng myLocation = new LatLng(Lat, Long);

            //m
            //double ac = Math.abs(f_latitude - Lat);//
            //double cb = Math.abs(f_longitude - Long);
            // distance = Math.hypot(ac, cb);
            distance = SphericalUtil.computeDistanceBetween(EleLocation, myLocation);


            double roundOffM = Math.round(distance*100)/100.00;

            Log.e("distance", "distance = " + distance);

            distanceM = findViewById(R.id.distanceM_tv);
            distanceM.setText(roundOffM+ "m");



            //Km
            distanceKm = distance / 100;
            //Toast.makeText(this, distanceKm + "km", Toast.LENGTH_SHORT).show();
            Toast.makeText(this,  "Calculating Distance.........", Toast.LENGTH_SHORT).show();
            Log.e("distance", "distance in km  = " + distanceKm);


            double roundOff = Math.round(distanceKm*100)/100.00;
            Distance_tv = findViewById(R.id.distance_tv);
            Distance_tv.setText(roundOff + "Km");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


}