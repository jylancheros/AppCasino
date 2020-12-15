package co.edu.unab.tads.appcasino.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import co.edu.unab.tads.appcasino.R;
import co.edu.unab.tads.appcasino.databinding.ActivityRegistroUsuarioBinding;
import co.edu.unab.tads.appcasino.model.entity.Usuario;
import co.edu.unab.tads.appcasino.viewmodel.RegistroViewModel;

public class RegistroUsuarioActivity extends AppCompatActivity {
    private ActivityRegistroUsuarioBinding registroBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        RegistroViewModel viewModel = new ViewModelProvider(RegistroUsuarioActivity.this).get(RegistroViewModel.class);
        registroBinding = DataBindingUtil.setContentView(RegistroUsuarioActivity.this, R.layout.activity_registro_usuario);
        registroBinding.setUser(new Usuario());

        registroBinding.btRegisterForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario myUser = registroBinding.getUser();
                String pass = registroBinding.getPassword();
                viewModel.signUp(myUser, pass);
                viewModel.getCurrentUser().observe(RegistroUsuarioActivity.this, new Observer<Usuario>() {
                    @Override
                    public void onChanged(Usuario user) {
                        if(user!=null){
                            finish();
                        }else{

                        }
                    }
                });
                finish();
            }
        });
    }
}