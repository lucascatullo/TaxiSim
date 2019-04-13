package com.example.uberlike.Objetos;

public class Cliente {



    private String Email,Direccion, Destino;


    public Cliente(String Email, String Direccion, String Destino) {
        this.Email = Email;
        this.Direccion = Direccion;
        this.Destino = Destino;
    }

    public String getEmail() {
        return Email;
    }

    public String getDireccion() {
        return Direccion;
    }

    public String getDestino() {
        return Destino;
    }
}
