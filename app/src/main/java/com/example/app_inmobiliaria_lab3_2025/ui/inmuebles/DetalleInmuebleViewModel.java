package com.example.app_inmobiliaria_lab3_2025.ui.inmuebles;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_inmobiliaria_lab3_2025.modelo.Inmuebles;
import com.example.app_inmobiliaria_lab3_2025.request.ApiClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> estadoActual = new MutableLiveData<>();
    private MutableLiveData<Boolean> resultadoExito = new MutableLiveData<>();
    private MutableLiveData<String> resultadoError = new MutableLiveData<>();


    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<Integer> getEstadoActual() {
        return estadoActual;
    }


    public LiveData<Boolean> getResultadoExito() {
        return resultadoExito;
    }

    public LiveData<String> getResultadoError() {
        return resultadoError;
    }


    public void actualizarEstado(int Id, Inmuebles inmueble) {

        String token = ApiClient.leerToken(getApplication());
        Log.d("Retrofit", "JSON enviado: " + new Gson().toJson(inmueble));
        Log.d("Retrofit", "Id por param metodo actualizarEstado " +Id);


                Call<Inmuebles> call = ApiClient.getApiClientInterface()
                .modificarDisponible("Bearer " + token, Id, inmueble)/*, inmueble)*/;
        call.enqueue(new Callback<Inmuebles>() {
            @Override
            public void onResponse(Call<Inmuebles> call, Response<Inmuebles> response) {
                if (response.isSuccessful()) {
                    estadoActual.postValue(inmueble.getEstado());
                    resultadoExito.postValue(true);
                } else {
                    resultadoError.postValue("Error al actualizar estado. Código: " + response.code());
                    Log.e("DetalleVM", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Inmuebles> call, Throwable t) {
                resultadoError.postValue("Fallo de red: " + t.getMessage());
                Log.e("DetalleVM", "Fallo: " + t.getMessage());
            }
        });
    }
}
