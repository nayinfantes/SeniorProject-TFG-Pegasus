package com.example.pegasus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mbuttonIamParent;
    Button mbuttonIamSon;

    SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbuttonIamParent = findViewById(R.id.btnIamParent);
        mbuttonIamSon = findViewById(R.id.btnIamSon);

        mPreferences = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();


        mbuttonIamParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("user", "parent");
                editor.apply();
                goToSelectAuth();
            }
        });
        mbuttonIamSon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("user", "child");
                editor.apply();
                goToSelectAuth();
            }
        });
    }

    private void goToSelectAuth() {
        Intent intent = new Intent( MainActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);
    }
}