package com.example.app_inmobiliaria_lab3_2025.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.example.app_inmobiliaria_lab3_2025.modelo.Inmuebles;
import com.example.app_inmobiliaria_lab3_2025.modelo.LoginView;
import com.example.app_inmobiliaria_lab3_2025.modelo.Propietarios;
import com.example.app_inmobiliaria_lab3_2025.modelo.TokenResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

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

    public static File getFileFromUri(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        String fileName = "temp_image_" + System.currentTimeMillis() + ".jpg";
        File tempFile = new File(context.getCacheDir(), fileName);
        FileOutputStream outputStream = new FileOutputStream(tempFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.close();
        inputStream.close();

        return tempFile;
    }



    //Consumo API
    //public static final String PATH = "http://10.0.2.2:5000/api/";//virtual
    public static final String PATH = "http://192.168.1.13:5000/api/";

    // public static final String PATH = "http://192.168.1.6:5000/api/";//dispositivo fisico
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

        @GET("inmueble/inmueblesDelPropietario")
        Call<List<Inmuebles>> getInmuebles(@Header("Authorization") String token);

        @FormUrlEncoded
        @PUT("propietario/editarPassword")
        Call<Void> CambiarClave(@Header("Authorization") String token,
                                @Field("claveActual") String claveActual,
                                @Field("nuevaClave") String nuevaClave);

        @PUT("inmueble/modificarEstadoInmueble")
        Call<Inmuebles> modificarDisponible(
                @Header("Authorization") String token,
                @Query("id") int id,
                @Body Inmuebles inmuebles
        );

        /*@Body Inmuebles inmueble*/


        @Multipart
        @POST("inmueble/crearInmueble")
        Call<Inmuebles> crearInmueble(
                @Header("Authorization") String token,
                @Part MultipartBody.Part imagen,
                @Part("tipo") RequestBody tipo,
                @Part("uso") RequestBody uso,
                @Part("direccion") RequestBody direccion,
                @Part("ambientes") RequestBody ambientes,
                @Part("precio") RequestBody precio,
                @Part("estado") RequestBody estado
        );


        @Multipart
        @POST("inmueble/crearInmueble")
        Call<Inmuebles> crearInmuebleSinImagen(
                @Header("Authorization") String token,
                @Part("tipo") RequestBody tipo,
                @Part("uso") RequestBody uso,
                @Part("direccion") RequestBody direccion,
                @Part("ambientes") RequestBody ambientes,
                @Part("precio") RequestBody precio,
                @Part("estado") RequestBody estado
        );
    }
}
