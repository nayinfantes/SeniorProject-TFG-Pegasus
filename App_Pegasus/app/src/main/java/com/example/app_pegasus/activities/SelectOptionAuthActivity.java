package com.example.app_pegasus.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.app_pegasus.R;
import com.example.app_pegasus.includes.MyToolbar;

public class SelectOptionAuthActivity extends AppCompatActivity {

    Button mButtonGoToLogin;
    Button mButtonGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);
        MyToolbar.show(this, "Elige una opción", true);

        mButtonGoToLogin = findViewById(R.id.btnGoToLogin);
        mButtonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });

        mButtonGoToRegister = findViewById(R.id.btnGoToRegister);
        mButtonGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });
    }

    public void goToLogin(){
        Intent intent= new Intent(SelectOptionAuthActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void goToRegister(){
        Intent intent= new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}