package com.example.cookbookkeep.models;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbookkeep.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Receta> datalist;

    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener {
        void OnItemClick(int position);
    }

    public void OnRecyclerViewClickListener(OnRecyclerViewClickListener listener) {
        this.listener = listener;
    }

    public MyAdapter(ArrayList<Receta> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_peliculas, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.t1.setText(datalist.get(position).getNombre());
        holder.t2.setText(datalist.get(position).getTiempo() + " minutos");
        holder.t3.setText("(" + datalist.get(position).getPorcion() + " personas)");
        holder.t4.setText(datalist.get(position).getCalorias() + " kcal.");
        holder.t5.setText(datalist.get(position).getCategoria());
        holder.ratingBar.setRating(Float.valueOf(datalist.get(position).getRating()));
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView t1, t2, t3, t4, t5;
        RatingBar ratingBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.textViewNombreRec);
            t2 = itemView.findViewById(R.id.textViewTiempoRec);
            t3 = itemView.findViewById(R.id.textViewPorcionRec);
            t4 = itemView.findViewById(R.id.textViewCaloriasRec);
            t5 = itemView.findViewById(R.id.textViewCategoriaRec);
            ratingBar = itemView.findViewById(R.id.ratingBarR);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(getAdapterPosition());

                    }
                }
            });
        }
    }
}
