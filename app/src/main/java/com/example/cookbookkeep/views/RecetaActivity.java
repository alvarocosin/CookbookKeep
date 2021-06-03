package com.example.cookbookkeep.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cookbookkeep.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class RecetaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnEditar, btnBorrar;
    private TextView txtNombre, txtTiempo, txtPorcion, txtCategoria, txtCalorias, txtIngredientes, txtPreparacion;
    private String nombreReceta;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);

        btnEditar = findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(this);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnBorrar.setOnClickListener(this);

        txtNombre = findViewById(R.id.textViewNombreReceta);
        txtTiempo = findViewById(R.id.textViewTiempo);
        txtPorcion = findViewById(R.id.textViewPorcion);
        txtCategoria = findViewById(R.id.textViewCategoria);
        txtCalorias = findViewById(R.id.textViewCalorias);
        txtIngredientes = findViewById(R.id.textViewIngredientes);
        txtPreparacion = findViewById(R.id.textViewPreparacion);

        nombreReceta = getIntent().getStringExtra("nombre");
        txtNombre.setText(nombreReceta);

        db = FirebaseFirestore.getInstance();
        db.collection("recetas").document(nombreReceta).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String tiempoReceta = value.getString("tiempo");
                String porcionReceta = value.getString("porcion");
                String categoriaReceta = value.getString("categoria");
                String caloriasReceta = value.getString("calorias");
                String ingredientesReceta = value.getString("ingredientes");
                String preparacionReceta = value.getString("preparacion");
                txtTiempo.setText("• Tiempo: " + tiempoReceta + " minutos");
                txtPorcion.setText("• Porción: " + porcionReceta + " personas");
                txtCategoria.setText("• Categoría: " + categoriaReceta);
                txtCalorias.setText("• Calorías: " + caloriasReceta + " kcal.");
                txtIngredientes.setText(ingredientesReceta);
                txtPreparacion.setText(preparacionReceta);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditar:
                Intent intent = new Intent(RecetaActivity.this, RecetaEditActivity.class);
                intent.putExtra("nombre", nombreReceta);
                startActivity(intent);
                break;
            case R.id.btnBorrar:
                AlertDialog diaBox = AskOption();
                diaBox.show();

                break;

        }
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Borrar")
                .setMessage("¿Estás seguro de que quieres borrar la receta?")
                .setIcon(R.drawable.ic_baseline_error_outline_24)
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        RecetaActivity.this.finish();
                        db.collection("recetas").document(nombreReceta).delete();
                        Intent intent2 = new Intent(RecetaActivity.this, MainActivity.class);
                        startActivity(intent2);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;
    }

}