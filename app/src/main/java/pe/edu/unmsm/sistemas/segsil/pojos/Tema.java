package pe.edu.unmsm.sistemas.segsil.pojos;

import java.util.ArrayList;

public class Tema {
    private int numero;
    private String nombre;
    private ArrayList<String> actividades;

    public Tema(int numero, String nombre, ArrayList<String> actividades) {
        this.numero = numero;
        this.nombre = nombre;
        this.actividades = actividades;
    }

    public Tema() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getActividades() {
        return actividades;
    }

    public void setActividades(ArrayList<String> actividades) {
        this.actividades = actividades;
    }
}
