package com.example.app_inmobiliaria_lab3_2025.modelo;

public class Propietarios {

    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;

    // Constructor vacío
    public Propietarios() {}

    // Constructor para crear un propietario nuevo sin id
    public Propietarios(String Dni, String Nombre, String Apellido, String Email, String Password, String Telefono) {
        this.dni = Dni;
        this.nombre = Nombre;
        this.apellido = Apellido;
        this.email = Email;
        this.password = Password;
        this.telefono = Telefono;
    }

    // Constructor con id
    public Propietarios(int Id, String Dni, String Nombre, String Apellido, String Email, String Telefono) {
        this.id = Id;
        this.dni = Dni;
        this.nombre = Nombre;
        this.apellido = Apellido;
        this.email = Email;
        this.telefono = Telefono;
    }

    // Getters y setters
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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
        this.password = contraseña;
    }

    public String getTel() {
        return telefono;
    }

    public void setTel(String telefono) {
        this.telefono = telefono;
    }
}
