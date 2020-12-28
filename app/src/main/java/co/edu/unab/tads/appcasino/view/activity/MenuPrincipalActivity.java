package co.edu.unab.tads.appcasino.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import co.edu.unab.tads.appcasino.R;
import co.edu.unab.tads.appcasino.databinding.ActivityMainBinding;
import co.edu.unab.tads.appcasino.databinding.ActivityMenuPrincipalBinding;

public class MenuPrincipalActivity extends AppCompatActivity {

    ActivityMenuPrincipalBinding menuPrincipalBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        menuPrincipalBinding = DataBindingUtil.setContentView(MenuPrincipalActivity.this, R.layout.activity_menu_principal);

        menuPrincipalBinding.btRegistrarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuPrincipalActivity.this, RegistrarServiciosActivity.class);
                startActivity(myIntent);
            }
        });

        menuPrincipalBinding.btListRegistros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuPrincipalActivity.this, EventosListActivity.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.list_empleados:
                startActivity(new Intent(MenuPrincipalActivity.this,EmpleadoListActivity.class));
                break;
            case R.id.mi_cerrar_sesion:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent i = new Intent(MenuPrincipalActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}