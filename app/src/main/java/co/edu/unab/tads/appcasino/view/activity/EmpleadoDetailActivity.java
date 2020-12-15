package co.edu.unab.tads.appcasino.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import co.edu.unab.tads.appcasino.R;
import co.edu.unab.tads.appcasino.databinding.ActivityEmpleadoDetailBinding;
import co.edu.unab.tads.appcasino.model.entity.Empleado;
import co.edu.unab.tads.appcasino.viewmodel.EmpleadoDetailViewModel;

public class EmpleadoDetailActivity extends AppCompatActivity {


    private static final int REQUEST_CODE_EDIT_EMPLEADO = 200;
    ActivityEmpleadoDetailBinding detailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado_detail);
        Empleado empleado = (Empleado) getIntent().getSerializableExtra("empleado");

        EmpleadoDetailViewModel viewModel = new ViewModelProvider(EmpleadoDetailActivity.this).get(EmpleadoDetailViewModel.class);

        detailBinding = DataBindingUtil.setContentView(EmpleadoDetailActivity.this, R.layout.activity_empleado_detail);
        detailBinding.setEmpleado(empleado);
        detailBinding.btDeleteEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.removeEmpleado(empleado);
                setResult(RESULT_OK);
                finish();
            }
        });
        detailBinding.btEditEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =  new Intent(EmpleadoDetailActivity.this,EmpleadoFormActivity.class);
                myIntent.putExtra("empleado",detailBinding.getEmpleado());
                startActivityForResult(myIntent,REQUEST_CODE_EDIT_EMPLEADO);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_EDIT_EMPLEADO:
                if(resultCode==RESULT_OK){
                    Empleado myEdit = (Empleado) data.getSerializableExtra("empleado");
                    detailBinding.setEmpleado(myEdit);
                }
                break;
        }
    }
}