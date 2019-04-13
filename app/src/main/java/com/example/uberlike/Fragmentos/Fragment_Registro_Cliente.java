package com.example.uberlike.Fragmentos;


import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uberlike.R;
import com.example.uberlike.VerificaPasswd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;



public class Fragment_Registro_Cliente extends Fragment {


    View v;
    EditText etemail,etpasswd1,etpasswd2;
    Button button;
    FirebaseAuth Mauth;



    public Fragment_Registro_Cliente() {
        // Required empty public constructor
    }




    @Override
    public void onStart() {
        super.onStart();
        etemail = v.findViewById(R.id.editText);
        etpasswd1 = v.findViewById(R.id.editText2);
        etpasswd2 = v.findViewById(R.id.editText3);
        button = v.findViewById(R.id.button2);
        Mauth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etemail.length() > 0 && etpasswd1.length() > 0 && etpasswd2.length() > 0) {
                    if (VerificaPasswd.passwdverification(etpasswd1.getText().toString(), etpasswd2.getText().toString())) {
                        CrearUsuario();
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(R.string.Se_deben_llenar_todos_los_casilleros)
                            .setPositiveButton(R.string.Ok, null)
                            .create()
                            .show();
                }

            }
        });
    }


    public void CrearUsuario() {
        Mauth.createUserWithEmailAndPassword(etemail.getText().toString(), etpasswd1.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()) {
                            Toast.makeText(getContext(),R.string.Usuario_creado_correctamente, Toast.LENGTH_SHORT).show();

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage(Objects.requireNonNull(task.getException()).toString())
                                    .setPositiveButton(R.string.Ok, null)
                            .create()
                            .show();
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
        v = inflater.inflate(R.layout.fragment_registro__cliente, container, false);
        return v;
    }








}
