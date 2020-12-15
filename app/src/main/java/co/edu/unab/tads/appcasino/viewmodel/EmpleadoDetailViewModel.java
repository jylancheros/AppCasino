package co.edu.unab.tads.appcasino.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import co.edu.unab.tads.appcasino.model.entity.Empleado;
import co.edu.unab.tads.appcasino.model.repository.EmpleadoRepository;
import co.edu.unab.tads.appcasino.model.repository.UsuarioRepository;

public class EmpleadoDetailViewModel extends AndroidViewModel {
    private EmpleadoRepository empleadoRepository;
    private UsuarioRepository userRepository;
    public EmpleadoDetailViewModel(@NonNull Application application) {
        super(application);
        empleadoRepository= new EmpleadoRepository(application);
        userRepository = new UsuarioRepository(application);
    }


    public void removeEmpleado(Empleado empleado) {
        empleadoRepository.removeFS(empleado);
    }


}
