package com.example.app_inmobiliaria_lab3_2025.modelo;

import java.io.Serializable;

public class Inmuebles implements Serializable {

    private int id;
    private String direccion;
    private String uso;
    private String tipo;
    private String ambientes;
    private double precio;
    private Propietarios propietarioInmueble;
    private int estado;
    private String imageUrl;
    private transient int idPropietario;

    public Inmuebles(int id, String direccion, String uso, String tipo, String ambientes, double precio, Propietarios propietarioInmueble,
                     int estado, String imagenUrl/*, int idPropietario*/) {
        this.id = id;//esta
        this.direccion = direccion;//esta
        this.uso = uso;
        this.tipo = tipo;
        this.ambientes = ambientes;
        this.precio = precio;//esta
        this.propietarioInmueble = propietarioInmueble;
        this.estado = estado;//esto
        this.imageUrl = imagenUrl; //esta
        //this.idPropietario = idPropietario;
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

    public String getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(String ambientes) {
        this.ambientes = ambientes;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /*public int getPropietarioId() {
        return idPropietario;
    }

    public void setPropietarioId(int idPropietario) {
        this.idPropietario = idPropietario;
    }*/


}
