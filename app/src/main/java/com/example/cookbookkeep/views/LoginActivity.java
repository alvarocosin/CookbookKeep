package com.example.cookbookkeep.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookbookkeep.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnEntrar;
    private TextView txtOlvido, txtRegistrar;
    private EditText editTextCorreo, editTextPassword;

    private FirebaseAuth maAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEntrar = findViewById(R.id.buttonEntrar);
        txtOlvido = findViewById(R.id.textViewOlvidar);
        txtRegistrar = findViewById(R.id.textViewRegistrar);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextPassword = findViewById(R.id.editTextPassword);

        btnEntrar.setOnClickListener(this);
        txtOlvido.setOnClickListener(this);
        txtRegistrar.setOnClickListener(this);

        maAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEntrar:
                userLogin();
                break;
            case R.id.textViewOlvidar:
                String correo = editTextCorreo.getText().toString();
                FirebaseAuth.getInstance().sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(LoginActivity.this, "Correo enviado", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Error al enviar correo", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.textViewRegistrar:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void userLogin() {
        String correo = editTextCorreo.getText().toString();
        String password = editTextPassword.getText().toString();

        if (correo.isEmpty()) {
            editTextCorreo.setError("Debe introducir correo");
            editTextCorreo.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Debe introducir contraseña");
            editTextPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            editTextCorreo.setError("Debe introducir un correo válido");
            editTextCorreo.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Debe introducir contraseña con mínimo 6 caracteres");
            editTextPassword.requestFocus();
            return;
        }

        maAuth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Algo ha fallado, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}