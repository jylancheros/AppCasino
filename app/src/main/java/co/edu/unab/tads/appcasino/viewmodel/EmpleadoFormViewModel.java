package co.edu.unab.tads.appcasino.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import co.edu.unab.tads.appcasino.model.entity.Empleado;
import co.edu.unab.tads.appcasino.model.repository.EmpleadoRepository;

public class EmpleadoFormViewModel extends AndroidViewModel {
    private EmpleadoRepository empleadoRepository;
    public EmpleadoFormViewModel(@NonNull Application application) {
        super(application);
        empleadoRepository = new EmpleadoRepository(application);
    }
    public void addEmpleado(Empleado e, Uri imageUri) {
        empleadoRepository.addFS(e, imageUri);
    }

    public void editEmpleado(Empleado e, Uri imageUri) {
        empleadoRepository.updateFS(e, imageUri);
    }

    public LiveData<Boolean> getReady() {
        return empleadoRepository.getReady();
    }

}
