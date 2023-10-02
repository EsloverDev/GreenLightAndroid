package com.example.greenlightproject.casosDeUso;

import com.example.greenlightproject.modelo.Perfil;

public class PerfilesCasosDeUso {

    Perfil perf = null;

    public PerfilesCasosDeUso(){
        Perfil perf = new Perfil();
    }

    public Perfil crearPerfil(String nombre, String password, String email, String telefono, String pais, String ciudad, String localidad, String documento){

        perf.setNombre(nombre);
        perf.setPassword(password);
        perf.setEmail(email);
        perf.setTelefono(telefono);
        perf.setPais(pais);
        perf.setCiudad(ciudad);
        perf.setLocalidad(localidad);
        perf.setDocumento(documento);

        return perf;
    }

}
