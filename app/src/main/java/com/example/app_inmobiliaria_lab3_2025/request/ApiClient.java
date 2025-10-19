package com.example.app_inmobiliaria_lab3_2025.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.app_inmobiliaria_lab3_2025.modelo.LoginView;
import com.example.app_inmobiliaria_lab3_2025.modelo.Propietarios;
import com.example.app_inmobiliaria_lab3_2025.modelo.TokenResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ApiClient {

    //Guardar token
    public static void guardarToken(Context context, String token){
        SharedPreferences sp= context.getSharedPreferences("token",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.putString("token",token);
        editor.apply();
    }

    //Leer token
    public static String leerToken(Context context){
        SharedPreferences sp= context.getSharedPreferences("token", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }

    //Guardar Correo usuario logueado
    public static void guardarEmail(Context context, String email){
        SharedPreferences sp= context.getSharedPreferences("correoPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.putString("correoPref",email);
        editor.apply();
    }

    //Leer email usuario logueado

    public static String leerEmail(Context context){
        SharedPreferences sp= context.getSharedPreferences("correoPref", Context.MODE_PRIVATE);
        return sp.getString("correoPref", null);
    }




    //Consumo API

    private static final String PATH = "http://192.168.1.6:5000/api/";
    private static MyApiInterfaceService myApiInterfaceInmobiliaria;

    // genera un Objeto que implementa la interface
    public static MyApiInterfaceService getApiClientInterface() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        myApiInterfaceInmobiliaria = retrofit.create(MyApiInterfaceService.class);

        Log.d("salida interface retrofit", retrofit.baseUrl().toString());
        return myApiInterfaceInmobiliaria;
    }


    public interface MyApiInterfaceService {

        @POST("propietario/login")
        Call<TokenResponse> login(@Body LoginView loginView);


        @GET("propietario/usuarioActual")
        Call<Propietarios>usuarioActual(@Header("Authorization")String token);


        @PUT("propietario/editar")
        Call<Propietarios>editar(@Header("Authorization") String token,@Body Propietarios propietario);



    }
}
