package co.edu.unab.tads.appcasino.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.tads.appcasino.model.entity.Empleado;
import co.edu.unab.tads.appcasino.model.repository.EmpleadoRepository;

public class EmpleadoListViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Empleado>> listEmpleado;

    private EmpleadoRepository empleadoRepository;
    public EmpleadoListViewModel(@NonNull Application application) {
        super(application);
        listEmpleado = new MutableLiveData<>();
        empleadoRepository = new EmpleadoRepository(application);
    }
    public LiveData<List<Empleado>> getListEmpleados() {
        return empleadoRepository.getEmpleados();
    }

    public void loadEmpleados(){
        empleadoRepository.loadEmpleados();

    }

}
