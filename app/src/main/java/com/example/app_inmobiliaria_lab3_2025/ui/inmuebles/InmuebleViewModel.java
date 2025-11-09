package com.example.app_inmobiliaria_lab3_2025.ui.inmuebles;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_inmobiliaria_lab3_2025.modelo.Inmuebles;
import com.example.app_inmobiliaria_lab3_2025.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmuebles>> inmuebles;
    private Application app;

    public InmuebleViewModel(@NonNull Application application) {
        super(application);
        this.app = application;
        inmuebles = new MutableLiveData<>();
    }

    public LiveData<List<Inmuebles>> getInmuebles() {
        return inmuebles;
    }

    public void cargarInmuebles() {
        String token = ApiClient.leerToken(app);
        if (token == null || token.isEmpty()) {
            Log.e("InmuebleViewModel", "Token no encontrado");
            return;
        }

        Call<List<Inmuebles>> call = ApiClient.getApiClientInterface()
                .getInmuebles("Bearer " + token);

        call.enqueue(new Callback<List<Inmuebles>>() {
            @Override
            public void onResponse(Call<List<Inmuebles>> call, Response<List<Inmuebles>> response) {
                if (response.isSuccessful()) {
                    inmuebles.postValue(response.body());
                } else {
                    Log.e("InmuebleViewModel", "Error en respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Inmuebles>> call, Throwable t) {
                Log.e("InmuebleViewModel", "Fallo en API: " + t.getMessage(), t);
            }
        });
    }
}

