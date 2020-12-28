package co.edu.unab.tads.appcasino.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import co.edu.unab.tads.appcasino.R;
import co.edu.unab.tads.appcasino.databinding.ActivityRegistrarServiciosBinding;
import co.edu.unab.tads.appcasino.model.entity.Empleado;
import co.edu.unab.tads.appcasino.viewmodel.EmpleadoFormViewModel;
import co.edu.unab.tads.appcasino.viewmodel.RegistroServiciosViewModel;

public class RegistrarServiciosActivity extends AppCompatActivity {
    private ActivityRegistrarServiciosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_servicios);
        binding = DataBindingUtil.setContentView(RegistrarServiciosActivity.this, R.layout.activity_registrar_servicios);
        //binding.tcFechaHoraRegistro.setText(new Date().toString());
        RegistroServiciosViewModel viewModel = new ViewModelProvider(RegistrarServiciosActivity.this).get(RegistroServiciosViewModel.class);


        ArrayList<String> eventos = new ArrayList<String>();
        eventos.add("Desayuno");
        eventos.add("Almuerzo");
        eventos.add("Cena");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.textview_spinner, eventos);
        adapter.setDropDownViewResource(R.layout.textview_spinner);

        binding.eventosSpinner.setAdapter(adapter);

        binding.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cedula = binding.etCedulaForm.getText().toString().replace(" ","");
                //Toast.makeText(RegistrarServiciosActivity.this, cedula, Toast.LENGTH_SHORT).show();
                if (cedula == "") {
                    Toast.makeText(RegistrarServiciosActivity.this, "Digite la cedula del empleado", Toast.LENGTH_SHORT).show();
                } else {

                    viewModel.getEmpleadoByCedula(cedula);
                    viewModel.getReady().observe(RegistrarServiciosActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean b) {
                            if(b){
                                viewModel.setReady();
                                Empleado e = new Empleado();
                                e = viewModel.getEmpleado();
                                if ( e.getNombre() != ""){
                                    binding.tcCedulaEmpleado.setText(e.getCedula());
                                    binding.tcNombreEmpleado.setText(e.getNombre());
                                    String pattern = "dd/MM/yyyy HH:mm:ss";
                                    DateFormat df = new SimpleDateFormat(pattern);
                                    Date today = Calendar.getInstance().getTime();
                                    String todayAsString = df.format(today);
                                    binding.tcFechaHoraRegistro.setText(todayAsString);
                                    viewModel.agregarServicio(e.getEid(),binding.eventosSpinner.getSelectedItem().toString(),new Date());
                                    Toast.makeText(RegistrarServiciosActivity.this, "Registro guardado", Toast.LENGTH_SHORT).show();
                                    binding.etCedulaForm.setText("");
                                    binding.etCedulaForm.setFocusable(true);
                                    binding.etCedulaForm.requestFocus();

                                    //finish();

                                }else{
                                    viewModel.setReady();
                                    Toast.makeText(RegistrarServiciosActivity.this, "No se encontro empleado", Toast.LENGTH_SHORT).show();
                                    binding.tcCedulaEmpleado.setText("NA");
                                    binding.tcNombreEmpleado.setText("NA");
                                    binding.tcFechaHoraRegistro.setText("");
                                }
                            }

                        }
                    });


                }


            }
        });


    }
}