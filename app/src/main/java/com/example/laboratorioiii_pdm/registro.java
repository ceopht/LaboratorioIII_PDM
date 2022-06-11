package com.example.laboratorioiii_pdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {

    public Button registro, login;
    public EditText nombre, correo, pass, carrera;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_registro);
        nombre = findViewById(R.id.editNombre);
        correo = findViewById(R.id.editCorreo);
        pass = findViewById(R.id.editPass);
        carrera = findViewById(R.id.editCarrera);

        registro = findViewById(R.id.btnRegistro);
        login = findViewById(R.id.btnLogin);

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

                if (nameUser.isEmpty() || emailUser.isEmpty() || passUser.isEmpty()){
                    Toast.makeText(registro.this, "Campos vacios, Complete los datos", Toast.LENGTH_SHORT).show();
                }else{
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

                                Intent i = new Intent(getApplicationContext(), login.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(registro.this, "registrado, no agregado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }else{
                    Toast.makeText(getApplicationContext(), "Fallo al crear usuario",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}