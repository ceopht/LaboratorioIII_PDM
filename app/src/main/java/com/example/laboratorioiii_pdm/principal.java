package com.example.laboratorioiii_pdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class principal extends AppCompatActivity {

    Button btnHistorial, btnDiccionario, btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btnDiccionario = findViewById(R.id.btnDiccionario);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnHistorial = findViewById(R.id.btnHistorial);

        btnDiccionario.setOnClickListener(v -> {
            startActivity(new Intent(principal.this, MainActivity.class));
        });

        btnHistorial.setOnClickListener(v -> {
            startActivity(new Intent(principal.this, HistorialPalabras.class));
        });

        btnLogOut.setOnClickListener(v -> {
            Toast.makeText(principal.this, "Adios", Toast.LENGTH_SHORT).show();
        });
    }
}