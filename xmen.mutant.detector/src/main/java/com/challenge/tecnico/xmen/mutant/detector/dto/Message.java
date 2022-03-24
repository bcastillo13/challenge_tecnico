package com.challenge.tecnico.xmen.mutant.detector.dto;

/**
 * Mensaje para retornar en los llamados a la API con detalle de la invocación
 */
public class Message {

    private String message;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
