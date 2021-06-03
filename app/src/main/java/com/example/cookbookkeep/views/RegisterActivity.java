package com.example.cookbookkeep.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cookbookkeep.R;
import com.example.cookbookkeep.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText editTextNombre, editTextEdad, editTextCorreoRegistro, editTextPasswordRegistro;
    private Button btnRegistrar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editTextNombre = findViewById(R.id.editTextNombreR);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextCorreoRegistro = findViewById(R.id.editTextCorreoRegistro);
        editTextPasswordRegistro = findViewById(R.id.editTextPasswordRegistro);

        btnRegistrar = findViewById(R.id.buttonRegistrar);
        btnRegistrar.setOnClickListener(this);
        btnCancelar = findViewById(R.id.buttonCancelar);
        btnCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegistrar:
                registrar();
                break;
            case R.id.buttonCancelar:
                this.finish();
                break;
        }
    }

    private void registrar() {
        String nombre = editTextNombre.getText().toString();
        String edad = editTextEdad.getText().toString();
        String correo = editTextCorreoRegistro.getText().toString();
        String password = editTextPasswordRegistro.getText().toString();

        if (nombre.isEmpty()) {
            editTextNombre.setError("Debe introducir nombre");
            editTextNombre.requestFocus();
            return;
        }
        if (edad.isEmpty()) {
            editTextEdad.setError("Debe introducir edad");
            editTextEdad.requestFocus();
            return;
        }
        if (correo.isEmpty()) {
            editTextCorreoRegistro.setError("Debe introducir correo");
            editTextCorreoRegistro.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            editTextCorreoRegistro.setError("Debe introducir un correo válido");
            editTextCorreoRegistro.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPasswordRegistro.setError("Debe introducir contraseña");
            editTextPasswordRegistro.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPasswordRegistro.setError("Debe introducir contraseña con mínimo 6 caracteres");
            editTextPasswordRegistro.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(correo, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Usuario usuario = new Usuario(nombre, edad, correo);

                            db = FirebaseFirestore.getInstance();
                            CollectionReference usuarios = db.collection("usuarios");
                            Map<String, String> usuarioMap = new HashMap<>();
                            usuarioMap.put("nombre", usuario.getNombre());
                            usuarioMap.put("edad", usuario.getEdad());
                            usuarioMap.put("correo", usuario.getCorreo());
                            usuarios.document(correo).set(usuarioMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RegisterActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                                            RegisterActivity.this.finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity.this, "Ha habido un error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
    }
}