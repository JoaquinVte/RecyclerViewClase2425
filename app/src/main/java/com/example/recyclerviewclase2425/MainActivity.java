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

            if (data.equals(pais)) {
                position = paises.indexOf(data);
                paises.remove(pais);
                recyclerView.getAdapter().notifyItemRemoved(position);

                Snackbar.make(recyclerView, "Deleted " + pais.getNombre(), Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> {
                            executeCall(INSERTAR_PAIS);
                        }).show();
            } else {
                recyclerView.getAdapter().notifyItemChanged(position);
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
            paises.add(position, pais);
            recyclerView.getAdapter().notifyItemInserted(position);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvEjemplo);
        btnAdd = findViewById(R.id.btnAdd);

//        paises = new ArrayList<>(List.of(
//                new Pais("Afganistan", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Flag_of_the_Taliban.svg/360px-Flag_of_the_Taliban.svg.png"),
//                new Pais("Albania", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/Flag_of_Albania.svg/252px-Flag_of_Albania.svg.png"),
//                new Pais("Alemania", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Flag_of_Germany.svg/300px-Flag_of_Germany.svg.png"),
//                new Pais("Andorra", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/19/Flag_of_Andorra.svg/257px-Flag_of_Andorra.svg.png"),
//                new Pais("Angola", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Flag_of_Angola.svg/270px-Flag_of_Angola.svg.png"),
//                new Pais("Argelia", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/77/Flag_of_Algeria.svg/270px-Flag_of_Algeria.svg.png"),
//                new Pais("Argentina", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Flag_of_Argentina.svg/288px-Flag_of_Argentina.svg.png"),
//                new Pais("Armenia", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Flag_of_Armenia.svg/360px-Flag_of_Armenia.svg.png"),
//                new Pais("Bahamas", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/Flag_of_the_Bahamas.svg/360px-Flag_of_the_Bahamas.svg.png"),
//                new Pais("Belice", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e7/Flag_of_Belize.svg/300px-Flag_of_Belize.svg.png"),
//                new Pais("Bolivia", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/Flag_of_Bolivia.svg/264px-Flag_of_Bolivia.svg.png"),
//                new Pais("España", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Flag_of_Spain.svg/270px-Flag_of_Spain.svg.png"),
//                new Pais("EEUU", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Flag_of_the_United_States.svg/342px-Flag_of_the_United_States.svg.png"),
//                new Pais("Finlandia", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Flag_of_Finland.svg/295px-Flag_of_Finland.svg.png"),
//                new Pais("Francia", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Flag_of_France.svg/270px-Flag_of_France.svg.png")
//        ));

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