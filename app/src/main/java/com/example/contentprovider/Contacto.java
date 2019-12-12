package com.example.contentprovider;

public class Contacto {

    private String nombre, email, numero;

    public Contacto() {}

    public Contacto(String nombre, String email, String numero) {
        this.nombre = nombre;
        this.email = email;
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", numero=" + numero +
                '}';
    }
}
