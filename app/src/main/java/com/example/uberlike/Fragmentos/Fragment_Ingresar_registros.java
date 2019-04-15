package com.example.uberlike.Fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.uberlike.Activities.Activity_Ingresos_Registros;
import com.example.uberlike.R;

public class Fragment_Ingresar_registros extends Fragment {

    View v;
    Button boton_ingresar_cliente;
    Button boton_ingresar_trabajar;
    Button boton_iniciar_sesion;

    public Fragment_Ingresar_registros() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ingresar, container, false);
        return v;
    }
    @Override
    public void onStart() {
        super.onStart();
        boton_ingresar_cliente = v.findViewById(R.id.button3);
        boton_ingresar_trabajar = v.findViewById(R.id.button4);
        boton_iniciar_sesion = v.findViewById(R.id.button6);
        boton_ingresar_trabajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // accede a la UI para registrarse como conductor.
                RegistroTrabajar();
            }
        });
        boton_ingresar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //accede a la interfaz para registrarte como Clienttes
                RegistroCliente();
            }
        });
        boton_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // entra a la interfaz para ingresar al sistema si ya estas registrado
                IniciarSesion();
            }
        });
    }


    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

    }

    public void IniciarSesion() {
        Intent i = new Intent(getContext(), Activity_Ingresos_Registros.class);
        i.putExtra("Tipo", "LogIn");
        startActivity(i);
    }

    public void RegistroTrabajar() {
        Intent i = new Intent(getContext(), Activity_Ingresos_Registros.class);
        i.putExtra("Tipo", "Trabajar");
        startActivity(i);
    }

    public void RegistroCliente() {
        Intent i = new Intent(getContext(), Activity_Ingresos_Registros.class);
        i.putExtra("Tipo", "Cliente");
        startActivity(i);
    }
}