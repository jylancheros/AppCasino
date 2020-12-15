package co.edu.unab.tads.appcasino.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import co.edu.unab.tads.appcasino.R;
import co.edu.unab.tads.appcasino.databinding.ActivityMainBinding;
import co.edu.unab.tads.appcasino.model.entity.Usuario;
import co.edu.unab.tads.appcasino.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        MainViewModel viewModel = new ViewModelProvider(MainActivity.this).get(MainViewModel.class);
        mainBinding = DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);


        mainBinding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mainBinding.getEmail();
                String password = mainBinding.getPassword();
                viewModel.signIn(email, password);
                viewModel.getCurrentUser().observe(MainActivity.this, new Observer<Usuario>() {
                    @Override
                    public void onChanged(Usuario user) {
                        if(user != null){
                            Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(MainActivity.this, EmpleadoListActivity.class);
                            startActivity(myIntent);
                        }else{
                            Toast.makeText(MainActivity.this, "Datos Errados", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        mainBinding.btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, RegistroUsuarioActivity.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(auth.getCurrentUser()!= null){
            Intent i = new Intent(MainActivity.this,EmpleadoListActivity.class);
            startActivity(i);
            finish();
        }
    }
}