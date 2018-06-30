package pe.edu.unmsm.sistemas.segsil.pojos;

public class Observacion {
    private String id;
    private String observacion;

    public Observacion(String id, String observacion) {
        this.id = id;
        this.observacion = observacion;
    }

    public Observacion() {
    }


    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
