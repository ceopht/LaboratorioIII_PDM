package com.example.laboratorioiii_pdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class principal extends AppCompatActivity {

    Button btnHistorial, btnDiccionario, btnLogOut;
    TextView txtUsername, txtEmail, txtCarrera;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //referencia base de datos
        reference = FirebaseDatabase.getInstance().getReference();
        //referencia de los botones
        btnDiccionario = findViewById(R.id.btnDiccionario);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnHistorial = findViewById(R.id.btnHistorial);
        //referencia de los textView
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtCarrera = findViewById(R.id.txtCarrera);

        setearTextViews();

        btnDiccionario.setOnClickListener(v -> {
            startActivity(new Intent(principal.this, MainActivity.class));
        });

        btnHistorial.setOnClickListener(v -> {
            startActivity(new Intent(principal.this, history.class));
        });

        btnLogOut.setOnClickListener(v -> {
            Toast.makeText(principal.this, "Adios", Toast.LENGTH_SHORT).show();
        });
    }

    private void setearTextViews() {
        reference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                txtUsername.setText(task.getResult().child("nombre").getValue().toString());
                txtCarrera.setText(task.getResult().child("carrera").getValue().toString());
                txtEmail.setText(task.getResult().child("correo").getValue().toString());
            }
        });
    }

}