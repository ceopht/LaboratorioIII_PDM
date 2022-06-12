package com.example.laboratorioiii_pdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {

    public Button registro, login;
    public EditText nombre, correo, pass, carrera;
    DatabaseReference reference;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        mFirestore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_registro);
        nombre = findViewById(R.id.editNombre);
        correo = findViewById(R.id.editCorreo);
        pass = findViewById(R.id.editPass);
        carrera = findViewById(R.id.editCarrera);

        registro = findViewById(R.id.btnRegistro);
        login = findViewById(R.id.btnLogin);

        this.setTitle("Crear cuenta");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getApplicationContext(), login.class);
                startActivity(i);
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameUser = nombre.getText().toString().trim();
                String emailUser = correo.getText().toString().trim();
                String passUser = pass.getText().toString().trim();
                String nameUser2 = nombre.getText().toString().trim();
                String emailUser2 = correo.getText().toString().trim();
                String passUser2 = pass.getText().toString().trim();

                if (nameUser.isEmpty() || emailUser.isEmpty() || passUser.isEmpty()){
                    Toast.makeText(registro.this, "Campos vacios, Complete los datos", Toast.LENGTH_SHORT).show();
                }else{
                    //registerUser2(nameUser2, emailUser2, passUser2);
                    registroUser(nameUser, emailUser, passUser);

                }
            }
        });
    }



    private void registroUser(String nameUser, String emailUser, String passUser) {
        mAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("nombre", nombre.getText().toString());
                    userMap.put("correo", correo.getText().toString());
                    userMap.put("carrera", carrera.getText().toString());


                    String id = mAuth.getCurrentUser().getUid();
                    Log.d("id", id);
                    Log.d("userMap", userMap.toString());

                    reference.child("Users").child(id).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                Toast.makeText(registro.this, "Usuario registrado y agregado", Toast.LENGTH_SHORT).show();

                                //Intent i = new Intent(getApplicationContext(), login.class);
                                //startActivity(i);
                                //finish();
                            }else {
                                Toast.makeText(registro.this, "registrado, no agregado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }else{
                    Toast.makeText(getApplicationContext(), "Fallo al crear usuario",Toast.LENGTH_SHORT).show();

                }
                String ida = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                Log.d("idxd", ida);
                map.put("id", ida.toString());
                map.put("name", nombre.getText().toString());
                map.put("email", correo.getText().toString());
                map.put("password", pass.getText().toString());

                mFirestore.collection("user").document(ida).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        startActivity(new Intent(registro.this, login.class));
                        Toast.makeText(registro.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registro.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    private void registerUser2(String nameUser, String emailUser, String passUser) {
        mAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String ida = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                Log.d("idxd", ida);
                map.put("id", ida.toString());
                map.put("name", nombre.getText().toString());
                map.put("email", correo.getText().toString());
                map.put("password", carrera.getText().toString());

                mFirestore.collection("user").document(ida).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        startActivity(new Intent(registro.this, login.class));
                        Toast.makeText(registro.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registro.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(registro.this, "Error al registrar", Toast.LENGTH_SHORT).show();
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