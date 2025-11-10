package com.example.app_inmobiliaria_lab3_2025.ui.contratos;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_inmobiliaria_lab3_2025.modelo.Contratos;
import com.example.app_inmobiliaria_lab3_2025.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosInmuebleViewModel  extends AndroidViewModel {

    private MutableLiveData<List<Contratos>> contratos = new MutableLiveData<>();
    private Application app;

    public ContratosInmuebleViewModel(@NonNull Application application) {
        super(application);
        this.app = application;
    }

    public LiveData<List<Contratos>> getContratos() {
        return contratos;
    }

    public void cargarContratos(int inmuebleId) {
        String token = ApiClient.leerToken(app);
        Call<List<Contratos>> call = ApiClient.getApiClientInterface().obtenerContratosPorInmueble("Bearer " + token, inmuebleId);

        call.enqueue(new Callback<List<Contratos>>() {
            @Override
            public void onResponse(Call<List<Contratos>> call, Response<List<Contratos>> response) {
                if (response.isSuccessful()) {
                    contratos.postValue(response.body());
                } else {
                    Log.e("ContratosInmuebleVM", "Error en respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Contratos>> call, Throwable t) {
                Log.e("ContratosInmuebleVM", "Fallo en API: " + t.getMessage(), t);
            }
        });
    }
}
