package co.edu.unab.tads.appcasino.model.entity;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;

public class Empleado implements Serializable {
    private String eid;
    private String cedula;
    private String nombre;
    private String cargo;
    private String telefono;
    private String urlImagen;
    private String usuarioId;
    private Usuario usuario;

    public Empleado(String eid, String cedula, String nombre, String cargo, String telefono,String urlImagen) {
        this.eid = eid;
        this.cedula = cedula;
        this.nombre = nombre;
        this.cargo = cargo;
        this.telefono = telefono;
        this.urlImagen= urlImagen;
    }

    public Empleado() {
        this.eid = "";
        this.cedula = "";
        this.nombre = "";
        this.cargo = "";
        this.telefono = "";
        this.urlImagen="";
    }
    @Exclude
    public String getEid() {
        return eid;
    }
    @Exclude
    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @PropertyName("url_imagen")
    public String getUrlImagen() {
        return urlImagen;
    }

    @PropertyName("url_imagen")
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
    @Exclude
    public Usuario getUsuario() {
        return usuario;
    }
    @Exclude
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
