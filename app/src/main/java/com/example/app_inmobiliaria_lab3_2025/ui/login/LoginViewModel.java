package com.example.app_inmobiliaria_lab3_2025.ui.login;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;



import com.example.app_inmobiliaria_lab3_2025.modelo.LoginView;
import com.example.app_inmobiliaria_lab3_2025.modelo.TokenResponse;
import com.example.app_inmobiliaria_lab3_2025.request.ApiClient;
import com.example.app_inmobiliaria_lab3_2025.MainActivity;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private Context context;
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

   /* public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }*/



    public LiveData<String> getError() {
        if (error == null) {
            error = new MutableLiveData<>();
        }
        return error;
    }

    public void login(String usuario, String clave) {
        LoginView loginView= new LoginView(usuario,clave);
        Call<TokenResponse> respuestaToken= ApiClient.getApiClientInterface().login(loginView);
        respuestaToken.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    String token = response.body().getToken();
                    ApiClient.guardarToken(getApplication(), token);
                    loginExitoso.setValue(true);

                    ApiClient.guardarToken(getApplication(), token);

                   /* SharedPreferences sp = context.getSharedPreferences("token.dat", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("token", "Bearer " + response.body());
                    editor.commit();
                    */
                    usuarioLogueado();
                }
                else{
                    error.setValue("Usuario y/o Contraseña Incorrectos!");
                }
            }


            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d("token", "algo falla "+ t.getMessage());
            }
        });

    }

    public void usuarioLogueado(){
        Toast.makeText(context, "Login exitoso", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}