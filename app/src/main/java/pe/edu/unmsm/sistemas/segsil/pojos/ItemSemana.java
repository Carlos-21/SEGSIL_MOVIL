package pe.edu.unmsm.sistemas.segsil.pojos;

public class ItemSemana {
    private int numero;
    private String temas;

    public ItemSemana(int numero, String temas) {
        this.numero = numero;
        this.temas = temas;
    }

    public ItemSemana() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTemas() {
        return temas;
    }

    public void setTemas(String temas) {
        this.temas = temas;
    }
}
