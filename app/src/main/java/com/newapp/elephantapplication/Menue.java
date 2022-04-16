package com.newapp.elephantapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class Menue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menue);

        TextView menu_name;

        final CardView LiveLocation  = findViewById(R.id.LiveLocation);
        final CardView MyLocation  = findViewById(R.id.MyLocation);
        final CardView statAndBehaviour  = findViewById(R.id.statAndBehaviour);
        //final CardView history  = findViewById(R.id.history);
        final CardView MyProfile  = findViewById(R.id.MyProfile);

        menu_name = findViewById(R.id.menu_profile_name);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            menu_name.setText(signInAccount.getDisplayName());
        }

        LiveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menue.this, LiveLocationActivity.class);
                startActivity(intent);
            }
        });

        MyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menue.this, MyLocationActivity.class);
                startActivity(intent);

            }
        });

        statAndBehaviour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menue.this, StatAndBehavioursActivity.class);
                startActivity(intent);

            }
        });
//        history.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Menue.this, HistoryActivity.class);
//                startActivity(intent);
//
//            }
//        });
        MyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menue.this, MyProfileActivity.class);
                startActivity(intent);

            }
        });
    }
}