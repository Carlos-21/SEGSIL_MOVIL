package pe.edu.unmsm.sistemas.segsil.pojos;

public class Semana {
    private int numero;
    private int unidad;
    private boolean llenado;

    public Semana(int numero, int unidad, boolean llenado) {
        this.numero = numero;
        this.unidad = unidad;
        this.llenado = llenado;
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

    public boolean isLlenado() {
        return llenado;
    }

    public void setLlenado(boolean llenado) {
        this.llenado = llenado;
    }
}
