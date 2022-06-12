package com.example.laboratorioiii_pdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    Button backTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listaPalabras = new ArrayList<>();
        recyclerViewXD = findViewById(R.id.recyclerViewXD);
        backTo = findViewById(R.id.backTo);
        recyclerViewXD.setLayoutManager(new LinearLayoutManager(this));

        //Action bar
        this.setTitle("Historial");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


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

        backTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(history.this, principal.class));
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}