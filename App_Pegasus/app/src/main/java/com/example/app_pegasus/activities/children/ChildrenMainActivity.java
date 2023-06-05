package com.example.app_pegasus.activities.children;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_pegasus.R;
import com.example.app_pegasus.activities.MainActivity;
import com.example.app_pegasus.activities.RecoverPasswordActivity;
import com.example.app_pegasus.activities.parent.MapParentActivity;
import com.example.app_pegasus.activities.parent.ParentMainActivity;
import com.example.app_pegasus.includes.MyToolbar;
import com.example.app_pegasus.models.Children;
import com.example.app_pegasus.models.Parent;
import com.example.app_pegasus.providers.AuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChildrenMainActivity extends AppCompatActivity {

    Button mButtonGoToMap;
    AuthProvider mAuthProvider;
    String childrenEmail;
    private String parentEmail;
    private String parentId;
    String childrenName;

    DatabaseReference mDatabase;

    TextView mTextChildrenName, mTextChildrenEmail, mTextParentName, mTextParentEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_main);

        MyToolbar.show(this, "Bienvenido a Pegasus", false);

        mAuthProvider = new AuthProvider();

        mDatabase = FirebaseDatabase.getInstance("https://app-pegasus-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users");

        childrenEmail = mAuthProvider.getUserEmail();
        getChildrenData(childrenEmail);
        mTextChildrenEmail = findViewById(R.id.showChildrenEmailChildrenProfile);
        mTextChildrenName = findViewById(R.id.showChildrenNameChildrenProfile);

        mTextParentEmail = findViewById(R.id.showParentEmailChildrenProfile);
        mTextParentName = findViewById(R.id.showParentNameChildrenProfile);

        mButtonGoToMap = findViewById(R.id.btnGoToMap);
        mButtonGoToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildrenMainActivity.this, MapChildrenActivity.class);
                intent.putExtra("parentId", parentId);
                intent.putExtra("childrenName", childrenName);
                startActivity(intent);
            }
        });
    }

            private void getChildrenData(String childrenEmail) {
        Query mQuery = mDatabase.child("Children").orderByChild("email").equalTo(childrenEmail);
        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot nodeShot = dataSnapshot.getChildren().iterator().next();

                    parentEmail = nodeShot.child("parentEmail").getValue(String.class);
                    String childrenId = nodeShot.child("id").getValue(String.class);
                    childrenName = nodeShot.child("name").getValue(String.class);
                    String childrenEmail = nodeShot.child("email").getValue(String.class);

                    Children children = new Children(childrenId, childrenName, childrenEmail);

                    mTextChildrenName.setText(children.getName());
                    mTextChildrenEmail.setText(children.getEmail());
                    getParentData(parentEmail);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getParentData(String parentEmail) {
        Query mQuery = mDatabase.child("Parent").orderByChild("email").equalTo(parentEmail);
        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot nodeShot = dataSnapshot.getChildren().iterator().next();

                    // Obtener los valores individualmente
                    parentId = nodeShot.child("id").getValue(String.class);
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