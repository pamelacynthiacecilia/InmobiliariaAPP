package com.example.app_inmobiliaria_lab3_2025.modelo;
import java.io.Serializable;
import java.util.Date;

public class Contratos

    implements Serializable {
        private int id;
        private int idInquilino;
        private int idInmueble;
        private double precio;
        private Date fechaInicio;
        private Date fechaFin;

        private Inquilinos inquilinoContrato; // si lo estás incluyendo en la respuesta

        // Getters y setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public int getIdInquilino() { return idInquilino; }
        public void setIdInquilino(int idInquilino) { this.idInquilino = idInquilino; }

        public int getIdInmueble() { return idInmueble; }
        public void setIdInmueble(int idInmueble) { this.idInmueble = idInmueble; }

        public double getPrecio() { return precio; }
        public void setPrecio(double precio) { this.precio = precio; }

        public Date getFechaInicio() { return fechaInicio; }
        public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

        public Date getFechaFin() { return fechaFin; }
        public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

        public Inquilinos getInquilinoContrato() { return inquilinoContrato; }
        public void setInquilinoContrato(Inquilinos inquilinoContrato) { this.inquilinoContrato = inquilinoContrato; }

}
