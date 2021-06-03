package com.example.cookbookkeep.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cookbookkeep.R;
import com.example.cookbookkeep.models.Receta;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class RecetaEditActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGuardar, btnCancelar;
    private EditText etNombre, etTiempo, etPorcion, etCalorias, etIngredientes, etPreparacion;
    private RatingBar ratingBar;
    private Spinner spinner;
    private String nombreReceta;
    private FirebaseFirestore db;
    private String nombre, tiempo, porcion, calorias, rating, categoria, ingredientes, preparacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_edit);

        nombreReceta = getIntent().getStringExtra("nombre");

        btnGuardar = findViewById(R.id.btnGuardarRecetaR);
        btnGuardar.setOnClickListener(this);
        btnCancelar = findViewById(R.id.btnCancelarRecetaR);
        btnCancelar.setOnClickListener(this);
        etNombre = findViewById(R.id.editTextNombreR);
        etTiempo = findViewById(R.id.editTextTiempoR);
        etPorcion = findViewById(R.id.editTextPorcionR);
        etCalorias = findViewById(R.id.editTextCaloriasR);
        etIngredientes = findViewById(R.id.editTextIngredientesR);
        etPreparacion = findViewById(R.id.editTextPreparacionR);
        ratingBar = findViewById(R.id.ratingBarR);
        spinner = findViewById(R.id.spinnerR);

        db = FirebaseFirestore.getInstance();
        db.collection("recetas").document(nombreReceta).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                etNombre.setText(value.getString("nombre"));
                etTiempo.setText(value.getString("tiempo"));
                etPorcion.setText(value.getString("porcion"));
                etCalorias.setText(value.getString("calorias"));
                etIngredientes.setText(value.getString("ingredientes"));
                etPreparacion.setText(value.getString("preparacion"));
                ratingBar.setRating(Float.valueOf(value.getString("rating")));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGuardarRecetaR:
                guardarReceta();
                this.finish();
                break;
            case R.id.btnCancelarRecetaR:
                this.finish();
                break;
        }
    }

    private void guardarReceta() {
        nombre = etNombre.getText().toString();
        tiempo = etTiempo.getText().toString();
        porcion = etPorcion.getText().toString();
        calorias = etCalorias.getText().toString();
        ingredientes = etIngredientes.getText().toString();
        preparacion = etPreparacion.getText().toString();
        rating = String.valueOf(ratingBar.getRating());
        categoria = spinner.getSelectedItem().toString();

        Receta receta = new Receta(nombre, tiempo, porcion, calorias, categoria, rating, ingredientes, preparacion);
        db = FirebaseFirestore.getInstance();
        CollectionReference recetas = db.collection("recetas");
        Map<String, String> recetaMap = new HashMap<>();
        recetaMap.put("nombre", receta.getNombre());
        recetaMap.put("tiempo", receta.getTiempo());
        recetaMap.put("porcion", receta.getPorcion());
        recetaMap.put("calorias", receta.getCalorias());
        recetaMap.put("categoria", receta.getCategoria());
        recetaMap.put("rating", receta.getRating());
        recetaMap.put("ingredientes", receta.getIngredientes());
        recetaMap.put("preparacion", receta.getPreparacion());
        recetas.document(nombre).set(recetaMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RecetaEditActivity.this, "Receta editada", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RecetaEditActivity.this, "Ha habido un error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}