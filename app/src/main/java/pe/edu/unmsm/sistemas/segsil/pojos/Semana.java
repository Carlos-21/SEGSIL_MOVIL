package pe.edu.unmsm.sistemas.segsil.pojos;

public class Semana {
    private int numero;
    private int unidad;
    private String nombreUnidad;
    private boolean llenado;

    public Semana(int numero, int unidad, String nombreUnidad, boolean llenado) {
        this.numero = numero;
        this.unidad = unidad;
        this.nombreUnidad = nombreUnidad;
        this.llenado = llenado;
    }

    public Semana() {
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
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
