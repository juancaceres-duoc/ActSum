package com.example.actsum.usuarios.dto;

public class RecuperarPasswordResponse {

    private boolean existe;
    private String mensaje;
    private String passwordActual;

    public RecuperarPasswordResponse(boolean existe, String mensaje, String passwordActual) {
        this.existe = existe;
        this.mensaje = mensaje;
        this.passwordActual = passwordActual;
    }

    public boolean isExiste() {
        return existe;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getPasswordActual() {
        return passwordActual;
    }
}