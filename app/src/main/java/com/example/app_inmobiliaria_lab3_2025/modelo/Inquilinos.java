package com.example.app_inmobiliaria_lab3_2025.modelo;

import java.io.Serializable;

public class Inquilinos implements Serializable {
    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private String tel;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

}
