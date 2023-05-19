package com.example.app_pegasus.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.app_pegasus.R;

public class SelectModeActivity extends AppCompatActivity {

    ImageButton mbuttonIamParent;
    ImageButton mbuttonIamChild;

    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        mbuttonIamParent = findViewById(R.id.imageBtnParent);
        mbuttonIamChild = findViewById(R.id.imageBtnChild);

        mPreferences = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        mbuttonIamParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("user", "parent");
                editor.apply();
                goToSelectAuth();
            }
        });

        mbuttonIamChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("user", "children");
                editor.apply();
                goToSelectAuth();
            }
        });
    }
    private void goToSelectAuth() {
        Intent intent = new Intent( SelectModeActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);
    }

}