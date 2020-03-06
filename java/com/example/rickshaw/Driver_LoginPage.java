package com.example.rickshaw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Driver_LoginPage extends AppCompatActivity {

    private EditText Driver_Name;
    private EditText Driver_Password;
    private Button Driver_Login;
    private TextView Driver_userRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__login_page);


        Driver_Name = (EditText)findViewById(R.id.D_name);
        Driver_Password = (EditText)findViewById(R.id.D_password);
        Driver_Login = (Button)findViewById(R.id.D_login);
        //Info = (TextView)findViewById(R.id.textView);
        Driver_userRegistration = (TextView)findViewById(R.id.D_tvregister);

        Driver_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name1 = Driver_Name.getText().toString();
                String Password1 = Driver_Password.getText().toString();
                if(Name1.isEmpty() || Password1.isEmpty() ) {
                    Toast.makeText(Driver_LoginPage.this,"Please Enter All Details",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this,"Please Enter all Deatils:",Toast.LENGTH_SHORT).show();
                }
                else {
                    //validate(Driver_Name.getText().toString(),Driver_Password.getText().toString());
                }
            }
        });
        Driver_userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Driver_LoginPage.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}
