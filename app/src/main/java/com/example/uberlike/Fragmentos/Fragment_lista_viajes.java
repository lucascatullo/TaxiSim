package com.example.uberlike.Fragmentos;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.uberlike.Adapter.PedidosAdapter;
import com.example.uberlike.Objetos.DatosConductor;
import com.example.uberlike.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Fragment_lista_viajes extends Fragment {

    View view;
    ArrayList<PedidosAdapter.Pedido> ArrayPedidos = new ArrayList<>();
    ListView listView;
    int pos;






    public void ActualizaLista() {
        // hace que la lista se actualice cada vez que se agrega o se elimina un pedido
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Viajes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("Error", "Error inesperado", e.getCause());
                } else {
                    CargarArray();
                }
            }
        });


    }



    public void VerificaEstado(final int position, final String comentario) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Viajes").document(ArrayPedidos.get(position).email)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                // verifica que el estado no cambio en en el momento en que se hizo click, para que no acepten 2
                // personas al mismo tiempo el viaje
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String status = (String) Objects.requireNonNull(documentSnapshot).get("status");
                    if (status != null && status.equals("EN CURSO")) {
                        ActualizaEstadoViaje(position, comentario);
                    }

                }
            }
        });
    }


    public void ActualizaEstadoViaje(int position, String Comentario) {
        // se ejecuta cuando un conductor acepta un viaje, y actualiza los datos de Firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put("status", "ACEPTADO");
        map.put("comentario", Comentario);
        map.put("auto", DatosConductor.getAuto());
        map.put("matricula", DatosConductor.getNumero());
        map.put("conductor", DatosConductor.getEmail());
        db.collection("Viajes").document(ArrayPedidos.get(position).email)
                .set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), R.string.El_viaje_fue_aceptado_correctamente,
                        Toast.LENGTH_SHORT).show();
            }
        });
        DatosConductor.setViajeActivo(true);
        pos = position;
    }


    public void Eliminar() {

        // se ejecuta cuando un viaje es completado para borrar la entrada de la base de datos
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Viajes").document(ArrayPedidos.get(pos).email).delete();
        CargarArray();
    }

    public void AceptarViaje_Dialog(final int position) {
        // crea un dialogo con los datos de la posición en el listview que se hizo click
        // y se espera confirmación de Aceptar Viaje.
        LayoutInflater layoutInflater = getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.dialog_aceptar_viaje, null);
        final EditText comentario = v.findViewById(R.id.editText8);
        TextView origen = v.findViewById(R.id.textView4);
        TextView destino = v.findViewById(R.id.textView5);
        String Elorigen = origen.getText().toString() + ArrayPedidos.get(position).origen;
        String ElDestino = destino.getText().toString() + ArrayPedidos.get(position).destino;
        origen.setText(Elorigen);
        destino.setText(ElDestino);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(v).setPositiveButton(R.string.Aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                VerificaEstado(position,comentario.getText().toString());
            }
        }).setNegativeButton(R.string.Cancelar, null)
        .create()
        .show();
    }


    public void CargarArray() {
        // se fija en firebase todos los pedidos registrados y carga ArrayPedidos para luego mostrarlo
        //en un ListView mediante PedidosAdapter
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Viajes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayPedidos.clear();
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        PedidosAdapter.Pedido pedido = new PedidosAdapter.Pedido();
                        pedido.origen = (String) document.get("origen");
                        pedido.destino = (String) document.get("destino");
                        pedido.status = (String) document.get("status");
                        pedido.email = (String) document.get("cliente");
                        ArrayPedidos.add(pedido);
                    }
                }
                listView.setAdapter(new PedidosAdapter(getContext(),ArrayPedidos));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (DatosConductor.isViajeActivo()) {// pregunta en los datos del conductor
                            // si este ya acepto algún viaje para
                            // no permirle aceptar dos al mismo tiempo
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage(R.string.Solo_puede_tener_un_viaje_activo_al_mismo_tiempo)
                                    .setPositiveButton(R.string.Aceptar,null)
                                    .create()
                                    .show();
                        }else{
                            AceptarViaje_Dialog(position);
                        }
                    }
                });
            }
        });

    }

    public void onStart() {
        super.onStart();
        listView = view.findViewById(R.id.lv);
        CargarArray();
        ActualizaLista();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_viajes, container, false);
        return view;
    }
}