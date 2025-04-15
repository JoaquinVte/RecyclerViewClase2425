package com.example.recyclerviewclase2425;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewclase2425.API.Connector;
import com.example.recyclerviewclase2425.base.BaseActivity;
import com.example.recyclerviewclase2425.base.CallInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, CallInterface<List<Pais>> {

    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;
    private List<Pais> paises;

    private Pais pais;
    private int position;

    private CallInterface<Pais> ELIMINAR_PAIS = new CallInterface<>() {
        @Override
        public Pais doInBackground() throws Exception {
            return Connector.getConector().delete(Pais.class, "pais/" + pais.getNombre());
        }

        @Override
        public void doInUI(Pais data) {

            if (data!=null && data.equals(pais)) {
                position = paises.indexOf(data);
                paises.remove(pais);
                recyclerView.getAdapter().notifyItemRemoved(position);

                Snackbar.make(recyclerView, "Deleted " + pais.getNombre(), Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> {
                            executeCall(INSERTAR_PAIS);
                        }).show();
            } else {
                // Se ha producido un error y no se ha podido eliminar de la BD
                // y se obtiene un objeto Pais con todos los atributos a null.
                // Se restaura el objeto en la Posicion
                recyclerView.getAdapter().notifyItemChanged(paises.indexOf(pais));
            }

        }
    };

    private CallInterface<Pais> INSERTAR_PAIS = new CallInterface<>() {
        @Override
        public Pais doInBackground() throws Exception {
            return Connector.getConector().post(Pais.class, pais, "pais");
        }

        @Override
        public void doInUI(Pais data) {
            if(data!=null && data.equals(pais)) {
                paises.add(position, pais);
                recyclerView.getAdapter().notifyItemInserted(position);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvEjemplo);
        btnAdd = findViewById(R.id.btnAdd);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Acciones

        ItemTouchHelper mIth = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        // move item in `fromPos` to `toPos` in adapter.
                        recyclerView.getAdapter().notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;// true if moved, false otherwise
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        pais = paises.get(viewHolder.getAdapterPosition());
                        executeCall(ELIMINAR_PAIS);
                    }

                });
        mIth.attachToRecyclerView(recyclerView);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode()==RESULT_OK){
                        Pais p = (Pais)result.getData().getExtras().getSerializable("pais");
                        paises.add(0, p);
                        recyclerView.getAdapter().notifyItemInserted(0);
                        //Toast.makeText(MainActivity.this,"Pais recibido: " + p.getNombre(),Toast.LENGTH_LONG).show();
                    }
                }
        );

        btnAdd.setOnClickListener( v -> {
            Intent intent = new Intent(MainActivity.this, PaisDetalleActivity.class);
            activityResultLauncher.launch(intent);
        });


        executeCall(this);
    }

    @Override
    public void onClick(View v) {

        int position = recyclerView.getChildAdapterPosition(v);
        Pais p = paises.get(position);

        Intent intent = new Intent(this,PaisDetalleActivity.class);
        intent.putExtra("pais",p);
        startActivity(intent);

        //Toast.makeText(this, "Click " + p.getNombre(), Toast.LENGTH_LONG).show();

//        MyRecyclerViewAdapter.ViewHolder vh = (MyRecyclerViewAdapter.ViewHolder) recyclerView.getChildViewHolder(v);
//        Toast.makeText(this,"Click " + vh.getPais().getNombre(),Toast.LENGTH_LONG).show();
    }

    @Override
    public List<Pais> doInBackground() throws Exception {
        return Connector.getConector().getAsList(Pais.class,"pais");
    }

    @Override
    public void doInUI(List<Pais> data) {
        paises=data;
        recyclerView.setAdapter(new MyRecyclerViewAdapter(this, paises, this));

    }
}