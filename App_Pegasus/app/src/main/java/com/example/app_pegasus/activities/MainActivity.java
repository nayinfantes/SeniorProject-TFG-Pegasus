package com.example.app_pegasus.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.app_pegasus.R;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        mButton = findViewById(R.id.btnGoToSelectMode);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSelectMode();
            }
        });
    }

    public void goToSelectMode(){
        Intent intent= new Intent(MainActivity.this, SelectModeActivity.class);
        startActivity(intent);
    }
}