package co.edu.unab.tads.appcasino.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import co.edu.unab.tads.appcasino.model.entity.Registro;
import co.edu.unab.tads.appcasino.model.repository.RegistroRepository;

public class EventoListViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Registro>> listRegistros;

    private RegistroRepository registroRepository;

    public EventoListViewModel( Application application) {
        super(application);
        listRegistros = new MutableLiveData<>();
        registroRepository = new RegistroRepository(application);
    }

    public LiveData<List<Registro>> getListRegister() {
        return registroRepository.getRegistros();
    }

    public void loadRegistros(String fecha){
        try {
            registroRepository.loadRegistros(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void loadRegistros(){
        registroRepository.loadRegistros();
    }
}