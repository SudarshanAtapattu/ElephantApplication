package com.newapp.elephantapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LiveLocationActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    DatabaseReference db;
    Data_Adapter data_adapter ;
    ArrayList<Data_Model> list ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_location_acivity);

        recyclerView = findViewById(R.id.eleList);
        db = FirebaseDatabase.getInstance().getReference("GPS");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        data_adapter  = new  Data_Adapter(this,list);
        recyclerView.setAdapter(data_adapter);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
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
}