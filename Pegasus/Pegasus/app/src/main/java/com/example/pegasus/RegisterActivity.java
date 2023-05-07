package com.example.pegasus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pegasus.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {


    Toolbar mToolbar;
    SharedPreferences mPreferences;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    Button mButtonRegister;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputPassword;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Introduce tus datos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();



        mPreferences = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        mButtonRegister = findViewById(R.id.btnRegister);
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputName = findViewById(R.id.textInputName);
        mTextInputPassword = findViewById(R.id.textInputPassword);

        mDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Espere por favor").build();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    void registerUser(){

        final String name = mTextInputName.getText().toString();
        final String email = mTextInputEmail.getText().toString();
        final String password = mTextInputPassword.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){

            if(password.length() >= 6){

                mDialog.show();

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        mDialog.hide();
                        if(task.isSuccessful()){

                            String id = mAuth.getCurrentUser().getUid();
                           // Toast.makeText(RegisterActivity.this, "uid " + id + "mail" + email + " name " + name, Toast.LENGTH_SHORT).show();
                            saveUser(id, name, email);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos seis caracteres", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(RegisterActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    void saveUser(String id, String name, String email){

        String selectedUser = mPreferences.getString("user", "");
       // Toast.makeText(RegisterActivity.this, "El valor que seleccionó fue " + selectedUser, Toast.LENGTH_SHORT).show();
        User user = new User ();
        user.setEmail (email);
        user.setName (name);
        if(selectedUser.equals("parent")){
            mDatabase.child("Users").child("Parent").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override

                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registro exitoso" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Registro fallido" , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else if (selectedUser.equals("children")){

            mDatabase.child("Users").child("Children").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registro exitoso" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Registro fallido" , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}