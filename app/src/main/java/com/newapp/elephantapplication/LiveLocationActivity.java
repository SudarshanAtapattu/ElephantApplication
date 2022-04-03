package com.newapp.elephantapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.newapp.elephantapplication.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class LiveLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    RecyclerView recyclerView;
    DatabaseReference db;
    Data_Adapter data_adapter;
    ArrayList<Data_Model> list;

    private GoogleMap mMap;
    private ActivityMapsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_location_acivity);

        recyclerView = findViewById(R.id.eleList);
        db = FirebaseDatabase.getInstance().getReference("GPS");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_frag);
        mapFragment.getMapAsync(this);


        list = new ArrayList<>();
        data_adapter = new Data_Adapter(this, list);
        recyclerView.setAdapter(data_adapter);
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Test");

        ValueEventListener listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Double f_latitude = snapshot.child("f_latitude").getValue(Double.class);
                Double f_longitude = snapshot.child("f_longitude").getValue(Double.class);


                LatLng location = new LatLng(f_latitude, f_longitude);


                mMap.addMarker(new MarkerOptions().position(location).title("ELE00001"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}