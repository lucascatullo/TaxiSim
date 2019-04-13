package com.example.uberlike.Fragmentos;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.uberlike.Activities.Activity_Home;
import com.example.uberlike.Objetos.Conductor;
import com.example.uberlike.Objetos.DatosConductor;
import com.example.uberlike.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Objects;


public class Fragment_LogIn extends Fragment {

    View v;
    EditText etEmail, Etpasswd;
    Button button;
    FirebaseAuth Mauth;
    CheckBox checkBox;
    int tipo_ingreso=0, CONDUCTOR=1, CLIENTE = 2;


    public Fragment_LogIn() {

    }


    public void onStart() {
        super.onStart();
        button = v.findViewById(R.id.button5);
        etEmail = v.findViewById(R.id.editText4);
        Etpasswd = v.findViewById(R.id.editText5);
        checkBox = v.findViewById(R.id.checkBox);
        Mauth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.length() > 0 && Etpasswd.length() > 0) {
                    InicioSesion();
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




    public void Ingresar() {

        // Verifica de que tipo es el ingreso(conductor o cliente) fijandose si existe la entrada
        // "Email" en el registro de conductores. Pasa variables tipo_ingreso para determinar
        // que fragmentos implementar en la actividad Home



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference collectionReference = db.collection("Conductores").document(etEmail.getText().toString());
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (Objects.requireNonNull(documentSnapshot).exists()) {
                        tipo_ingreso = CONDUCTOR;
                        // en caso de ser Conductor, también guarda los datos del conductor alojados en Firebase
                        // en el objeto Conductor, para que , cuando se acepte un pedido, extraerlos para informar
                        // al cliente
                        String email = (String) documentSnapshot.get("email");
                        String auto = (String) documentSnapshot.get("auto");
                        String numero_regitr = (String) documentSnapshot.get("numero_Registro");

                        DatosConductor.setAuto(auto);
                        DatosConductor.setEmail(email);
                        DatosConductor.setNumero(numero_regitr);
                        Intent i = new Intent(getContext(), Activity_Home.class);
                        i.putExtra("TipoIngreso", tipo_ingreso);
                        startActivity(i);
                    }else{
                        tipo_ingreso = CLIENTE;
                        Intent i = new Intent(getContext(), Activity_Home.class);
                        i.putExtra("TipoIngreso", tipo_ingreso);
                        startActivity(i);
                    }
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void RecordarPasswd() {
        //si checkbox esta tildada creo un archivo en el sistema con Email y
        // Passwd para luego ser utilizado y abrir sesion automaticamente
        SharedPreferences preferences = Objects.requireNonNull(getContext())
                    .getSharedPreferences("Datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Email", etEmail.getText().toString());
        editor.putString("passwd", Etpasswd.getText().toString());
        editor.apply();

    }

    public void InicioSesion() {
        Mauth.signInWithEmailAndPassword(etEmail.getText().toString(), Etpasswd.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete() && task.isSuccessful()) {
                            Ingresar();
                            if (checkBox.isChecked()) RecordarPasswd();

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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        v = inflater.inflate(R.layout.fragment_login, container,false);
        return v;
    }
}
