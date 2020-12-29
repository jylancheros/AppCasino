package co.edu.unab.tads.appcasino.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.tads.appcasino.R;
import co.edu.unab.tads.appcasino.databinding.ActivityEventosListBinding;
import co.edu.unab.tads.appcasino.databinding.ActivityMenuPrincipalBinding;
import co.edu.unab.tads.appcasino.model.entity.Registro;
import co.edu.unab.tads.appcasino.view.adapter.RegistroAdapter;
import co.edu.unab.tads.appcasino.view.components.DatePickerFragment;
import co.edu.unab.tads.appcasino.viewmodel.EventoListViewModel;

public class EventosListActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_REGISTER_DETAIL = 100;
    private RegistroAdapter myAdapter;
    ActivityEventosListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_list);

        binding = DataBindingUtil.setContentView(EventosListActivity.this, R.layout.activity_eventos_list);

        binding.fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        EventoListViewModel viewModel = new ViewModelProvider(EventosListActivity.this).get(EventoListViewModel.class);

        binding.setViewModel(viewModel);
        myAdapter = new RegistroAdapter(new ArrayList<>());

        viewModel.getListRegister().observe(EventosListActivity.this, new Observer<List<Registro>>() {
            @Override
            public void onChanged(List<Registro> registros) {
                if (registros.isEmpty()) {
                }
                myAdapter.setRegistros((ArrayList<Registro>) registros);
            }
        });

        binding.rvEventos.setLayoutManager(new LinearLayoutManager(EventosListActivity.this));
        binding.rvEventos.setHasFixedSize(true);
        binding.rvEventos.setAdapter(myAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!binding.fecha.getText().toString().isEmpty()) {
            binding.getViewModel().loadRegistros(binding.fecha.toString());
        }else{
            binding.getViewModel().loadRegistros();
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final String selectedDate = dayOfMonth + "/" + (month+1) + "/" + year;
                binding.fecha.setText(selectedDate);
                binding.getViewModel().loadRegistros(selectedDate);
            }
        });

        newFragment.show(EventosListActivity.this.getSupportFragmentManager(), "datePicker");
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
                startActivity(new Intent(EventosListActivity.this,EmpleadoListActivity.class));
                break;
            case R.id.registro:
                startActivity(new Intent(EventosListActivity.this,RegistrarServiciosActivity.class));
                break;
            case R.id.list_registro:
                startActivity(new Intent(EventosListActivity.this, EventosListActivity.class));
                break;
            case R.id.mi_cerrar_sesion:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent i = new Intent(EventosListActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}