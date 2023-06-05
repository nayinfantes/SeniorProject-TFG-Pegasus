package com.example.app_pegasus.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_pegasus.R;
import com.example.app_pegasus.activities.children.ChildrenMainActivity;
import com.example.app_pegasus.activities.children.RegisterActivity;
import com.example.app_pegasus.activities.parent.ParentMainActivity;
import com.example.app_pegasus.activities.parent.RegisterParentActivity;
import com.example.app_pegasus.includes.MyToolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText mEditTextEmail, mEditTextPassword;

    TextView mTextViewGoToRegister, mTextViewGoToRecoverPassword;

    Button mButtonLogin;

    SharedPreferences mPreferences;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyToolbar.show(this, "Inicia sesión", true);

        mEditTextEmail = findViewById(R.id.eTmailLogin);
        mEditTextPassword = findViewById(R.id.eTpasswordLogin);
        mEditTextPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                Drawable visibilityDrawable = mEditTextPassword.getCompoundDrawables()[DRAWABLE_RIGHT];


                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (mEditTextPassword.getRight() - mEditTextPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // El usuario hizo clic en el icono de visualización de contraseña
                        togglePasswordVisibility();
                        return true;
                    }
                }
                return false;            }
        });

        mTextViewGoToRegister = findViewById(R.id.tVGoToRegister);
        mTextViewGoToRecoverPassword = findViewById(R.id.tVGoToRecoverPassword);

        mButtonLogin = findViewById(R.id.btnLogin);

        mDatabase = FirebaseDatabase.getInstance("https://app-pegasus-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        mAuth= FirebaseAuth.getInstance();

        mPreferences = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        mTextViewGoToRecoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRecoverPassword();
            }
        });

        mTextViewGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToRegister();
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    private void togglePasswordVisibility() {
        int selectionStart = mEditTextPassword.getSelectionStart();
        int selectionEnd = mEditTextPassword.getSelectionEnd();

        if (mEditTextPassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
            // Cambiar a modo de visualización de contraseña
            mEditTextPassword.setTransformationMethod(null);
            Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_visibility_24);
            mEditTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
        } else {
            // Cambiar a modo oculto de contraseña
            mEditTextPassword.setTransformationMethod(new PasswordTransformationMethod());
            Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24);
            mEditTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
        }

        // Restaurar la selección del cursor
        mEditTextPassword.setSelection(selectionStart, selectionEnd);
    }

    private void goToRecoverPassword(){
        Intent intent = new Intent(LoginActivity.this, RecoverPasswordActivity.class);
        startActivity(intent);
    }

    private void goToRegister(){
        String user = mPreferences.getString("user", "");

        if (user.equals("parent")) {
            Intent intent = new Intent(LoginActivity.this, RegisterParentActivity.class);
            startActivity(intent);

        } else{
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
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
                            String user = mPreferences.getString("user", "");
                            if (user.equals("parent")) {
                                Intent intent = new Intent(LoginActivity.this, ParentMainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(LoginActivity.this, ChildrenMainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
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