package com.example.app_inmobiliaria_lab3_2025.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.app_inmobiliaria_lab3_2025.modelo.LoginView;
import com.example.app_inmobiliaria_lab3_2025.modelo.Propietario;
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
        SharedPreferences sp= context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
         return sp.getString("token", null);
    }



    //Consumo API

    private static final String PATH = "http://192.168.1.6:5000/api/";
    private static MyApiInterfaceService myApiInterfaceInmobiliaria;

    // genera un Objeto que implementa la interface
    public static MyApiInterfaceService getApiClientInterface() {

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        myApiInterfaceInmobiliaria = retrofit.create(MyApiInterfaceService.class);

        Log.d("salida", retrofit.baseUrl().toString());
        return myApiInterfaceInmobiliaria;
    }


    public interface MyApiInterfaceService {

       /* @FormUrlEncoded
        @POST("propietario/login")
        Call<String> login(@Field("usuario") String usuario, @Field("clave") String clave);

        @POST("propietario/login")
        Call<String> login(@Body LoginView loginView);*/

        @POST("propietario/login")
        Call<TokenResponse> login(@Body LoginView loginView);


        @GET("propietario/obtenerUsuario")
        Call<Propietario> obtenerPropietario(@Header("Authorization")@Body int id);

        Call<String> login(String usuario, String clave);
    }
}
