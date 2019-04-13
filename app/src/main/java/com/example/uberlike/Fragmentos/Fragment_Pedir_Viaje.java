package com.example.uberlike.Fragmentos;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uberlike.Objetos.PedirViaje;
import com.example.uberlike.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class Fragment_Pedir_Viaje extends Fragment {
    View v;
    EditText etMiDireccion, etMiDestino;
    Button button;
    FirebaseAuth Mauth;



    public Fragment_Pedir_Viaje() {

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void PedirElViaje() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Viajes")
                .document(Objects.requireNonNull(Objects.requireNonNull(Mauth.getCurrentUser())
                        .getEmail()))
                .set(new PedirViaje(Objects.requireNonNull(Mauth.getCurrentUser()).getEmail()
                        , etMiDireccion.getText().toString()
                        , etMiDestino.getText().toString()
                        , "EN CURSO")).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), R.string.Pedido_realizado, Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();
        button = v.findViewById(R.id.button9);
        etMiDestino = v.findViewById(R.id.editText7);
        etMiDireccion = v.findViewById(R.id.editText6);
        Mauth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (etMiDestino.length() > 0 && etMiDireccion.length() > 0) {
                    PedirElViaje();
                } else{
                    Toast.makeText(getContext(), R.string.Se_deben_llenar_todos_los_casilleros, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pedir__viaje, container, false);
        return v;
    }
}