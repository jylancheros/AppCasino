package co.edu.unab.tads.appcasino.model.repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.tads.appcasino.model.entity.Empleado;
import co.edu.unab.tads.appcasino.model.entity.Usuario;

public class EmpleadoRepository {
    public static final String EMPLEADO_COLLETION = "empleados";
    private static final String IMAGE_DIRECTORY = "images";
    private MutableLiveData<List<Empleado>> empleadoList;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private StorageReference myReference;
    private MutableLiveData<Boolean> ready;

    public EmpleadoRepository(Context context) {
        empleadoList = new MutableLiveData<>();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        myReference = FirebaseStorage.getInstance().getReference();

        ready = new MutableLiveData();
        loadEmpleados();

    }

    public LiveData<List<Empleado>> getEmpleados() {
        return empleadoList;
    }
    public LiveData<Boolean> getReady() {
        return ready;
    }

    public void listenEmpleados() {
        firestore.collection(EMPLEADO_COLLETION).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    List<Empleado> list = new ArrayList<>();
                    for (DocumentSnapshot item : value.getDocuments()) {
                        Empleado empleado = item.toObject(Empleado.class);
                        empleado.setEid(item.getId());
                        firestore.collection(UsuarioRepository.USUARIO_COLLECTION).document(empleado.getUsuarioId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    Usuario myUser = task.getResult().toObject(Usuario.class);
                                    myUser.setUid((empleado.getUsuarioId()));
                                    empleado.setUsuario(myUser);
                                    list.add(empleado);
                                    empleadoList.setValue(list);
                                } else {
                                    Log.e("firestore", task.getException().getMessage());
                                }
                            }
                        });

                    }
                    empleadoList.setValue(list);
                } else {
                    Log.e("firestore", error.getMessage());
                }
            }
        });
    }


    public void loadEmpleados() {
        firestore.collection(EMPLEADO_COLLETION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Empleado> list = new ArrayList<>();
                    for (DocumentSnapshot item : task.getResult().getDocuments()) {
                        Empleado empleado = item.toObject(Empleado.class);
                        empleado.setEid(item.getId());
                        firestore.collection(UsuarioRepository.USUARIO_COLLECTION).document(empleado.getUsuarioId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    Usuario myUser = task.getResult().toObject(Usuario.class);
                                    myUser.setUid((empleado.getUsuarioId()));
                                    empleado.setUsuario(myUser);
                                    list.add(empleado);
                                    empleadoList.setValue(list);
                                } else {
                                    Log.e("firestore", task.getException().getMessage());
                                }
                            }
                        });

                    }
                    empleadoList.setValue(list);
                } else {
                    Log.e("firestore", task.getException().getMessage());
                }
            }
        });
    }

    public void addFS(Empleado empleado, Uri imageUri) {
        empleado.setUsuarioId(auth.getUid());
        if (imageUri != null) {
            String image = imageUri.toString().substring(imageUri.toString().lastIndexOf("/"));
            StorageReference myImage = myReference.child(IMAGE_DIRECTORY + "/" + image);
            myImage.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    String url = task.getResult().toString();
                                    empleado.setUrlImagen(url);
                                    firestore.collection(EMPLEADO_COLLETION).add(empleado).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                ready.setValue(true);
                                            } else {
                                                Log.e("firestore", task.getException().getMessage());
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }else
        {

            firestore.collection(EMPLEADO_COLLETION).add(empleado).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()) {

                    } else {
                        Log.e("firestore", task.getException().getMessage());
                    }
                }
            });
        }


    }
    public void updateFS(Empleado empleado, Uri imageUri) {

        if (imageUri != null) {
            String image = imageUri.toString().substring(imageUri.toString().lastIndexOf("/"));
            StorageReference myImage = myReference.child(IMAGE_DIRECTORY + "/" + image);
            myImage.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    String url = task.getResult().toString();
                                    empleado.setUrlImagen(url);
                                        firestore.collection(EMPLEADO_COLLETION).document(empleado.getEid()).set(empleado).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                ready.setValue(true);
                                            } else {
                                                Log.e("firestore", task.getException().getMessage());
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }else
        {
            firestore.collection(EMPLEADO_COLLETION).document(empleado.getEid()).set(empleado).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        ready.setValue(true);
                    } else {
                        Log.e("firestore", task.getException().getMessage());
                    }
                }
            });
        }


    }
    public void removeFS(Empleado empleado) {
        firestore.collection(EMPLEADO_COLLETION).document(empleado.getEid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    ready.setValue(true);
                } else {
                    Log.e("firestore", task.getException().getMessage());
                }
            }
        });
    }



}
