package pe.edu.unmsm.sistemas.segsil.pojos;

public class Actividad {
    private String nombre;
    private String numTema;
    private String numUnidad;
    private String numero;
    private String realizado;
    private String semana;
    private String tema;
    private String unidad;

    public Actividad(String nombre, String numTema, String numUnidad, String numero, String realizado, String semana, String tema, String unidad) {
        this.nombre = nombre;
        this.numTema = numTema;
        this.numUnidad = numUnidad;
        this.numero = numero;
        this.realizado = realizado;
        this.semana = semana;
        this.tema = tema;
        this.unidad = unidad;
    }

    public Actividad() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumTema() {
        return numTema;
    }

    public void setNumTema(String numTema) {
        this.numTema = numTema;
    }

    public String getNumUnidad() {
        return numUnidad;
    }

    public void setNumUnidad(String numUnidad) {
        this.numUnidad = numUnidad;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRealizado() {
        return realizado;
    }

    public void setRealizado(String realizado) {
        this.realizado = realizado;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}

