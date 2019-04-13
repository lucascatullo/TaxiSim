package com.example.uberlike.Objetos;

public class Conductor {
    private String Email;
    private String Auto;
    private String Numero_Registro;



    public Conductor(String Email, String Auto, String Numero_Registro) {
        this.Email = Email;
        this.Auto = Auto;
        this.Numero_Registro = Numero_Registro;
    }


    public String getEmail() {
        return Email;
    }

    public String getAuto() {
            return Auto;
    }

    public String getNumero_Registro() {
            return Numero_Registro;
    }




}
