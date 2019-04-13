package com.example.uberlike.Activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.uberlike.Fragmentos.Fragment_LogIn;
import com.example.uberlike.Fragmentos.Fragment_Registro_Cliente;
import com.example.uberlike.Fragmentos.Fragment_Registro_Trabajar;
import com.example.uberlike.R;

public class Activity_Ingresos_Registros extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Bundle bundle = getIntent().getExtras();
        String tipo = bundle.getString("Tipo");

        // comienza la transacción del fragmento según que boton se toco en "mainAcitivity"
        if (tipo.equals("Trabajar")) {
            FragmentRegistro_trabajar_init();
        }else{
            if (tipo.equals("Cliente")) {
                FragmentRegistro_Cliente_init();
            }
            else {
                if (tipo.equals("LogIn")) {
                    FragmentInicio_login_init();
                }
            }
        }
    }


    public void FragmentInicio_login_init() {
        //inicia el fragmento Fragment_LogIn si se presiono el boton iniciar sesion

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment_LogIn Log = new Fragment_LogIn();
        fragmentTransaction.add(R.id.viewg, Log).commit();
    }


    public void FragmentRegistro_trabajar_init() {
        //inicia el fragmento si se presiono el boton quiero trabajar

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment_Registro_Trabajar trabajar = new Fragment_Registro_Trabajar();
        fragmentTransaction.add(R.id.viewg, trabajar).commit();


    }

    public void FragmentRegistro_Cliente_init( ) {
        /// Inicia El fragmento Cliente si se toco ese boton

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment_Registro_Cliente fragmentRegistro_cliente = new Fragment_Registro_Cliente();
        fragmentTransaction.add(R.id.viewg, fragmentRegistro_cliente).commit();

    }
}
