package com.example.laboratorioiii_pdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    public Button login, registrace;
    public EditText correo, pass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        correo = findViewById(R.id.editcorreoL);
        pass = findViewById(R.id.editpassL);

        login = findViewById(R.id.btnLogin);
        registrace = findViewById(R.id.btnRe);

        registrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getApplicationContext(), registro.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = correo.getText().toString().trim();
                String passUser = pass.getText().toString().trim();

                if (emailUser.isEmpty() || passUser.isEmpty()){
                    Toast.makeText(login.this, "Campos vacios, Complete los datos", Toast.LENGTH_SHORT).show();

                }else{
                    LoginUser(emailUser, passUser);
                }
            }
        });
    }

    private void LoginUser(String emailUser, String passUser) {
        mAuth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    Intent i = new Intent(getApplicationContext(), principal.class);
                    startActivity(i);
                    Toast.makeText(login.this, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(login.this, "No se logro iniciar sesion", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}