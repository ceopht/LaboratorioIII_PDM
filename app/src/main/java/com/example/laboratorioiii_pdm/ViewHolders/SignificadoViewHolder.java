package com.example.laboratorioiii_pdm.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratorioiii_pdm.R;

import java.time.temporal.Temporal;

public class SignificadoViewHolder extends RecyclerView.ViewHolder {
    public TextView partsOfSpeech;
    public RecyclerView recyclerDefinicion;

    public SignificadoViewHolder(@NonNull View itemView) {
        super(itemView);

        partsOfSpeech = itemView.findViewById(R.id.partsofSpeech);
        recyclerDefinicion = itemView.findViewById(R.id.recyclerDefinicion);

    }
}
