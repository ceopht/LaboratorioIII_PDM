package com.example.laboratorioiii_pdm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratorioiii_pdm.Modelos.Definiciones;
import com.example.laboratorioiii_pdm.R;
import com.example.laboratorioiii_pdm.ViewHolders.DefinicionViewHolder;

import java.util.List;

public class DefinicionAdapter extends RecyclerView.Adapter<DefinicionViewHolder> {

    private Context context;
    private List<Definiciones> definicionesList;

    public DefinicionAdapter(Context context, List<Definiciones> definicionesList) {
        this.context = context;
        this.definicionesList = definicionesList;
    }

    @NonNull
    @Override
    public DefinicionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefinicionViewHolder(LayoutInflater.from(context).inflate(R.layout.definiciones_list_items,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull DefinicionViewHolder holder, int position) {
        holder.definiciones.setText("Definiciones: "+definicionesList.get(position).getDefinition());
        holder.ejemplos.setText("Ejemplos: "+definicionesList.get(position).getExample());
        StringBuilder sinonimos = new StringBuilder();
        StringBuilder antonimos = new StringBuilder();

        sinonimos.append(definicionesList.get(position).getSynonyms());
        antonimos.append(definicionesList.get(position).getAntonyms());

        holder.sinonimos.setText(sinonimos);
        holder.antonimos.setText(antonimos);

        holder.sinonimos.setSelected(true);
        holder.antonimos.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return definicionesList.size();
    }
}
