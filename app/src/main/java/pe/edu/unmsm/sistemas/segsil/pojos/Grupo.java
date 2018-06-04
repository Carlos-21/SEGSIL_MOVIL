package pe.edu.unmsm.sistemas.segsil.pojos;

public class Grupo {
    private String id;
    private String eap;
    private int numero;
    private String tipo;
    private String codCurso;
    private int ciclo;
    private String nombrePlan1;
    private String nombrePlan2;
    private String codCoordinador;
    private String nomCoordinador;
    private String codProfesor;
    private String nomProfesor;
    private String codDelegado;
    private String nomDelegado;


    public Grupo(String id, String eap, int numero, String tipo, String codCurso, int ciclo, String nombrePlan1, String nombrePlan2, String codCoordinador, String nomCoordinador, String codProfesor, String nomProfesor, String codDelegado, String nomDelegado) {
        this.id = id;
        this.eap = eap;
        this.numero = numero;
        this.tipo = tipo;
        this.codCurso = codCurso;
        this.ciclo = ciclo;
        this.nombrePlan1 = nombrePlan1;
        this.nombrePlan2 = nombrePlan2;
        this.codCoordinador = codCoordinador;
        this.nomCoordinador = nomCoordinador;
        this.codProfesor = codProfesor;
        this.nomProfesor = nomProfesor;
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

    public String getNombrePlan1() {
        return nombrePlan1;
    }

    public void setNombrePlan1(String nombrePlan1) {
        this.nombrePlan1 = nombrePlan1;
    }

    public String getNombrePlan2() {
        return nombrePlan2;
    }

    public void setNombrePlan2(String nombrePlan2) {
        this.nombrePlan2 = nombrePlan2;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }
}
