package com.example.app_inmobiliaria_lab3_2025.modelo;

import java.util.Objects;

public class Propietario {

    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;

    public Propietario(){}
    public Propietario( String dni, String nombre, String apellido, String email, String password, String telefono) {
        // this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
    }

    public Propietario(int id, String dni, String nombre, String apellido, String email,  String telefono) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return password;
    }

    public void setClave(String contraseña) {
        this.password = password;
    }

    public String getTel() {
        return telefono;
    }

    public void setTel(String telefono) {
        this.telefono = telefono;
    }


}