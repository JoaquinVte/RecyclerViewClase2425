package com.example.recyclerviewclase2425;

import static android.view.View.INVISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;

import com.example.recyclerviewclase2425.API.Connector;
import com.example.recyclerviewclase2425.base.BaseActivity;
import com.example.recyclerviewclase2425.base.CallInterface;

public class PaisDetalleActivity extends BaseActivity  {

    private EditText etUrl;
    private EditText etNombre;
    private EditText etIdioma;
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
        etIdioma =findViewById(R.id.etIdioma);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave=findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeCall(new CallInterface<Pais>(){
                    @Override
                    public Pais doInBackground() throws Exception {
                        Pais p = new Pais(etNombre.getText().toString(),etUrl.getText().toString(),etIdioma.getText().toString());
                        return Connector.getConector().post(Pais.class,p,"pais");
                    }

                    @Override
                    public void doInUI(Pais data) {
                        Intent intent = new Intent();
                        intent.putExtra("pais",data);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });

            }
        });
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
            etIdioma.setEnabled(false);

            etUrl.setText(p.getUrl());
            etNombre.setText(p.getNombre());
        }

    }





}