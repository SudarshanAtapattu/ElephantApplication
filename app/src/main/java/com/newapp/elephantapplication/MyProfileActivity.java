package com.newapp.elephantapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyProfileActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    ImageView logout;
    TextView name, email;

    Layout callToo;
    private DatabaseReference databaseReference;
    private static final int REQUEST_CALL = 1;
    private TextView callText;
    private AppCompatButton callTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);

        callText = findViewById(R.id.callText);
        callTo = findViewById(R.id.callTo);



        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            name.setText(signInAccount.getDisplayName());
            email.setText(signInAccount.getEmail());
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.my_default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(MyProfileActivity.this, gso);
        logout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                mAuth.signOut();
                /*
                    when click logout image  go to logout ui
                 */
                Intent mainIntent = new Intent(MyProfileActivity.this, Login.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                finish();
            }
        });


        callTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //CallButton();

            }
        });

    }

    private void CallButton() {
        String number =  callText.getText().toString();
        if (number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(MyProfileActivity.this, Manifest.permission.CALL_PHONE )!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MyProfileActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }
            else {
                String dial = "tel:"+ number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));


            }
        }
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CallButton();


                }
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    //   private  DBProfile(){
//
//       FirebaseDatabase db =  FirebaseDatabase.getInstance();
//       databaseReference = db.getReference();
//   }


}