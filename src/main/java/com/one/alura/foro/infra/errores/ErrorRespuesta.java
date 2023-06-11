package com.one.alura.foro.infra.errores;

public class ErrorRespuesta {
    private int status;
    private String mensaje;

    public ErrorRespuesta(String mensaje) {
        this.mensaje = mensaje;
    }
    public ErrorRespuesta(int status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

