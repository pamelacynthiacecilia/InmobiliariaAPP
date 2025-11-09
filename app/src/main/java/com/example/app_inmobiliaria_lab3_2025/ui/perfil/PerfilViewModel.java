package com.example.app_inmobiliaria_lab3_2025.ui.perfil;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_inmobiliaria_lab3_2025.modelo.Propietarios;
import com.example.app_inmobiliaria_lab3_2025.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private final MutableLiveData<Propietarios> propietarioMutable = new MutableLiveData<>();
    private final MutableLiveData<String> mensajeMutable = new MutableLiveData<>();
    private final Application app;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        this.app = application;
    }

    public LiveData<Propietarios> getPropietarioMutable() {
        return propietarioMutable;
    }

    public LiveData<String> getMensajeMutable() {
        return mensajeMutable;
    }

    // Obtener usuario actual
    public void obtenerUsuarioActual() {
        String token = ApiClient.leerToken(app);

        if (token == null || token.isEmpty()) {
            mensajeMutable.postValue("Token no encontrado");
            return;
        }

        Call<Propietarios> call = ApiClient.getApiClientInterface()
                .usuarioActual("Bearer " + token);

        call.enqueue(new Callback<Propietarios>() {
            @Override
            public void onResponse(Call<Propietarios> call, Response<Propietarios> response) {
                if (response.isSuccessful()) {
                    Propietarios propietario = response.body();
                    if (propietario != null) {
                        propietarioMutable.postValue(propietario);
                        // mensajeMutable.postValue("Usuario cargado correctamente");
                    } else {
                        mensajeMutable.postValue("Propietario NULO en body");
                    }
                } else {
                    mensajeMutable.postValue("Error en respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Propietarios> call, Throwable t) {
                mensajeMutable.postValue("Fallo en API: " + t.getMessage());
                Log.e("PerfilViewModel", "Error API usuarioActual", t);
            }
        });
    }

    // Editar propietario
    public void editarPropietario(Propietarios propietario) {
        String token = ApiClient.leerToken(app);

        if (token == null || token.isEmpty()) {
            mensajeMutable.postValue("Token no encontrado para editar");
            return;
        }

        Call<Propietarios> call = ApiClient.getApiClientInterface()
                .editar("Bearer " + token, propietario);

        call.enqueue(new Callback<Propietarios>() {
            @Override
            public void onResponse(Call<Propietarios> call, Response<Propietarios> response) {
                if (response.isSuccessful()) {
                    Propietarios actualizado = response.body();
                    if (actualizado != null) {
                        propietarioMutable.postValue(actualizado);
                        mensajeMutable.postValue("Propietario actualizado correctamente");
                    } else {
                        mensajeMutable.postValue("Propietario NULO en body tras editar");
                    }
                } else {
                    mensajeMutable.postValue("Error al editar: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Propietarios> call, Throwable t) {
                mensajeMutable.postValue("Fallo en API editar: " + t.getMessage());
                Log.e("PerfilViewModel", "Error API editarPropietario", t);
            }
        });
    }
}