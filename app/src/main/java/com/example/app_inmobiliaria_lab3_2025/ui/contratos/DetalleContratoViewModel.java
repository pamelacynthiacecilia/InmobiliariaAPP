package com.example.app_inmobiliaria_lab3_2025.ui.contratos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_inmobiliaria_lab3_2025.modelo.Contratos;

public class DetalleContratoViewModel extends AndroidViewModel {

    private MutableLiveData<Contratos> contrato = new MutableLiveData<>();

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Contratos> getContrato() {
        return contrato;
    }

    public void setContrato(Contratos c) {
        contrato.setValue(c);
    }
}