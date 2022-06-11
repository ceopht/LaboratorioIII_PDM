package com.example.laboratorioiii_pdm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratorioiii_pdm.Modelos.Significado;
import com.example.laboratorioiii_pdm.R;
import com.example.laboratorioiii_pdm.ViewHolders.SignificadoViewHolder;

import java.util.List;

public class SignificadoAdapter extends RecyclerView.Adapter<SignificadoViewHolder> {
    private Context context;
    protected List<Significado> significadoList;

    public SignificadoAdapter(Context context, List<Significado> significadoList) {
        this.context = context;
        this.significadoList = significadoList;
    }

    @NonNull
    @Override
    public SignificadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SignificadoViewHolder(LayoutInflater.from(context).inflate(R.layout.significado_list_items,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull SignificadoViewHolder holder, int position) {
        holder.partsOfSpeech.setText("Partes de la oracion: "+significadoList.get(position).getPartOfSpeech());
        holder.recyclerDefinicion.setHasFixedSize(true);
        holder.recyclerDefinicion.setLayoutManager(new GridLayoutManager(context,1));

        DefinicionAdapter definicionAdapter = new DefinicionAdapter(context,significadoList.get(position).getDefinitions());
        holder.recyclerDefinicion.setAdapter(definicionAdapter);
    }

    @Override
    public int getItemCount() {
        return significadoList.size();
    }
}
