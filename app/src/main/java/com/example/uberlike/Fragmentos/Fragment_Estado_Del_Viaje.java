package com.example.uberlike.Fragmentos;



import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uberlike.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;


public class Fragment_Estado_Del_Viaje extends Fragment {


    View view;
    TextView tv_estado, tv_comentarios;
    FirebaseAuth Mauth;
    Resources resources;


    public Fragment_Estado_Del_Viaje() {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        tv_estado = view.findViewById(R.id.textView);
        tv_comentarios = view.findViewById(R.id.textView2);
        Mauth = FirebaseAuth.getInstance();
        resources = getResources();
        TextosInicial();


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void TextosInicial() {

        //pregunta en la base de datos el estado del viaje en el momento en que se pega el fragmento
        // a la actividad
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference documentReference = db.collection("Viajes")
                .document(Objects.requireNonNull(Objects.requireNonNull(Mauth.getCurrentUser()).getEmail()));
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        String Status = (String) documentSnapshot.get("status");
                        if (Objects.requireNonNull(Status).equals("EN CURSO")) {
                            tv_estado.setText(R.string.Su_viaje_fue_registrado);
                            tv_comentarios.setText("");
                        } else {
                            if (Status.equals("ACEPTADO")) {
                                ObtenerDatosConductor();
                            }
                        }
                    }else{
                        tv_estado.setText(R.string.Usted_no_pidio_ningun_viaje);
                        tv_comentarios.setText("");

                    }
                }
            }
        });
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_estado__del__viaje, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
         TextosInicial();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void ObtenerDatosConductor() {
        // obtiene los datos del Conductor que acepto el viaje
        final String[] datos = new String[4];
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Viajes")
                .document(Objects.requireNonNull(Objects.requireNonNull(Mauth.getCurrentUser())
                        .getEmail()))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                datos[0] = (String) Objects.requireNonNull(documentSnapshot).get("conductor");
                datos[1] = (String) documentSnapshot.get("auto");
                datos[2] = (String) documentSnapshot.get("matricula");
                datos[3] = (String) documentSnapshot.get("comentario");
                String Texto1_estado = resources.getString(R.string.Su_viaje_fue_aceptado) + "\n"
                        + resources.getString(R.string.El_conductor_es) + datos[0] +
                        "\n" + resources.getString(R.string.el_auto_tiene) + datos[1] +
                        "\n" + resources.getString(R.string.el_numero_de_matricula_del_conductor_es)
                        + datos[2];
                tv_estado.setText(Texto1_estado);
                String Texto2_comentario = R.string.Comentarios_del_conductor + datos[3];
                if (datos[3] != null) tv_comentarios.setText(Texto2_comentario);
            }
        });

    }






}