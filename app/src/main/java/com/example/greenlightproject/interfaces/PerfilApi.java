package com.example.greenlightproject.interfaces;



import com.example.greenlightproject.modelo.Perfil;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PerfilApi {

    @POST("api/perfiles")
    Call<Perfil> registrarDatos(@Body Perfil p);
}
