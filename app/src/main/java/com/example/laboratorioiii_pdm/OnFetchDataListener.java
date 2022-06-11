package com.example.laboratorioiii_pdm;

import com.example.laboratorioiii_pdm.Modelos.API;

public interface OnFetchDataListener {
    void onFetchData(API api, String mensaje);
    void onError(String message);
}
