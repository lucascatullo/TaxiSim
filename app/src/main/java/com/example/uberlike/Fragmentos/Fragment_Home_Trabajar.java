package com.example.uberlike.Fragmentos;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.uberlike.Objetos.DatosConductor;
import com.example.uberlike.R;

import java.util.Objects;

public class Fragment_Home_Trabajar extends Fragment {

    Button button_ver_lista, button_pedir_viaje, button_estado_viaje,button_complete_mi_viaje;
    Fragment fragment_estado_del_viaje = new Fragment_Estado_Del_Viaje();
    Fragment fragment_pedir_viaje = new Fragment_Pedir_Viaje();
    Fragment_lista_viajes fragment_lista_viajes = new Fragment_lista_viajes();
    View view;


    public Fragment_Home_Trabajar() {

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void MuestraFragment_Estado_del_viaje(FragmentManager fragmentManager) {
        // muestra el fragmento estado del viaje si el boton ESTADO DEL VIAJE fue presionado
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(fragment_estado_del_viaje)
                .hide(fragment_lista_viajes)
                .hide(fragment_pedir_viaje)
                .commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void MostrarFragment_Pedir_Viaje(FragmentManager fragmentManager) {
        // muestra el fragmento pedir viaje  si el boton pedir viaje fue presionado
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(fragment_pedir_viaje).hide(fragment_estado_del_viaje)
                .hide(fragment_lista_viajes).commit();
    }


    public void MuestraFragment_lista_viaje(FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(fragment_lista_viajes).hide(fragment_estado_del_viaje)
                .hide(fragment_pedir_viaje)
                .commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onStart() {
        super.onStart();
        button_ver_lista = view.findViewById(R.id.button10);
        button_pedir_viaje = view.findViewById(R.id.button11);
        button_estado_viaje = view.findViewById(R.id.button12);
        button_complete_mi_viaje = view.findViewById(R.id.button13);

        //añado todos los fragmentos
        final FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.add(R.id.viewh2, fragment_lista_viajes)
                .add(R.id.viewh2, fragment_pedir_viaje)
                .add(R.id.viewh2, fragment_estado_del_viaje)
                .commit();


        // carga los fragmentos que se vna a usar en cada uno de los botones de arriba
        // y los oculta, al hacer click en algún boton se muestra el fragmento que fue requerido
        button_ver_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            MuestraFragment_lista_viaje(fragmentManager);
            }
        });

        button_pedir_viaje.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                MostrarFragment_Pedir_Viaje(fragmentManager);
            }
        });

        button_estado_viaje.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                MuestraFragment_Estado_del_viaje(fragmentManager);
            }
        });
        button_complete_mi_viaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DatosConductor.isViajeActivo()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(R.string.Completo_su_viaje)
                            .setPositiveButton(R.string.Si, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatosConductor.setViajeActivo(false);
                                    fragment_lista_viajes.Eliminar();
                                }
                            })
                            .setNegativeButton(R.string.No, null)
                            .create()
                            .show();
                } else {
                    Toast.makeText(getContext(), R.string.Usted_no_tiene_viajes_activos, Toast.LENGTH_SHORT).show();
                }
                }

        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home__trabajar,container,false);
        return view;
    }
}
