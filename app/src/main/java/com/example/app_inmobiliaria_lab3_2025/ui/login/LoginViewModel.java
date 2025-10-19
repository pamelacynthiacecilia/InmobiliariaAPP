package com.example.app_inmobiliaria_lab3_2025.ui.login;


import android.app.Application;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.app_inmobiliaria_lab3_2025.modelo.LoginView;
import com.example.app_inmobiliaria_lab3_2025.modelo.TokenResponse;
import com.example.app_inmobiliaria_lab3_2025.request.ApiClient;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private Application application;
    private MutableLiveData<String> error;
    private MutableLiveData<Boolean> loginExitoso = new MutableLiveData<>();

    public LiveData<Boolean> getLoginExitoso() {
        return loginExitoso;
    }


    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<String> getError() {
        if (error == null) {
            error = new MutableLiveData<>();
        }
        return error;
    }

    public void login(String usuario, String clave) {
        LoginView loginView= new LoginView(usuario,clave);

        Call<TokenResponse> respuestaToken= ApiClient.getApiClientInterface().login(loginView);

        //CallBack




        respuestaToken.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String token = response.body().getToken();
                    ApiClient.guardarToken(getApplication(), token);
                   // Log.d("LOGIN_VIEW", "El token luego de guardar en preferencia es: " + token);
                    loginExitoso.postValue(true);
                } else {
                    error.postValue("Usuario y/o Contraseña Incorrectos!");
                }
            }




            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d("token", "algo falla "+ t.getMessage());
            }
        });

    }



}