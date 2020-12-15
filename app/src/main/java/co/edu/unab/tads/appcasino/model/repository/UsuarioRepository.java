package co.edu.unab.tads.appcasino.model.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import co.edu.unab.tads.appcasino.model.entity.Usuario;

public class UsuarioRepository {
    public static final String USUARIO_COLLECTION = "usuario";
    private FirebaseAuth auth;
    private FirebaseFirestore firebase;
    private MutableLiveData<Usuario> currentUsuario;

    public UsuarioRepository(Context context){
        auth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();
        currentUsuario = new MutableLiveData<>();
    }

    public LiveData<Usuario> getCurrentUsusario(){
        return currentUsuario;
    }

    public void signUp(Usuario myUser, String pass){
        auth.createUserWithEmailAndPassword(myUser.getEmail(), pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    firebase.collection(USUARIO_COLLECTION).document(auth.getUid()).set(myUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                myUser.setUid(auth.getUid());
                                currentUsuario.setValue(myUser);
                            }else{
                                Log.e("signUp", task.getException().getMessage());
                            }
                        }
                    });
                }else{
                    Log.e("signUp", task.getException().getMessage());
                }
            }
        });
    }

    public void SignIn(String email, String pass){
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    firebase.collection(USUARIO_COLLECTION).document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                if(task.getResult()!=null){
                                    Usuario myUser = task.getResult().toObject(Usuario.class);
                                    myUser.setUid(auth.getUid());
                                    currentUsuario.setValue(myUser);
                                }
                            }else{
                                Log.e("signIn", task.getException().getMessage());
                            }
                        }
                    });
                }else{
                    Log.e("signIn", task.getException().getMessage());
                }
            }
        });
    }
}
