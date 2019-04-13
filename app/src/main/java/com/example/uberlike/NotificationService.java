package com.example.uberlike;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class NotificationService extends Service {
    FirebaseAuth Mauth;
    Context context;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate() {
        super.onCreate();
        Mauth = FirebaseAuth.getInstance();
        context = getBaseContext();
        RegistraCambiosDeEstado();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void RegistraCambiosDeEstado() {
        // si hay cambios de estado ejecuta el proceso para crear notificación y cambiar los textos en el
        // fragmento de estados del viaje
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference documentReference =  db.collection("Viajes")
                .document(Objects.requireNonNull(Objects.requireNonNull(Mauth.getCurrentUser()).getEmail()));
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot,
                                @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("Error", "Se produjo un error", e);
                    return;
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    String status = (String) documentSnapshot.get("status");
                    if (status!= null && status.equals("ACEPTADO")) {
                        createNotificacion();
                    }
                }

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createNotificacion() {
        Intent i = new Intent();
        i.putExtra("idScreen", 12);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(Objects.requireNonNull(context, "a"));
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setContentTitle("Pedido")
                .setContentText("Su pedido a sido aceptado")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
