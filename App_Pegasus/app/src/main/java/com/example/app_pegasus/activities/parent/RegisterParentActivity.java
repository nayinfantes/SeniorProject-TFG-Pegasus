package com.example.app_pegasus.activities.parent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_pegasus.R;
import com.example.app_pegasus.activities.children.ChildrenMainActivity;
import com.example.app_pegasus.activities.children.RegisterActivity;
import com.example.app_pegasus.includes.MyToolbar;
import com.example.app_pegasus.models.Parent;
import com.example.app_pegasus.providers.AuthProvider;
import com.example.app_pegasus.providers.ParentProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.nullness.qual.NonNull;

public class RegisterParentActivity extends AppCompatActivity {

    EditText mEditTextName;
    EditText mEditTextEmail, mEditTextPassword, mEditTextConfirmPassword;

    Button mButtonRegister;

    AuthProvider mAuthProvider;
    ParentProvider mParentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_parent);
        MyToolbar.show(this, "Regístro de padres", true);
        mAuthProvider = new AuthProvider();
        mParentProvider = new ParentProvider();

        mEditTextName = findViewById(R.id.eTnameRegister);
        mEditTextEmail = findViewById(R.id.eTmailRegister);
        mEditTextPassword = findViewById(R.id.eTpasswordRegister);
        mEditTextConfirmPassword = findViewById(R.id.eTconfirmPasswordRegister);

        mButtonRegister = findViewById(R.id.btnRegister);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegister();
                //goToLogin();
            }
        });
    }

    void clickRegister(){
        final String name = mEditTextName.getText().toString();
        final String email = mEditTextEmail.getText().toString();
        final String password = mEditTextPassword.getText().toString();
        final String password2 = mEditTextConfirmPassword.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !password2.isEmpty()){

            if(password.length() >= 6) {

                if (password.equals(password2)) {

                    register(name, email, password);

                } else {
                    Toast.makeText(RegisterParentActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(RegisterParentActivity.this, "La contraseña debe tener al menos seis caracteres", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(RegisterParentActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    void register(final String name, final String email, final String password){

        mAuthProvider.register(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Parent parent = new Parent(id, name, email);
                    create(parent);
                }
                else {
                    Toast.makeText(RegisterParentActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create(Parent parent) {
        mParentProvider.create(parent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(RegisterParentActivity.this, ParentMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(RegisterParentActivity.this, "No se pudo crear el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}