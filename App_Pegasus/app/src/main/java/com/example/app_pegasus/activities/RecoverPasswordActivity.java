package com.example.app_pegasus.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_pegasus.R;
import com.example.app_pegasus.activities.children.RegisterActivity;
import com.example.app_pegasus.activities.parent.RegisterParentActivity;
import com.example.app_pegasus.includes.MyToolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RecoverPasswordActivity extends AppCompatActivity {

    EditText mEditTextEmail;
    Button mButtonRecoverPassword;

    SharedPreferences mPreferences;
    TextView mTextViewGoToRegister;

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        MyToolbar.show(this, "Recupera tu contraseña", true);

        mEditTextEmail = findViewById(R.id.eTmailRecoverPassword);
        mButtonRecoverPassword = findViewById(R.id.btnRecoverPassword);
        mButtonRecoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email= mEditTextEmail.getText().toString();
                if(!email.isEmpty()){
                    recoverPassword();
                } else{
                    Toast.makeText(RecoverPasswordActivity.this, "Debe ingresar ell mail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mPreferences = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        mDatabase = FirebaseDatabase.getInstance("https://app-pegasus-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        mAuth = FirebaseAuth.getInstance();

        mTextViewGoToRegister = findViewById(R.id.tVGoToRegister);

        mTextViewGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });
    }

    private void recoverPassword(){
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RecoverPasswordActivity.this, "Se ha enviado un correo recuperar su contraseña, revisa la carpeta de spam si no logras localizarlo", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(RecoverPasswordActivity.this, "No se pudo recuperar su contraseña, quizas el usuario no exista", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToRegister(){
        String user = mPreferences.getString("user", "");
        if (user.equals("parent")) {
            Intent intent = new Intent(RecoverPasswordActivity.this, RegisterParentActivity.class);
            startActivity(intent);

        } else{
            Intent intent = new Intent(RecoverPasswordActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}