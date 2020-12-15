package co.edu.unab.tads.appcasino.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import co.edu.unab.tads.appcasino.model.entity.Usuario;
import co.edu.unab.tads.appcasino.model.repository.UsuarioRepository;

public class MainViewModel extends AndroidViewModel {
    private UsuarioRepository usuarioRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository(application);

    }

    public LiveData<Usuario> getCurrentUser(){
        return usuarioRepository.getCurrentUsusario();
    }

    public void signIn(String email, String pass){
        usuarioRepository.SignIn(email, pass);
    }
}
