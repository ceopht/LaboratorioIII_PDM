package com.example.laboratorioiii_pdm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;

public class principal extends AppCompatActivity {

    Button btnHistorial, btnDiccionario, btnLogOut, btnsubir, btnborrar;
    TextView txtUsername, txtEmail, txtCarrera;
    DatabaseReference reference;
    ImageView photop;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mfirestore;

    StorageReference storageReference;
    String storage_path = "img/*";

    private static final int COD_SEL_STORAGE = 200;
    private static final int COD_SEL_IMAGE = 300;

    private Uri image_url;
    String photo = "photo";
    String idd;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //referencia base de datos
        reference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        mfirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        String id = getIntent().getStringExtra("id_photo");
        //referencia de los botones
        btnDiccionario = findViewById(R.id.btnDiccionario);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnHistorial = findViewById(R.id.btnHistorial);
        btnsubir = findViewById(R.id.btnFoto);
        btnborrar = findViewById(R.id.btnBorrarF);
        //referencia de los textView
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtCarrera = findViewById(R.id.txtCarrera);
        //img
        photop = findViewById(R.id.imageView);

        setearTextViews();
        idd = id;
        getImg();

        btnsubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto();
            }
        });
        btnDiccionario.setOnClickListener(v -> {
            startActivity(new Intent(principal.this, MainActivity.class));
        });

        btnHistorial.setOnClickListener(v -> {
            startActivity(new Intent(principal.this, history.class));
        });

        btnLogOut.setOnClickListener(v -> {
            mAuth.signOut();
            finish();
            Intent i = new Intent(getApplicationContext(), login.class);
            startActivity(i);
        });

    }

    private void getImg() {
        String ida = mAuth.getCurrentUser().getUid();
        mfirestore.collection("user").document(ida).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String photoPerfil = documentSnapshot.getString("photo");


                try {
                    if(!photoPerfil.equals("")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Cargando foto", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,200);
                        toast.show();
                        Picasso.with(principal.this)
                                .load(photoPerfil)
                                .resize(150, 150)
                                .into(photop);
                    }
                }catch (Exception e){
                    Log.v("Error", "e: " + e);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
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
        String id = mAuth.getCurrentUser().getUid();
        Log.d("idxd", id);
        mfirestore.collection("img").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String photoPerfil = documentSnapshot.getString("photo");


                try {
                    if(!photoPerfil.equals("")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Cargando foto", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,200);
                        toast.show();
                        Picasso.with(principal.this)
                                .load(photoPerfil)
                                .resize(150, 150)
                                .into(photop);
                    }
                }catch (Exception e){
                    Log.v("Error", "e: " + e);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void uploadPhoto() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");

        startActivityForResult(i, COD_SEL_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("image_url", "requestCode - RESULT_OK: "+requestCode+" "+RESULT_OK);
        if(resultCode == RESULT_OK){
            if (requestCode == COD_SEL_IMAGE){
                image_url = data.getData();
                subirPhoto(image_url);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void subirPhoto(Uri image_url) {
        progressDialog.setMessage("Actualizando foto");
        progressDialog.show();

        //Log.d("idxd", idd);
        String rute_storage_photo = storage_path + "" + photo + "" + mAuth.getUid();

        StorageReference reference = storageReference.child(rute_storage_photo);
        reference.putFile(image_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                if (uriTask.isSuccessful()){
                    uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String ida = mAuth.getCurrentUser().getUid();
                            String download_uri = uri.toString();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("photo", download_uri);
                            mfirestore.collection("user").document(ida).update(map);
                            Toast.makeText(principal.this, "Foto actualizada", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            getImg();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(principal.this, "Error al cargar foto", Toast.LENGTH_SHORT).show();
            }
        });

    }
}