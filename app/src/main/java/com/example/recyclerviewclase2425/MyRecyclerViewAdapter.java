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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

    private List<Pais> paises;
    private Context context;
    private LayoutInflater inflater;
    private View.OnClickListener onClickListener;

    public MyRecyclerViewAdapter(Context context,List<Pais> paises,View.OnClickListener onClickListener) {
        this.context = context;
        this.onClickListener = onClickListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.paises=paises;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_holder_layout,parent,false);
        view.setOnClickListener(onClickListener);
        return new ViewHolder(view);
    }

    public void addPaises(List<Pais> paises){
        this.paises=paises;
        notifyDataSetChanged();
    }

    public void sort(Comparator<Pais> comparator){
        Collections.sort(paises,comparator);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Pais pais = paises.get(position);

        holder.tvNombre.setText(pais.getNombre());
        holder.setPais(pais);

        //Picasso.get().load(url).into(iv)
        Picasso.get()
                .load(pais.getUrl())
                .error(context.getDrawable(R.drawable.ic_launcher_background))
                .into(holder.ivBandera);

    }

    @Override
    public int getItemCount() {
        return paises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivBandera;
        private TextView tvNombre;
        private Pais pais;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBandera = itemView.findViewById(R.id.ivBandera);
            tvNombre = itemView.findViewById(R.id.tvNombre);

        }

        public void setPais(Pais pais) {
            this.pais = pais;
        }

        public TextView getTvNombre() {
            return tvNombre;
        }

        public Pais getPais() {
            return pais;
        }
    }
}
