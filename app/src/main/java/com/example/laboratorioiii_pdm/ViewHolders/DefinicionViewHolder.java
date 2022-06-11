package com.example.laboratorioiii_pdm.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratorioiii_pdm.R;

import java.time.temporal.Temporal;

public class DefinicionViewHolder extends RecyclerView.ViewHolder {
    public TextView definiciones,ejemplos,sinonimos,antonimos;

    public DefinicionViewHolder(@NonNull View itemView) {
        super(itemView);
        definiciones = itemView.findViewById(R.id.definiciones);
        ejemplos = itemView.findViewById(R.id.ejemplos);
        sinonimos = itemView.findViewById(R.id.sinonimos);
        antonimos = itemView.findViewById(R.id.antonimos);
    }
}
