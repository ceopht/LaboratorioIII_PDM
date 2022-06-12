package com.example.laboratorioiii_pdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.laboratorioiii_pdm.Adapter.PalabraAdapter;
import com.example.laboratorioiii_pdm.Modelos.Palabra;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class history extends AppCompatActivity {
    DatabaseReference reference;
    ArrayList<String> listaPalabras;
    RecyclerView recyclerViewXD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listaPalabras = new ArrayList<>();
        recyclerViewXD = findViewById(R.id.recyclerViewXD);
        recyclerViewXD.setLayoutManager(new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Users").child(FirebaseAuth.getInstance().getUid()).child("historial").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                        String z_name = zoneSnapshot.child("palabra").getValue().toString();
                        Log.i("historial::", z_name);
                        listaPalabras.add(z_name);
                        PalabraAdapter adapter = new PalabraAdapter (listaPalabras);
                        recyclerViewXD.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast.makeText(history.this, "No se ha encontrado historial disponible", Toast.LENGTH_SHORT).show();
            }
        });
    }


}