package pe.edu.unmsm.sistemas.segsil.pojos;

public class SemanaItem {
    private int numero;
    private int unidad;
    private String temas;

    public SemanaItem(int numero, int unidad, String temas) {
        this.numero = numero;
        this.unidad = unidad;
        this.temas = temas;
    }

    public SemanaItem() {
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

    public String getTemas() {
        return temas;
    }

    public void setTemas(String temas) {
        this.temas = temas;
    }
}
