package com.example.app_pegasus.providers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class TokenProvider {

    DatabaseReference mDatabase;

    public TokenProvider(){
        mDatabase = FirebaseDatabase.getInstance("https://app-pegasus-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Notifications").child("Tokens");
    }

    public void create(final String idUser){
        if (idUser == null) return;
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult();
                mDatabase.child(idUser).setValue(token);
            }
        });
    }

    public DatabaseReference getToken(String idUser) {
        return mDatabase.child(idUser);
    }
}
