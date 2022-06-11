package com.example.laboratorioiii_pdm.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratorioiii_pdm.R;

public class FoneticaViewHolder extends RecyclerView.ViewHolder {
    public TextView fonetica;
    public ImageButton imageButton_audio;

    public FoneticaViewHolder(@NonNull View itemView) {
        super(itemView);
        fonetica = itemView.findViewById(R.id.fonetica);
        imageButton_audio = itemView.findViewById(R.id.imageBotton_audio);

    }
}
