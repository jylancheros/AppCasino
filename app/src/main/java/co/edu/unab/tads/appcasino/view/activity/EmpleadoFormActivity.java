package co.edu.unab.tads.appcasino.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.edu.unab.tads.appcasino.R;
import co.edu.unab.tads.appcasino.databinding.ActivityEmpleadoFormBinding;
import co.edu.unab.tads.appcasino.model.entity.Empleado;
import co.edu.unab.tads.appcasino.viewmodel.EmpleadoFormViewModel;

public class EmpleadoFormActivity extends AppCompatActivity {


    private static final int CODE_REQUEST_GALLERY = 102;
    private static final int CODE_REQUEST_CAMERA = 100;
    ActivityEmpleadoFormBinding empleadoFormBinding;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado_form);

        EmpleadoFormViewModel viewModel = new ViewModelProvider(EmpleadoFormActivity.this).get(EmpleadoFormViewModel.class);

        Empleado empleado = (Empleado) getIntent().getSerializableExtra("empleado");
        empleadoFormBinding = DataBindingUtil.setContentView(EmpleadoFormActivity.this, R.layout.activity_empleado_form);
        if (empleado != null) {
            empleadoFormBinding.setEmpleado(empleado);
            empleadoFormBinding.btAddProducto.setText("Editar Empleado");
            empleadoFormBinding.btAddProducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Empleado e = empleadoFormBinding.getEmpleado();
                    viewModel.editEmpleado(e,imageUri);
                    Intent data = new Intent();
                    data.putExtra("empleado", e);
                    setResult(RESULT_OK, data);
                    viewModel.getReady().observe(EmpleadoFormActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean){
                                finish();
                            }
                        }
                    });

                }
            });
        } else {
            empleadoFormBinding.setEmpleado(new Empleado());

            empleadoFormBinding.btAddProducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Empleado empleado = empleadoFormBinding.getEmpleado();

                    viewModel.addEmpleado(empleado,imageUri);
                    viewModel.getReady().observe(EmpleadoFormActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean){
                                finish();
                            }
                        }
                    });
                }
            });
        }

        empleadoFormBinding.ibCameraForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (myIntent.resolveActivity(getPackageManager()) != null) {
                    File image = null;
                    try {
                        image = createPhoto();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    if(image !=null){
                        imageUri = FileProvider.getUriForFile(EmpleadoFormActivity.this,"co.edu.unab.tads.appcasino.fileprovider",image);
                        myIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        startActivityForResult(myIntent, CODE_REQUEST_CAMERA);
                    }

                }

            }
        });

        empleadoFormBinding.ibGalleryForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (myIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(myIntent, CODE_REQUEST_GALLERY);

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_REQUEST_CAMERA:
                Glide.with(EmpleadoFormActivity.this).load(imageUri).into(empleadoFormBinding.ivImagenForm);
                break;
            case CODE_REQUEST_GALLERY:
                imageUri = data.getData();
                Glide.with(EmpleadoFormActivity.this).load(imageUri).into(empleadoFormBinding.ivImagenForm);
                break;
        }
    }

    private File createPhoto() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

}