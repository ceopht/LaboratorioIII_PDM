package com.example.laboratorioiii_pdm.Adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratorioiii_pdm.MainActivity;
import com.example.laboratorioiii_pdm.R;

import java.util.List;

public class PalabraAdapter extends RecyclerView.Adapter<PalabraAdapter.PalabraViewHolder> {
    private List<String> listaPalabras;

    public PalabraAdapter(List<String> listaPalabras) {
        this.listaPalabras = listaPalabras;
    }

    @NonNull
    @Override
    public PalabraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.history_item, null);
        return new PalabraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PalabraViewHolder holder, int position) {
        holder.word.setText(listaPalabras.get(position));
    }

    @Override
    public int getItemCount() {
        return listaPalabras.size();
    }

    public class PalabraViewHolder extends RecyclerView.ViewHolder {
        TextView word;
        Button seeButton;
        public PalabraViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.word);
            seeButton = itemView.findViewById(R.id.seeButton);

            seeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("palabra", word.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });

        }
    }
}
