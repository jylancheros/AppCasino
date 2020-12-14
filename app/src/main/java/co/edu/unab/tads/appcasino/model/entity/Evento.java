package co.edu.unab.tads.appcasino.model.entity;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Evento implements Serializable {
    private String evid;
    private String nombre;
    private String descripcion;
    private Boolean estado;


    public Evento(String evid, String nombre, String descripcion, Boolean estado) {
        this.evid = evid;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Evento() {
        this.evid = "";
        this.nombre = "";
        this.descripcion = "";
        this.estado = false;
    }
    @Exclude
    public String getEvid() {
        return evid;
    }
    @Exclude
    public void setEvid(String evid) {
        this.evid = evid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
