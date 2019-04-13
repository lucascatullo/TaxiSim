package com.example.uberlike.Fragmentos;




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


import com.example.uberlike.R;
import com.google.firebase.auth.FirebaseAuth;


import java.util.Objects;


public class Fragment_Home_Cliente extends Fragment {

    View v;
    Fragment fragment_estado_del_viaje = new Fragment_Estado_Del_Viaje();
    Fragment fragment_pedir_viaje = new Fragment_Pedir_Viaje();
    FragmentManager fragmentManager;
    FirebaseAuth Mauth;


    public Fragment_Home_Cliente() {

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void MuestraFragment_Estado_del_viaje(FragmentManager fragmentManager) {
      // muestra el fragmento estado del viaje si el boton ESTADO DEL VIAJE fue presionado
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(fragment_estado_del_viaje).hide(fragment_pedir_viaje).commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void MostrarFragment_Pedir_Viaje(FragmentManager fragmentManager) {
        // muestra el fragmento pedir viaje  si el boton pedir viaje fue presionado
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(fragment_pedir_viaje).hide(fragment_estado_del_viaje).commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }





    @Override
    public void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        // añado los 2 fragmentos que se van a ejecutar cuando se presione el boton correspondiente a cada uno
        // y escondo ambos, al tocar el boton solo se ejecuta el comando para mostrarlos.
        super.onStart();
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.add(R.id.viewh2, fragment_estado_del_viaje)
                .add(R.id.viewh2, fragment_pedir_viaje)
                .hide(fragment_pedir_viaje).hide(fragment_estado_del_viaje)
                .commit();
        Button buttonPedirVIaje = Objects.requireNonNull(v).findViewById(R.id.button7);
        Button buttonEstadoDelPedido = v.findViewById(R.id.button8);
        buttonPedirVIaje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MostrarFragment_Pedir_Viaje(fragmentManager);
                }
            });
        buttonEstadoDelPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MuestraFragment_Estado_del_viaje(fragmentManager);
            }
        });
        Mauth = FirebaseAuth.getInstance();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home__cliente, container, false);
        return v;
    }
}

