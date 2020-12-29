package co.edu.unab.tads.appcasino.model.entity;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Registro implements Serializable {
    private String rid;
    private Date fecha;
    private String empleadoId;
    private String usuarioId;
    private Empleado myEmpleado;
    private String evento;
    private Usuario myUsuario;

    public Registro() {
        this.rid = "";
        this.fecha = new Date() ;
        this.empleadoId = "";
        this.usuarioId = "";
        this.myEmpleado = new Empleado();
        this.evento =  "";
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

    public String getFechaString() {
        String pattern = "dd/MM/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        Date fec = calendar.getTime();
        return df.format(fec);
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
    public Usuario getMyUsuario() {
        return myUsuario;
    }
    @Exclude
    public void setMyUsuario(Usuario myUsuario) {
        this.myUsuario = myUsuario;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }
}
