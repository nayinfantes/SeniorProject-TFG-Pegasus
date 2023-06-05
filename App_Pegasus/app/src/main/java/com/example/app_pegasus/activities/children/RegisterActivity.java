package com.example.app_pegasus.activities.children;

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
import com.example.app_pegasus.activities.parent.RegisterParentActivity;
import com.example.app_pegasus.includes.MyToolbar;
import com.example.app_pegasus.models.Children;
import com.example.app_pegasus.providers.AuthProvider;
import com.example.app_pegasus.providers.ChildrenProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.nullness.qual.NonNull;

public class RegisterActivity extends AppCompatActivity {

   // SharedPreferences mPreferences;

    EditText mEditTextName, mEditTextEmail, mEditTextParentEmail, mEditTextPassword, mEditTextConfirmPassword;

    TextView mTextViewGoToRegister;

    Button mButtonRegister;

    AuthProvider mAuthProvider;
    ChildrenProvider mChildrenProvider;

    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MyToolbar.show(this, "Regístro de hijos", true);

       // mPreferences = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        mAuthProvider = new AuthProvider();
        mChildrenProvider = new ChildrenProvider();

        mEditTextName = findViewById(R.id.eTnameRegister);
        mEditTextEmail = findViewById(R.id.eTmailRegister);
        mEditTextParentEmail = findViewById(R.id.eTmailParentRegister);
        mEditTextPassword = findViewById(R.id.eTpasswordRegister);
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

        mEditTextConfirmPassword = findViewById(R.id.eTconfirmPasswordRegister);
        mEditTextConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                Drawable visibilityDrawable = mEditTextConfirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT];

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (mEditTextConfirmPassword.getRight() - mEditTextConfirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // El usuario hizo clic en el icono de visualización de contraseña
                        toggleConfirmPasswordVisibility();
                        return true;
                    }
                }
                return false;            }
        });

        mPreferences = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        mTextViewGoToRegister = findViewById(R.id.tVGoToRegister);
        mTextViewGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });

        mButtonRegister = findViewById(R.id.btnRegister);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegister();
                //goToLogin();
            }
        });
    }
    private void toggleConfirmPasswordVisibility() {
        int selectionStart = mEditTextConfirmPassword.getSelectionStart();
        int selectionEnd = mEditTextConfirmPassword.getSelectionEnd();

        if (mEditTextConfirmPassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
            // Cambiar a modo de visualización de contraseña
            mEditTextConfirmPassword.setTransformationMethod(null);
            Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_visibility_24);
            mEditTextConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
        } else {
            // Cambiar a modo oculto de contraseña
            mEditTextConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
            Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24);
            mEditTextConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
        }

        // Restaurar la selección del cursor
        mEditTextConfirmPassword.setSelection(selectionStart, selectionEnd);
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
    public void goToRegister() {
        String typeUser = mPreferences.getString("user", "");
        if (typeUser.equals("children")) {
            Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(RegisterActivity.this, RegisterParentActivity.class);
            startActivity(intent);
        }
    }

    void clickRegister(){
        final String name = mEditTextName.getText().toString();
        final String email = mEditTextEmail.getText().toString();
        final String parentEmail = mEditTextParentEmail.getText().toString();
        final String password = mEditTextPassword.getText().toString();
        final String password2 = mEditTextConfirmPassword.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !password2.isEmpty() && !parentEmail.isEmpty()){

            if(password.length() >= 6){
                if(password.equals(password2)){
                    register(name, email, password, parentEmail);
                } else{
                    Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos seis caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    void register(final String name, final String email, final String password, final String parentEmail){

        mAuthProvider.register(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Children children = new Children(id, name, email);
                    children.setParentEmail(parentEmail);
                    create(children);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create(Children children) {
        mChildrenProvider.create(children).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, ChildrenMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

  /*  void saveUser(String id, String name, String email){

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
   public void goToLogin(){
        Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }*/
}