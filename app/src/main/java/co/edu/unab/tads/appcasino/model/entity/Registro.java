package co.edu.unab.tads.appcasino.model.entity;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;

public class Registro implements Serializable {
    private String rid;
    private Date fecha;
    private String empleadoId;
    private String eventoId;
    private String usuarioId;
    private Empleado myEmpleado;
    private Evento myEvento;
    private Usuario myUsuario;

    public Registro() {
        this.rid = "";
        this.fecha = new Date() ;
        this.empleadoId = "";
        this.eventoId = "";
        this.usuarioId = "";
        this.myEmpleado = new Empleado();
        this.myEvento = new Evento();
        this.myUsuario = new Usuario();
    }
    @Exclude
    public String getRid() {
        return rid;
    }
    @Exclude
    public void setRid(String rid) {
        this.rid = rid;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(String empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getEventoId() {
        return eventoId;
    }

    public void setEventoId(String eventoId) {
        this.eventoId = eventoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
    @Exclude
    public Empleado getMyEmpleado() {
        return myEmpleado;
    }
    @Exclude
    public void setMyEmpleado(Empleado myEmpleado) {
        this.myEmpleado = myEmpleado;
    }
    @Exclude
    public Evento getMyEvento() {
        return myEvento;
    }
    @Exclude
    public void setMyEvento(Evento myEvento) {
        this.myEvento = myEvento;
    }
    @Exclude
    public Usuario getMyUsuario() {
        return myUsuario;
    }
    @Exclude
    public void setMyUsuario(Usuario myUsuario) {
        this.myUsuario = myUsuario;
    }
}
