package com.example.app_pegasus.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_pegasus.R;
import com.example.app_pegasus.includes.MyToolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText mEditTextEmail, mEditTextPassword;

    Button mButtonLogin;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyToolbar.show(this, "Inicia sesión", true);

        mEditTextEmail = findViewById(R.id.eTmailLogin);
        mEditTextPassword = findViewById(R.id.eTpasswordLogin);

        mButtonLogin = findViewById(R.id.btnLogin);

        mDatabase = FirebaseDatabase.getInstance("https://app-pegasus-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mAuth = FirebaseAuth.getInstance();
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    private void login() {
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()){
            if (password.length()>=6){
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "El login se ha realizado con exito", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(LoginActivity.this, "El mail o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(LoginActivity.this, "La contraseña debe tener al menos seis caracteres", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(LoginActivity.this, "La contraseña y el mail son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }
}