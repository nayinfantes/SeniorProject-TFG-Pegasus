package com.example.app_pegasus.providers;

import com.example.app_pegasus.models.Children;
import com.example.app_pegasus.models.Parent;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ParentProvider {
    DatabaseReference mDatabase;

    public ParentProvider(){
        mDatabase = FirebaseDatabase.getInstance("https://app-pegasus-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child("Parent");
    }

    public Task<Void> create(Parent parent){
        return mDatabase.child(parent.getId()).setValue(parent);
    }
}
