package com.example.laboratorioiii_pdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laboratorioiii_pdm.Adapter.FoneticaAdapter;
import com.example.laboratorioiii_pdm.Adapter.SignificadoAdapter;
import com.example.laboratorioiii_pdm.Modelos.API;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    TextView word;
    RecyclerView recyclerViewFonetica, recyclerViewSignificado;
    ProgressDialog progressDialog;//Barra de carga
    FoneticaAdapter foneticaAdapter;
    SignificadoAdapter significadoAdapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        String palabra = "hello";

        try {
            palabra = bundle.getString("palabra");
        }catch (Exception e){
            palabra = "hello";
        }

        reference = FirebaseDatabase.getInstance().getReference();
        searchView = findViewById(R.id.search_view);
        word = findViewById(R.id.word);
        recyclerViewFonetica = findViewById(R.id.recyclerFonetica);
        recyclerViewSignificado = findViewById(R.id.recyclerSignificado);
        progressDialog = new ProgressDialog(this);



        progressDialog.setTitle("Cargando...");
        progressDialog.show();
        //Llamado API

        Manejador manager = new Manejador(MainActivity.this);
        manager.getSignificadoPalabra(listener, palabra);



        //
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Buscando "+query);
                progressDialog.show();

                //GUARDANDO LA PALABRA
                String histori = searchView.getQuery().toString();
                historial(histori);

                //Llamado API

                Manejador manager = new Manejador(MainActivity.this);
                manager.getSignificadoPalabra(listener, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void historial(String histori) {
        Map<String, Object> historialMap = new HashMap<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        historialMap.put("palabra", histori);

        Log.d("id", (uid));
        Log.d("userMap", historialMap.toString());

        reference.child("Users").child(uid).child("historial").push().setValue(historialMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if (task2.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Historial guardado", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MainActivity.this, "no funciona", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(API api, String mensaje) {
            progressDialog.dismiss();
            if (api==null){
                Toast.makeText(MainActivity.this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                return;
            }
            showData(api);

        }

        @Override
        public void onError(String message) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showData(API api) {
        word.setText("Palabra: "+api.getWord());
        recyclerViewFonetica.setHasFixedSize(true);
        recyclerViewFonetica.setLayoutManager(new GridLayoutManager(this,1));
        foneticaAdapter = new FoneticaAdapter(this, api.getPhonetics());
        recyclerViewFonetica.setAdapter(foneticaAdapter);

        recyclerViewSignificado.setHasFixedSize(true);
        recyclerViewSignificado.setLayoutManager(new GridLayoutManager(this,1));
        significadoAdapter = new SignificadoAdapter(this, api.getMeanings());
        recyclerViewSignificado.setAdapter(significadoAdapter);

    }
}