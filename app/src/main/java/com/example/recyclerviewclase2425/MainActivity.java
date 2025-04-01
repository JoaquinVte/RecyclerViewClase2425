package com.example.recyclerviewclase2425;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvEjemplo);

        List<Pais> paises = List.of(
                new Pais("Afganistan","https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Flag_of_the_Taliban.svg/360px-Flag_of_the_Taliban.svg.png"),
                new Pais("Albania","https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/Flag_of_Albania.svg/252px-Flag_of_Albania.svg.png"),
                new Pais("Alemania","https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Flag_of_Germany.svg/300px-Flag_of_Germany.svg.png"),
                new Pais("Andorra","https://upload.wikimedia.org/wikipedia/commons/thumb/1/19/Flag_of_Andorra.svg/257px-Flag_of_Andorra.svg.png"),
                new Pais("Angola","https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Flag_of_Angola.svg/270px-Flag_of_Angola.svg.png"),
                new Pais("Argelia","https://upload.wikimedia.org/wikipedia/commons/thumb/7/77/Flag_of_Algeria.svg/270px-Flag_of_Algeria.svg.png"),
                new Pais("Argentina","https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Flag_of_Argentina.svg/288px-Flag_of_Argentina.svg.png"),
                new Pais("Armenia","https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Flag_of_Armenia.svg/360px-Flag_of_Armenia.svg.png"),
                new Pais("Bahamas","https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/Flag_of_the_Bahamas.svg/360px-Flag_of_the_Bahamas.svg.png"),
                new Pais("Belice","https://upload.wikimedia.org/wikipedia/commons/thumb/e/e7/Flag_of_Belize.svg/300px-Flag_of_Belize.svg.png"),
                new Pais("Bolivia","https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/Flag_of_Bolivia.svg/264px-Flag_of_Bolivia.svg.png"),
                new Pais("España","https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Flag_of_Spain.svg/270px-Flag_of_Spain.svg.png"),
                new Pais("EEUU","https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Flag_of_the_United_States.svg/342px-Flag_of_the_United_States.svg.png"),
                new Pais("Finlandia","https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Flag_of_Finland.svg/295px-Flag_of_Finland.svg.png"),
                new Pais("Francia","https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Flag_of_France.svg/270px-Flag_of_France.svg.png")
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new MyRecyclerViewAdapter(this,paises));



    }
}