package com.example.vergencyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);




        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                checkDangNhap();
            }
        },1000);

    }

    private void checkDangNhap() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null){
            Intent intent = new Intent(Welcome.this,MainActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(Welcome.this,DangNhap.class);
            startActivity(intent);
        }

        finish();
    }


}
