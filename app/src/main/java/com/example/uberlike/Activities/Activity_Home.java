package com.example.uberlike.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import com.example.uberlike.Fragmentos.Fragment_Home_Cliente;
import com.example.uberlike.Fragmentos.Fragment_Home_Trabajar;
import com.example.uberlike.NotificationService;
import com.example.uberlike.R;

import java.util.Objects;

public class Activity_Home extends FragmentActivity {


    int TIPO_INGRESO=0, CONDUCTOR =1, CLIENTE = 2;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        TIPO_INGRESO = Objects.requireNonNull(bundle).getInt("TipoIngreso");
        setContentView(R.layout.activity_home);
        if (TIPO_INGRESO == CLIENTE) {
            EjecutarFragment_Home_Cliente();
        }
        if (TIPO_INGRESO == CONDUCTOR) {
            EjecutarFragment_Home_Trabajar();
        }
        Intent i = new Intent(this, NotificationService.class);
        startService(i);
    }



    public void EjecutarFragment_Home_Trabajar() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new Fragment_Home_Trabajar();
        fragmentTransaction.add(R.id.viewh, fragment).commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void EjecutarFragment_Home_Cliente() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment_home_cliente = new Fragment_Home_Cliente();
        fragmentTransaction.add(R.id.viewh, fragment_home_cliente).commit();

    }





}
