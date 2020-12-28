package co.edu.unab.tads.appcasino.model.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.tads.appcasino.model.entity.Registro;
import co.edu.unab.tads.appcasino.model.entity.Usuario;

public class RegistroRepository {
    private FirebaseAuth auth;
    public static final String REGISTRO_COLLECTION = "registros";
    private MutableLiveData<List<Registro>> registroList;
    private FirebaseFirestore firebaseStore;
    private StorageReference myReference;
    private MutableLiveData<Boolean> ready;

    public RegistroRepository(Context context) {

        firebaseStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        registroList = new MutableLiveData<>();
        myReference = FirebaseStorage.getInstance().getReference();
        ready = new MutableLiveData<>();
        loadRegistros();
    }

    public LiveData<List<Registro>> getRegistros() {
        return registroList;
    }

    public void loadRegistros() {
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
                                if(task.isSuccessful())
                                {
                                    Usuario myUser =  task.getResult().toObject(Usuario.class);
                                    myUser.setUid(myRegister.getUsuarioId());
                                    myRegister.setMyUsuario(myUser);
                                    list.add(myRegister);
                                    registroList.setValue(list);
                                }else
                                {
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
}
