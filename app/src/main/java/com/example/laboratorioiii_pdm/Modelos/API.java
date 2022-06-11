package com.example.laboratorioiii_pdm.Modelos;

import java.util.List;

public class API {
    String word = "";
    List<Fonetica> phonetics = null;
    List<Significado> meanings = null;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Fonetica> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<Fonetica> phonetics) {
        this.phonetics = phonetics;
    }

    public List<Significado> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Significado> meanings) {
        this.meanings = meanings;
    }
}
