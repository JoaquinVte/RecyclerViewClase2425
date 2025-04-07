package com.example.recyclerviewclase2425;

import static android.view.View.INVISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PaisDetalleActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUrl;
    private EditText etNombre;
    private EditText etPoblacion;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pais_detalle);
        Pais p = null;

        if(getIntent().getExtras()!=null)
            p = (Pais) getIntent().getExtras().getSerializable("pais");

        etUrl = findViewById(R.id.etUrl);
        etNombre = findViewById(R.id.etNombre);
        etPoblacion=findViewById(R.id.etPoblacion);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave=findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED,intent);
            finish();
        });

        if(p!=null){
            // Ocultar botones
            btnCancel.setVisibility(INVISIBLE);
            btnSave.setVisibility(INVISIBLE);
            // Disable editText
            etUrl.setEnabled(false);
            etNombre.setEnabled(false);
            etPoblacion.setEnabled(false);

            etUrl.setText(p.getUrlBandera());
            etNombre.setText(p.getNombre());
        }

    }

    @Override
    public void onClick(View v) {
        Pais p = new Pais(etNombre.getText().toString(),"https://upload.wikimedia.org/wikipedia/commons/thumb/b/bd/Flag_of_Cuba.svg/500px-Flag_of_Cuba.svg.png");
        Intent intent = new Intent();
        intent.putExtra("pais",p);
        setResult(RESULT_OK,intent);
        finish();
    }
}