package com.example.app_inmobiliaria_lab3_2025.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_inmobiliaria_lab3_2025.modelo.Inmuebles;
import com.example.app_inmobiliaria_lab3_2025.request.ApiClient;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> resultadoExito = new MutableLiveData<>();
    private MutableLiveData<String> resultadoError = new MutableLiveData<>();

    public CrearInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getResultadoExito() {
        return resultadoExito;
    }

    public LiveData<String> getResultadoError() {
        return resultadoError;
    }
    public void crearInmueble(String tipo, String uso, String direccion, String ambientes, String precio, int estado, Uri imagenUri) {
        Context context = getApplication().getApplicationContext();
        String token = ApiClient.leerToken(context);

        if (token == null || token.isEmpty()) {
            resultadoError.postValue("Token no disponible. Iniciá sesión nuevamente.");
            return;
        }

        token = "Bearer " + token;

        if (tipo.isEmpty() || uso.isEmpty() || direccion.isEmpty() || ambientes.isEmpty() || precio.isEmpty()) {
            resultadoError.postValue("Todos los campos son obligatorios");
            return;
        }

        RequestBody tipoBody = RequestBody.create(MediaType.parse("text/plain"), tipo);
        RequestBody usoBody = RequestBody.create(MediaType.parse("text/plain"), uso);
        RequestBody direccionBody = RequestBody.create(MediaType.parse("text/plain"), direccion);
        RequestBody ambientesBody = RequestBody.create(MediaType.parse("text/plain"), ambientes);
        RequestBody precioBody = RequestBody.create(MediaType.parse("text/plain"), precio);
        RequestBody estadoBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(estado));

        MultipartBody.Part imagenPart = null;
        if (imagenUri != null) {
            try {
                File imagenFile = ApiClient.getFileFromUri(context, imagenUri);
                RequestBody imagenBody = RequestBody.create(MediaType.parse("image/*"), imagenFile);
                imagenPart = MultipartBody.Part.createFormData("ImagenFile", imagenFile.getName(), imagenBody);
            } catch (IOException e) {
                resultadoError.postValue("Error al procesar la imagen: " + e.getMessage());
                return;
            }
        }

        Call<Inmuebles> call = (imagenPart != null)
                ? ApiClient.getApiClientInterface().crearInmueble(token, imagenPart, tipoBody, usoBody, direccionBody, ambientesBody, precioBody, estadoBody)
                : ApiClient.getApiClientInterface().crearInmuebleSinImagen(token, tipoBody, usoBody, direccionBody, ambientesBody, precioBody, estadoBody);

        call.enqueue(new Callback<Inmuebles>() {
            @Override
            public void onResponse(Call<Inmuebles> call, Response<Inmuebles> response) {
                if (response.isSuccessful()) {
                    resultadoExito.postValue(true);
                } else {
                    String mensajeError = "Error desconocido";
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            mensajeError = errorBody.isEmpty() ? "Error sin mensaje del servidor" : errorBody;
                        } else {
                            mensajeError = "Error sin cuerpo de respuesta";
                        }
                    } catch (IOException e) {
                        mensajeError = "Error al leer el mensaje del servidor: " + e.getMessage();
                    }
                    resultadoError.postValue(mensajeError);
                }
            }

            @Override
            public void onFailure(Call<Inmuebles> call, Throwable t) {
                resultadoError.postValue("Fallo de conexión: " + t.getMessage());
            }
        });
    }

    /*public void crearInmueble(String tipo, String uso, String direccion, String ambientes, String precio, int estado, Uri imagenUri) {
        Context context = getApplication().getApplicationContext();
        String token = ApiClient.leerToken(context);

        if (token == null || token.isEmpty()) {
            Toast.makeText(context, "Token no disponible. Iniciá sesión nuevamente.", Toast.LENGTH_LONG).show();
            return;
        }

        token = "Bearer " + token;

        // Validaciones básicas
        if (tipo.isEmpty() || uso.isEmpty() || direccion.isEmpty() || ambientes.isEmpty() || precio.isEmpty()) {
            Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody tipoBody = RequestBody.create(MediaType.parse("text/plain"), tipo);
        RequestBody usoBody = RequestBody.create(MediaType.parse("text/plain"), uso);
        RequestBody direccionBody = RequestBody.create(MediaType.parse("text/plain"), direccion);
        RequestBody ambientesBody = RequestBody.create(MediaType.parse("text/plain"), ambientes);
        RequestBody precioBody = RequestBody.create(MediaType.parse("text/plain"), precio);
        RequestBody estadoBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(estado));

        MultipartBody.Part imagenPart = null;
        if (imagenUri != null) {
            try {
                File imagenFile = ApiClient.getFileFromUri(context, imagenUri);
                RequestBody imagenBody = RequestBody.create(MediaType.parse("image/*"), imagenFile);
                imagenPart = MultipartBody.Part.createFormData("ImagenFile", imagenFile.getName(), imagenBody);
            } catch (IOException e) {
                Toast.makeText(context, "Error al procesar la imagen", Toast.LENGTH_SHORT).show();
                Log.e("CrearInmueble", "IOException: " + e.getMessage());
                return;
            }
        }

        Call<Inmuebles> call = (imagenPart != null)
                ? ApiClient.getApiClientInterface().crearInmueble(token, imagenPart, tipoBody, usoBody, direccionBody, ambientesBody, precioBody, estadoBody)
                : ApiClient.getApiClientInterface().crearInmuebleSinImagen(token, tipoBody, usoBody, direccionBody, ambientesBody, precioBody, estadoBody);

        call.enqueue(new Callback<Inmuebles>() {
            @Override
            public void onResponse(Call<Inmuebles> call, Response<Inmuebles> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Inmueble creado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    String mensajeError = "Error desconocido";
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            mensajeError = errorBody.isEmpty() ? "Error sin mensaje del servidor" : errorBody;
                        } else {
                            mensajeError = "Error sin cuerpo de respuesta";
                        }
                    } catch (IOException e) {
                        mensajeError = "Error al leer el mensaje del servidor: " + e.getMessage();
                    }
                    Log.e("CrearInmueble", "Error: " + mensajeError);
                    Toast.makeText(context, "Error: " + mensajeError, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Inmuebles> call, Throwable t) {
                String mensaje = "Fallo de conexión: " + t.getMessage();
                Log.e("CrearInmueble", "onFailure: " + mensaje);
                Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
            }
        });
    }
}


     */
}