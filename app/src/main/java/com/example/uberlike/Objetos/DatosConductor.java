package com.example.uberlike.Objetos;

public class DatosConductor {


    static private String email,auto,numero;
    static private boolean viajeActivo;


    public  DatosConductor() {

    }

    public static boolean isViajeActivo() {
        return viajeActivo;
    }

    public static void setViajeActivo(boolean viajeActivo) {
        DatosConductor.viajeActivo = viajeActivo;
    }


    public static void setEmail(String email) {
        DatosConductor.email = email;
    }

    public static void setAuto(String auto) {
        DatosConductor.auto = auto;
    }



    public static void setNumero(String numero) {
        DatosConductor.numero = numero;
    }

    public static String getAuto() {
        return auto;
    }



    public static String getEmail() {
        return email;
    }

    public static String getNumero() {
        return numero;
    }
}
