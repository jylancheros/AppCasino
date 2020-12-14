package co.edu.unab.tads.appcasino.model.entity;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String uid;
    private String nombre;
    private String email;
    private String telefono;

    public Usuario(String uid, String nombre, String email, String telefono) {
        this.uid = uid;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public Usuario() {
        this.uid = "";
        this.nombre = "";
        this.email = "";
        this.telefono = "";
    }

    @Exclude
    public String getUid() {
        return uid;
    }
    @Exclude
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
