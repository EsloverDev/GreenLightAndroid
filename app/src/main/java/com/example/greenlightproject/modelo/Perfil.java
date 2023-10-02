package com.example.greenlightproject.modelo;

public class Perfil {

    private int idPerfil;
    private String nombre;
    private String password;
    private String email;
    private String telefono;
    private String pais;
    private String ciudad;
    private String localidad;
    private String documento;

    public Perfil() {
    }

    public Perfil(int idPerfil, String nombre, String password, String email, String telefono, String pais, String ciudad, String localidad, String documento) {
        this.idPerfil = idPerfil;
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.telefono = telefono;
        this.pais = pais;
        this.ciudad = ciudad;
        this.localidad = localidad;
        this.documento = documento;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "idPerfil=" + idPerfil +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", pais='" + pais + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", localidad='" + localidad + '\'' +
                ", documento='" + documento + '\'' +
                '}';
    }
}
