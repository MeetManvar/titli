package com.example.rickshaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Main2Activity extends AppCompatActivity {
    private  Button Customer_btn,Driver_btn;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Customer_btn = findViewById(R.id.button);
        Driver_btn = findViewById(R.id.button2);


        firebaseAuth = FirebaseAuth.getInstance();
        //progressDialog = new progressDialog(this);
        firebaseListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    Toast.makeText(Main2Activity.this,"You are looged in",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Main2Activity.this,SecondActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Main2Activity.this,"Please Login/SignUp......",Toast.LENGTH_SHORT).show();
                }
            }
        };

        Customer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent1);
            }
        });

        Driver_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }

}
