package com.example.rickshaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistrationActivity extends AppCompatActivity {
    private EditText username,userpassword,useremail,userNumber;
    private Button regbutton;
    private TextView userLogin;
    FirebaseAuth firebaseAuth;
    String name,password,email,Number;
    //private FirebaseAuth firebaseAuth1;
    private FirebaseAuth.AuthStateListener firebaseListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.example.rickshaw.R.layout.activity);

        username=findViewById(com.example.rickshaw.R.id.etusername);
        useremail=findViewById(com.example.rickshaw.R.id.etuseremail);
        userpassword=findViewById(com.example.rickshaw.R.id.userpassword);
        regbutton=findViewById(com.example.rickshaw.R.id.btnRegister);
        userLogin=findViewById(com.example.rickshaw.R.id.tvUserLogin);
        userNumber=findViewById(R.id.etNumber);
        firebaseAuth= FirebaseAuth.getInstance();

        //regbutton.setOnClickListener(new View.OnClickListener() {
        // @Override
        //  public void onClick(View view) {
        //  Intent intent1=new Intent(RegistrationActivity.this,MainActivity.class);
        // startActivity(intent1);


        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //Upload data to database
                    String user_email=useremail.getText().toString().trim();
                    String user_password=userpassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(RegistrationActivity.this, "Registaration Scucessful", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(RegistrationActivity.this,SecondActivity.class));
                                sendEmailVerification();
                            }else
                            {
                                FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                Toast.makeText(RegistrationActivity.this,"Registaration Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                //message.hide();
                                return;
                            }

                        }
                    });
                }
            }
        });
        //}
        //});

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(RegistrationActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }


    private boolean validate(){
        boolean result =false;

        name = username.getText().toString();
        password = userpassword.getText().toString();
        email = useremail.getText().toString().trim();
        Number = userNumber.getText().toString().trim();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty() ||  Number.isEmpty()){
            Toast.makeText(this, "Please Enter All The Details",Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

    private void sendEmailVerification()
    {
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(RegistrationActivity.this,"Sucessfully Register , Verification mail sent",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(RegistrationActivity.this," Verification mail  hasn't been sent!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserData()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference(firebaseAuth.getUid());
        userprofile userprofile1 =new userprofile(name,email,Number);
        myref.setValue(userprofile1);
    }

}


