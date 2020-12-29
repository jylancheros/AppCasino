package co.edu.unab.tads.appcasino.model.repository;

import android.content.Context;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

import co.edu.unab.tads.appcasino.model.entity.Empleado;
import co.edu.unab.tads.appcasino.model.entity.Registro;
import co.edu.unab.tads.appcasino.model.entity.Usuario;

public class RegistroRepository {
    private FirebaseAuth auth;
    public static final String REGISTRO_COLLECTION = "registros";
    private MutableLiveData<List<Registro>> registroList;
    private FirebaseFirestore firebaseStore;
    private StorageReference myReference;
    private Empleado empleado;
    private MutableLiveData<Boolean> ready;

    public RegistroRepository(Context context) {

        firebaseStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        registroList = new MutableLiveData<>();
        myReference = FirebaseStorage.getInstance().getReference();
        ready = new MutableLiveData<>();
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
        firebaseStore.collection(REGISTRO_COLLECTION).add(registro).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
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

    public LiveData<List<Registro>> getRegistros() {
        return registroList;
    }

    public void loadRegistros(String fecha) throws ParseException {
        Date fechaI = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaI);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date fechaF = calendar.getTime();
        firebaseStore.collection(REGISTRO_COLLECTION).whereGreaterThanOrEqualTo("fecha",fechaI).whereLessThan("fecha",fechaF).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Registro> list = new ArrayList<>();
                    for (DocumentSnapshot item : task.getResult().getDocuments()) {
                        Registro myRegister = item.toObject(Registro.class);
                        myRegister.setRid(item.getId());
                        firebaseStore.collection(UsuarioRepository.USUARIO_COLLECTION).document(myRegister.getUsuarioId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    Usuario myUser = task.getResult().toObject(Usuario.class);
                                    myUser.setUid(myRegister.getUsuarioId());
                                    myRegister.setMyUsuario(myUser);
                                    firebaseStore.collection(EmpleadoRepository.EMPLEADO_COLLETION).document(myRegister.getEmpleadoId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                Empleado myEmpleado = task.getResult().toObject(Empleado.class);
                                                myEmpleado.setEid(myRegister.getEmpleadoId());
                                                myRegister.setMyEmpleado(myEmpleado);
                                                list.add(myRegister);
                                                registroList.setValue(list);
                                            }else{
                                                Log.e("firestore", task.getException().getMessage());
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("firestore", task.getException().getMessage());
                                }
                            }
                        });
                    }
                } else {
                    Log.e("firestore", task.getException().getMessage());
                }
            }
        });
    }

    public void loadRegistros(){
        firebaseStore.collection(REGISTRO_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Registro> list = new ArrayList<>();
                    for (DocumentSnapshot item : task.getResult().getDocuments()) {
                        Registro myRegister = item.toObject(Registro.class);
                        myRegister.setRid(item.getId());
                        firebaseStore.collection(UsuarioRepository.USUARIO_COLLECTION).document(myRegister.getUsuarioId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    Usuario myUser = task.getResult().toObject(Usuario.class);
                                    myUser.setUid(myRegister.getUsuarioId());
                                    myRegister.setMyUsuario(myUser);
                                    firebaseStore.collection(EmpleadoRepository.EMPLEADO_COLLETION).document(myRegister.getEmpleadoId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                Empleado myEmpleado = task.getResult().toObject(Empleado.class);
                                                myEmpleado.setEid(myRegister.getEmpleadoId());
                                                myRegister.setMyEmpleado(myEmpleado);
                                                list.add(myRegister);
                                                registroList.setValue(list);
                                            }else{
                                                Log.e("firestore", task.getException().getMessage());
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("firestore", task.getException().getMessage());
                                }
                            }
                        });
                    }
                } else {
                    Log.e("firestore", task.getException().getMessage());
                }
            }
        });
    }

    //metodo para traer un empleado por la cedula
    public void loadEmpleadoByCedula(String cedula) {
        firebaseStore.collection(EmpleadoRepository.EMPLEADO_COLLETION).whereEqualTo("cedula",cedula).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                            firebaseStore.collection(UsuarioRepository.USUARIO_COLLECTION).document(e.getUsuarioId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
