package com.example.recyclerviewclase2425;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

    private List<Pais> paises;
    private Context context;
    private LayoutInflater inflater;

    public MyRecyclerViewAdapter(Context context,List<Pais> paises) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.paises=paises;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_holder_layout,parent,false);
        view.setOnClickListener();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Pais pais = paises.get(position);

        holder.tvNombre.setText(pais.getNombre());

        //Picasso.get().load(url).into(iv)
        Picasso.get().load(pais.getUrlBandera()).into(holder.ivBandera);
    }

    @Override
    public int getItemCount() {
        return paises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivBandera;
        private TextView tvNombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivBandera = itemView.findViewById(R.id.ivBandera);
            tvNombre = itemView.findViewById(R.id.tvNombre);

        }
    }
}
