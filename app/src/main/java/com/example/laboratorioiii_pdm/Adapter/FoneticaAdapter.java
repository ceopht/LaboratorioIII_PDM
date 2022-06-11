package com.example.laboratorioiii_pdm.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratorioiii_pdm.MainActivity;
import com.example.laboratorioiii_pdm.Modelos.Fonetica;
import com.example.laboratorioiii_pdm.R;
import com.example.laboratorioiii_pdm.ViewHolders.FoneticaViewHolder;

import java.util.List;

public class FoneticaAdapter extends RecyclerView.Adapter<FoneticaViewHolder> {
    private Context context;
    private List<Fonetica> foneticaList;

    public FoneticaAdapter(Context context, List<Fonetica> foneticaList) {
        this.context = context;
        this.foneticaList = foneticaList;
    }

    @NonNull
    @Override
    public FoneticaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoneticaViewHolder(LayoutInflater.from(context).inflate(R.layout.fonetica_list_items,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull FoneticaViewHolder holder, int position) {
        holder.fonetica.setText(foneticaList.get(position).getText());
        holder.imageButton_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MediaPlayer player = new MediaPlayer();
                try{
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource("https:"+foneticaList.get(position).getAudio());
                    player.prepare();
                    player.start();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(context, "No fue posible reproducir el audio", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foneticaList.size();
    }
}
