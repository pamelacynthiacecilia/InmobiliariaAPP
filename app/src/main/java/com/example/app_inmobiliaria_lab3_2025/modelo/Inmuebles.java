package com.example.app_inmobiliaria_lab3_2025.modelo;

import java.io.Serializable;

public class Inmuebles implements Serializable {

    private int id;
    private String direccion;
    private String uso;
    private String tipo;
    private int cantAmbientes;
    private double precio;
    private Propietarios propietarioInmueble;
    private int disponible;
    private String imagen;
    private int propietarioId;

    public Inmuebles(int id, String direccion, String uso, String tipo, int cantAmbientes, double precio, Propietarios propietarioInmueble,
                    int disponible, String imagen, int propietarioId ) {
        this.id = id;
        this.direccion = direccion;
        this.uso = uso;
        this.tipo = tipo;
        this.cantAmbientes = cantAmbientes;
        this.precio = precio;
        this.propietarioInmueble = propietarioInmueble;
        this.disponible = disponible;
        this.imagen = imagen;
        this.propietarioId = propietarioId;
    }

    public Inmuebles() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantAmbientes() {
        return cantAmbientes;
    }

    public void setAmbientes(int cantAmbientes) {
        this.cantAmbientes = cantAmbientes;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Propietarios getpropietarioInmueble() {
        return propietarioInmueble;
    }

    public void setpropietarioInmueble(Propietarios propietarioInmueble) {
        this.propietarioInmueble = propietarioInmueble;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(int propietarioId) {
        this.propietarioId = propietarioId;
    }



}
