package com.example.app_pegasus.activities.children;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.app_pegasus.R;
import com.example.app_pegasus.activities.MainActivity;
import com.example.app_pegasus.activities.parent.MapParentActivity;
import com.example.app_pegasus.activities.parent.ParentMainActivity;
import com.example.app_pegasus.includes.MyToolbar;
import com.example.app_pegasus.models.Children;
import com.example.app_pegasus.providers.AuthProvider;

public class ChildrenMainActivity extends AppCompatActivity {

    Button mButtonGoToMap;
    AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_main);

        MyToolbar.show(this, "Bienvenido a Pegasus", false);

        mAuthProvider = new AuthProvider();

        mButtonGoToMap = findViewById(R.id.btnGoToMap);
        mButtonGoToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildrenMainActivity.this, MapChildrenActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.children_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
        }
        if (item.getItemId() == R.id.action_update) {
            Intent intent = new Intent(ChildrenMainActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    void logout() {
        mAuthProvider.logout();
        Intent intent = new Intent(ChildrenMainActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}