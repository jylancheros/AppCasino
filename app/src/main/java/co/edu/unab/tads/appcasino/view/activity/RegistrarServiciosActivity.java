package co.edu.unab.tads.appcasino.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Date;

import co.edu.unab.tads.appcasino.R;
import co.edu.unab.tads.appcasino.databinding.ActivityRegistrarServiciosBinding;

public class RegistrarServiciosActivity extends AppCompatActivity {
private ActivityRegistrarServiciosBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_servicios);
        binding = DataBindingUtil.setContentView(RegistrarServiciosActivity.this,R.layout.activity_registrar_servicios);
        binding.tcFechaHoraRegistro.setText(new Date().toString());


        ArrayList<String> eventos = new ArrayList<String>();
        eventos.add("Desayuno");
        eventos.add("Almuerzo");
        eventos.add("Cena");

       ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,R.layout.textview_spinner,eventos);
        adapter.setDropDownViewResource( R.layout.textview_spinner);

        binding.eventosSpinner.setAdapter(adapter);


    }
}