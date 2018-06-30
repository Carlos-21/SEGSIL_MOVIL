package pe.edu.unmsm.sistemas.segsil.pojos;

public class Avance {
    String id;
    String idGrupo;
    double avance;

    public Avance(String id, String idGrupo, double avance) {
        this.id = id;
        this.idGrupo = idGrupo;
        this.avance = avance;
    }

    public Avance() {
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAvance() {
        return avance;
    }

    public void setAvance(double avance) {
        this.avance = avance;
    }
}
