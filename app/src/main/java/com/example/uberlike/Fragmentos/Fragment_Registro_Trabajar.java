package com.example.uberlike.Fragmentos;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uberlike.Objetos.Conductor;
import com.example.uberlike.R;
import com.example.uberlike.VerificaPasswd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Objects;



public class Fragment_Registro_Trabajar extends Fragment {

    private EditText etemail,etpasswd1,etpasswd2,etauto, etnumeroregistro;
    private View v;
    private FirebaseAuth mAuth;



    public Fragment_Registro_Trabajar() {

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        etemail = v.findViewById(R.id.et);
        etpasswd1 = v.findViewById(R.id.et2);
        etpasswd2 = v.findViewById(R.id.et3);
        etauto = v.findViewById(R.id.et4);
        etnumeroregistro = v.findViewById(R.id.et5);
        Button button = v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etemail.length() > 0 && etpasswd1.length() > 0 && etpasswd2.length() > 0 && etauto.length() > 0 && etnumeroregistro.length() > 0) {
                    if (VerificaPasswd.passwdverification(etpasswd1.getText().toString(),etpasswd2.getText().toString()))
                    { CrearUsuario(etemail.getText().toString(),etpasswd2.getText().toString());}
                }else{
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                    builder.setMessage(R.string.Se_deben_llenar_todos_los_casilleros)
                            .setPositiveButton(R.string.Ok, null)
                            .create()
                            .show();
                }

            }
        });
    }





    public void GuardarDatosConductor() {
        //si el usuario paso el proceso de Activity_Ingresos_Registros correctamente, Guarda los datos del conductor en la base de datos
            FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Conductores")
                .document(etemail.getText().toString())
                .set(new Conductor(etemail.getText().toString()
                        , etauto.getText().toString(), etnumeroregistro.getText().toString()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), R.string.Usuario_creado_correctamente, Toast.LENGTH_SHORT).show();
                    }
                });
    }



    public void CrearUsuario(String email, String passwd) {
        // registra un nuevo usuario con E-mail y passwd, Guarda los datos extra si se completa correctamente

        mAuth.createUserWithEmailAndPassword(email, passwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    GuardarDatosConductor();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                    builder.setMessage(Objects.requireNonNull(task.getException()).toString())
                            .setPositiveButton(R.string.Ok, null)
                            .create().show();

                }
            }
        });

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_registro_trabajar, container, false);
        return v;
    }


}