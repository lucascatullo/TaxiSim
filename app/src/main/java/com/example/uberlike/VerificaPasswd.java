package com.example.uberlike;

public class VerificaPasswd {

    public VerificaPasswd() {

    }

     public static boolean passwdverification(String passwd1, String passwd2) {
        // devuelve true si se cumplen las condiciones de Firebase para las contraseñas
        return passwdmasde6digitos(passwd1) && passwdiguales(passwd1,passwd2);
    }

    private static boolean passwdmasde6digitos(String passd) {
        return passd.toCharArray().length > 0;
    }

    private static boolean passwdiguales(String passwd1, String passwd2) {
        return passwd1.equals(passwd2);
    }
}
