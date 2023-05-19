package com.example.app_pegasus.providers;

import com.example.app_pegasus.models.Children;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChildrenProvider {

    DatabaseReference mDatabase;

    public ChildrenProvider(){
        mDatabase = FirebaseDatabase.getInstance("https://app-pegasus-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child("Children");
    }

    public Task<Void> create(Children children){
        return mDatabase.child(children.getId()).setValue(children);
    }
}
