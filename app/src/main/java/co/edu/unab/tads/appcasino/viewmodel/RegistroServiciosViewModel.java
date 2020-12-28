package co.edu.unab.tads.appcasino.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;

import co.edu.unab.tads.appcasino.model.entity.Empleado;
import co.edu.unab.tads.appcasino.model.repository.RegistroRepository;

public class RegistroServiciosViewModel extends AndroidViewModel {
    private RegistroRepository registroRepository;
    public RegistroServiciosViewModel(@NonNull Application application) {
        super(application);
        registroRepository = new RegistroRepository(application);
    }

    public void agregarServicio(String empleadoId, String Evento, Date fechaHora) {
        registroRepository.addRegistro(empleadoId,Evento,fechaHora);
    }

    public LiveData<Boolean> getReady() {
        return registroRepository.getReady();
    }
    public void setReady() {
       registroRepository.setReady();
    }

    public Empleado getEmpleado() {
        return registroRepository.getEmpleado();
    }

    public void getEmpleadoByCedula(String cedula) {
        registroRepository.loadEmpleadoByCedula(cedula);
    }

}
