package com.example.uberlike.Objetos;


// Este objeto, se sube a Firebase los datos del Cliente para ser aceptados por un auto y realizar
// el viaje

public class PedirViaje {

    String Cliente,Origen, Destino, Status;


    public PedirViaje(String Cliente, String Origen, String Destino, String Status) {
        this.Cliente = Cliente;
        this.Origen = Origen;
        this.Destino = Destino;
        this.Status = Status;
    }

    public String getStatus() {
        return Status;
    }

    public String getCliente() {
        return Cliente;
    }

    public String getDestino() {
        return Destino;
    }

    public String getOrigen() {
        return Origen;
    }
}
