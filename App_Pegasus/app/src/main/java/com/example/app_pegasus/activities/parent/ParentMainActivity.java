package com.example.app_pegasus.activities.parent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.app_pegasus.R;
import com.example.app_pegasus.activities.MainActivity;
import com.example.app_pegasus.includes.MyToolbar;
import com.example.app_pegasus.models.Parent;
import com.example.app_pegasus.providers.AuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParentMainActivity extends AppCompatActivity {

    Button mButtonGoToMap;
    //Button mButtonLogout;
    AuthProvider mAuthProvider;
    String parentEmail;
    DatabaseReference mDatabase;
    TextView mTextParentName, mTextParentEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);

        MyToolbar.show(this, "Bienvenido a Pegasus", false);

        mAuthProvider = new AuthProvider();

        mDatabase = FirebaseDatabase.getInstance("https://app-pegasus-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child("Parent");

        parentEmail = mAuthProvider.getUserEmail();

        mTextParentEmail = findViewById(R.id.showParentEmailParentProfile);
        mTextParentName = findViewById(R.id.showParentNameParentProfile);

        getParentData(parentEmail);

       // makeChildrenList();
       /* mButtonLogout = findViewById(R.id.btnLogout);
        mButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuthProvider.logout();
                Intent intent = new Intent(ParentMainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

       mButtonGoToMap = findViewById(R.id.btnGoToMap);
        mButtonGoToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentMainActivity.this, MapParentActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parent_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
        }
        if (item.getItemId() == R.id.action_update) {
            Intent intent = new Intent(ParentMainActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    void logout() {
        mAuthProvider.logout();
        Intent intent = new Intent(ParentMainActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void getParentData(String parentEmail) {
        Query mQuery = mDatabase.orderByChild("email").equalTo(parentEmail);
        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot nodeShot = dataSnapshot.getChildren().iterator().next();

                    // Obtener los valores individualmente
                    String parentId = nodeShot.child("id").getValue(String.class);
                    String parentName = nodeShot.child("name").getValue(String.class);
                    String parentEmail = nodeShot.child("email").getValue(String.class);

                    Parent parent = new Parent(parentId, parentName, parentEmail);

                    mTextParentName.setText(parent.getName());
                    mTextParentEmail.setText(parent.getEmail());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

   /* public void makeChildrenList(){

    }*/
}