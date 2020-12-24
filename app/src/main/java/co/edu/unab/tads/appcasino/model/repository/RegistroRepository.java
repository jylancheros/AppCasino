package co.edu.unab.tads.appcasino.model.repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.edu.unab.tads.appcasino.model.entity.Empleado;
import co.edu.unab.tads.appcasino.model.entity.Registro;
import co.edu.unab.tads.appcasino.model.entity.Usuario;

public class RegistroRepository {
    public static final String REGISTRO_COLLETION = "registros";
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private MutableLiveData<Boolean> ready;
    private Empleado empleado;

    public RegistroRepository(Context context) {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        ready = new MutableLiveData();
        empleado = new Empleado();
    }

    public void setReady() {
        this.ready.setValue(false);
    }

    public LiveData<Boolean> getReady() {
        return ready;
    }
    public Empleado getEmpleado() {
        return empleado;
    }
    public void addRegistro(String empleadoId, String Evento, Date fechaHora) {
        Registro registro = new Registro();
        registro.setUsuarioId(auth.getUid());
        registro.setEmpleadoId(empleadoId);
        registro.setEvento(Evento);
        registro.setFecha(fechaHora);
        firestore.collection(REGISTRO_COLLETION).add(registro).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    empleado =  new Empleado();
                    ready.setValue(false);
                    //empleado.setValue(new Empleado());
                    return;
                } else {
                    Log.e("firestore", task.getException().getMessage());
                    return;
                }

            }
        });

    }

    //metodo para traer un empleado por la cedula
    public void loadEmpleadoByCedula(String cedula) {
        firestore.collection(EmpleadoRepository.EMPLEADO_COLLETION).whereEqualTo("cedula",cedula).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    if( task.getResult().isEmpty()){
                        empleado = new Empleado();
                        ready.setValue(true);
                        Log.e("yesid", "empleado vacio");
                        return;
                    }else
                    {
                        for (DocumentSnapshot item : task.getResult().getDocuments()) {
                            Empleado e = item.toObject(Empleado.class);
                            e.setEid(item.getId());
                            firestore.collection(UsuarioRepository.USUARIO_COLLECTION).document(e.getUsuarioId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Usuario myUser = task.getResult().toObject(Usuario.class);
                                        myUser.setUid((e.getUsuarioId()));
                                        e.setUsuario(myUser);
                                        empleado = e;
                                        ready.setValue(true);
                                        Log.e("yesid", e.getEid());
                                        return;
                                    } else {
                                        Log.e("yesid", task.getException().getMessage());
                                        return;
                                    }
                                }
                            });

                        }
                    }

                } else {
                    Log.e("yesid", task.getException().getMessage());
                }
            }
        });
    }






}
