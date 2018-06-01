package pe.edu.unmsm.sistemas.segsil.pojos;

public class Grupo {
    private String id;
    private int numero;
    private String eap;
    private String codCurso;
    private String nombre1;
    private String nombre2;
    private String tipo;
    private String codProfesor;
    private String nomProfesor;
    private String codCoordinador;
    private String nomCoordinador;
    private String codDelegado;
    private String nomDelegado;

    public Grupo(String id, int numero, String eap, String codCurso, String nombre1, String nombre2, String tipo, String codProfesor, String nomProfesor, String codCoordinador, String nomCoordinador, String codDelegado, String nomDelegado) {
        this.id = id;
        this.numero = numero;
        this.eap = eap;
        this.codCurso = codCurso;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.tipo = tipo;
        this.codProfesor = codProfesor;
        this.nomProfesor = nomProfesor;
        this.codCoordinador = codCoordinador;
        this.nomCoordinador = nomCoordinador;
        this.codDelegado = codDelegado;
        this.nomDelegado = nomDelegado;
    }

    public Grupo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEap() {
        return eap;
    }

    public void setEap(String eap) {
        this.eap = eap;
    }

    public String getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(String codCurso) {
        this.codCurso = codCurso;
    }

    public String getCodCoordinador() {
        return codCoordinador;
    }

    public void setCodCoordinador(String codCoordinador) {
        this.codCoordinador = codCoordinador;
    }

    public String getNomCoordinador() {
        return nomCoordinador;
    }

    public void setNomCoordinador(String nomCoordinador) {
        this.nomCoordinador = nomCoordinador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodProfesor() {
        return codProfesor;
    }

    public void setCodProfesor(String codProfesor) {
        this.codProfesor = codProfesor;
    }

    public String getNomProfesor() {
        return nomProfesor;
    }

    public void setNomProfesor(String nomProfesor) {
        this.nomProfesor = nomProfesor;
    }

    public String getCodDelegado() {
        return codDelegado;
    }

    public void setCodDelegado(String codDelegado) {
        this.codDelegado = codDelegado;
    }

    public String getNomDelegado() {
        return nomDelegado;
    }

    public void setNomDelegado(String nomDelegado) {
        this.nomDelegado = nomDelegado;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }
}
