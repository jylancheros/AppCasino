package co.edu.unab.tads.appcasino.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.tads.appcasino.R;
import co.edu.unab.tads.appcasino.databinding.ActivityEmpleadoListBinding;
import co.edu.unab.tads.appcasino.model.entity.Empleado;
import co.edu.unab.tads.appcasino.view.adapter.EmpleadoAdapter;
import co.edu.unab.tads.appcasino.viewmodel.EmpleadoListViewModel;

public class EmpleadoListActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_EMPLEADO_DETAIL = 300;
    private EmpleadoAdapter myAdapter;
    private ActivityEmpleadoListBinding empleadoListBinding;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.list_empleados:
                startActivity(new Intent(EmpleadoListActivity.this,EmpleadoListActivity.class));
                break;
            case R.id.registro:
                startActivity(new Intent(EmpleadoListActivity.this,RegistrarServiciosActivity.class));
                break;
            case R.id.list_registro:
                startActivity(new Intent(EmpleadoListActivity.this, EventosListActivity.class));
                break;
            case R.id.mi_cerrar_sesion:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent i = new Intent(EmpleadoListActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado_list);

        empleadoListBinding = DataBindingUtil.setContentView(EmpleadoListActivity.this, R.layout.activity_empleado_list);


        EmpleadoListViewModel viewModel = new ViewModelProvider(EmpleadoListActivity.this).get(EmpleadoListViewModel.class);

        empleadoListBinding.setViewModel(viewModel);
        myAdapter = new EmpleadoAdapter(new ArrayList<>());

        viewModel.getListEmpleados().observe(EmpleadoListActivity.this, new Observer<List<Empleado>>() {
            @Override
            public void onChanged(List<Empleado> empleados) {
                if (empleados.isEmpty()) {
                    //viewModel.setFakeData();
                }

                myAdapter.setEmpleados((ArrayList<Empleado>) empleados);

            }
        });

        empleadoListBinding.rvEmpleados.setLayoutManager(new LinearLayoutManager(EmpleadoListActivity.this));
        empleadoListBinding.rvEmpleados.setHasFixedSize(true);
        empleadoListBinding.rvEmpleados.setAdapter(myAdapter);

        empleadoListBinding.btToCreateEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(EmpleadoListActivity.this, EmpleadoFormActivity.class);
                startActivity(myIntent);

            }
        });

        myAdapter.setOnItemClickListener(new EmpleadoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Empleado empleado, int position) {
                Intent myIntent = new Intent(EmpleadoListActivity.this, EmpleadoDetailActivity.class);
                myIntent.putExtra("empleado", empleado);
                startActivityForResult(myIntent, REQUEST_CODE_EMPLEADO_DETAIL);


            }
        });



    }
    //se ejecuta al regresar al listado*@Override
    protected void onResume() {
        super.onResume();
        empleadoListBinding.getViewModel().loadEmpleados();

    }



}