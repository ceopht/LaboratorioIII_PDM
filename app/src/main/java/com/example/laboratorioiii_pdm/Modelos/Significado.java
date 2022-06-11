package com.example.laboratorioiii_pdm.Modelos;

import java.util.List;

public class Significado {
    String partOfSpeech="";
    List<Definiciones> definitions = null;

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Definiciones> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definiciones> definitions) {
        this.definitions = definitions;
    }
}
