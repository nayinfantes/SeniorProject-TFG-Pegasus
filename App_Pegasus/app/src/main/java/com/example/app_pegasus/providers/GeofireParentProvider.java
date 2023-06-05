package com.example.app_pegasus.providers;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class GeofireParentProvider {
    private DatabaseReference mDatabase;
    private GeoFire mGeofire;
    private AuthProvider mAuthProvider;
    private String location = "location";

    public GeofireParentProvider() {
        mAuthProvider = new AuthProvider();
        String id = mAuthProvider.getId();
        mDatabase = FirebaseDatabase.getInstance("https://app-pegasus-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference().child("Users").child("Parent").child(id);
        mGeofire = new GeoFire(mDatabase.child("location"));
    }

    public void saveLocation(LatLng latLng) {
        DatabaseReference locationRef = mDatabase.child("location");
        locationRef.child("latitude").setValue(latLng.latitude);
        locationRef.child("longitude").setValue(latLng.longitude);
    }

    public void removeLocation(String id) {
        DatabaseReference locationRef = mDatabase.child(id).child("location");
        locationRef.removeValue();
    }
   /* public GeoQuery getChildrenLotacion(LatLng latLng) {
        GeoQuery geoQuery = mGeofire.queryAtLocation(new GeoLocation(latLng.latitude, latLng.longitude), 10000);
        geoQuery.removeAllListeners();
        return geoQuery;
    }
}

     public GeofireParentProvider () {
        mAuthProvider = new AuthProvider();
        String id= mAuthProvider.getId();
        //String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance("https://app-pegasus-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child("Parent").child("Location");
        mGeofire = new GeoFire(mDatabase.child(id));
    }

   // public void saveLocation(String id, LatLng latLng) {
    public void saveLocation(LatLng latLng) {
        String locationKey = mDatabase.push().getKey();
        mGeofire.setLocation(locationKey, new GeoLocation(latLng.latitude, latLng.longitude));
        //mDatabase.child(id).setValue(new GeoLocation(latLng.latitude, latLng.longitude));
    }

   public void removeLocation(String idParent) {
        mGeofire.removeLocation(idParent);
    }*/
}
