package pe.edu.unmsm.sistemas.segsil.pojos;

public class Curso {
    private String id;
    private String eap;
    private String nombreCurso;
    private String nombrePlan1;
    private String nombrePlan2;
    private String idCoordinador;
    private boolean silabus;

    public Curso(String id, String eap, String nombreCurso, String nombrePlan1, String nombrePlan2, String idCoordinador, boolean silabus) {
        this.id = id;
        this.eap = eap;
        this.nombreCurso = nombreCurso;
        this.nombrePlan1 = nombrePlan1;
        this.nombrePlan2 = nombrePlan2;
        this.idCoordinador = idCoordinador;
        this.silabus = silabus;
    }

    public Curso() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEap() {
        return eap;
    }

    public void setEap(String eap) {
        this.eap = eap;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
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

    public String getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(String idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public boolean isSilabus() {
        return silabus;
    }

    public void setSilabus(boolean silabus) {
        this.silabus = silabus;
    }
}

