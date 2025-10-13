package com.example.app_inmobiliaria_lab3_2025.modelo;

public class LoginView {
    public String usuario;
    public String clave;

    public LoginView(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }





}