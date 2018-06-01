package pe.edu.unmsm.sistemas.segsil.pojos;

public class Semana {
    private int numero;
    private int unidad;

    public Semana(int numero, int unidad) {
        this.numero = numero;
        this.unidad = unidad;
    }

    public Semana() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getUnidad() {
        return unidad;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }
}
