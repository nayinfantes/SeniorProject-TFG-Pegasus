package com.example.app_pegasus.providers;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeofireChildrenProvider {
    private DatabaseReference mDatabase;
    private GeoFire mGeofire;
    private AuthProvider mAuthProvider;
    private String location = "location";

    public GeofireChildrenProvider() {
        mAuthProvider = new AuthProvider();
        String id = mAuthProvider.getId();
        mDatabase = FirebaseDatabase.getInstance("https://app-pegasus-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference().child("Users").child("Children").child(id);
        mGeofire = new GeoFire(mDatabase.child("location"));
    }

    public void saveLocation(LatLng latLng) {
        DatabaseReference locationRef = mDatabase.child("location");
        locationRef.child("latitude").setValue(latLng.latitude);
        locationRef.child("longitude").setValue(latLng.longitude);
    }

}

