package com.example.cookbookkeep.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NuevaRecetaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevaRecetaFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnGuardar;
    private Button btnCancelar;
    private EditText etNombre, etTiempo, etPorcion, etCalorias, etIngredientes, etPreparacion;
    private RatingBar ratingBar;
    private Spinner spinner;
    private FirebaseFirestore db;
    private String nombre, tiempo, porcion, calorias, rating, categoria, ingredientes, preparacion;

    public NuevaRecetaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NuevaPeliculaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NuevaRecetaFragment newInstance(String param1, String param2) {
        NuevaRecetaFragment fragment = new NuevaRecetaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nueva_receta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnGuardar = getView().findViewById(R.id.btnGuardarRecetaR);
        btnCancelar = getView().findViewById(R.id.btnCancelarRecetaR);
        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        etNombre = getView().findViewById(R.id.editTextNombreR);
        etTiempo = getView().findViewById(R.id.editTextTiempoR);
        etPorcion = getView().findViewById(R.id.editTextPorcionR);
        etCalorias = getView().findViewById(R.id.editTextCaloriasR);
        spinner = getView().findViewById(R.id.spinnerR);
        etIngredientes = getView().findViewById(R.id.editTextIngredientesR);
        etPreparacion = getView().findViewById(R.id.editTextPreparacionR);
        ratingBar = getView().findViewById(R.id.ratingBarR);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGuardarRecetaR:
                guardarReceta();
                break;
            case R.id.btnCancelarRecetaR:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new RecyclerViewFragment())
                        .commit();
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

        if (nombre.isEmpty()) {
            etNombre.setError("Debe introducir nombre");
            etNombre.requestFocus();
            return;
        }
        if (tiempo.isEmpty()) {
            etTiempo.setError("Debe introducir tiempo");
            etTiempo.requestFocus();
            return;
        }
        if (porcion.isEmpty()) {
            etPorcion.setError("Debe introducir porción");
            etPorcion.requestFocus();
            return;
        }

        if (calorias.isEmpty()) {
            etCalorias.setError("Debe introducir calorías");
            etCalorias.requestFocus();
            return;
        }
        if (ingredientes.isEmpty()) {
            etIngredientes.setError("Debe introducir ingredientes");
            etIngredientes.requestFocus();
            return;
        }
        if (preparacion.isEmpty()) {
            etPreparacion.setError("Debe introducir preparación");
            etPreparacion.requestFocus();
            return;
        }


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
                        Toast.makeText(getActivity(), "Receta añadida", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Ha habido un error", Toast.LENGTH_SHORT).show();
                    }
                });
        etNombre.setText("");
        etTiempo.setText("");
        etPorcion.setText("");
        etCalorias.setText("");
        etIngredientes.setText("");
        etPreparacion.setText("");
        ratingBar.setRating(0);
    }
}