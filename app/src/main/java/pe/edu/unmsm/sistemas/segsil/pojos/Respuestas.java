package pe.edu.unmsm.sistemas.segsil.pojos;

import java.util.ArrayList;

public class Respuestas {
    String id;
    ArrayList<Boolean> respuestas;

    public Respuestas(String id, ArrayList<Boolean> respuestas) {
        this.id = id;
        this.respuestas = respuestas;
    }

    public Respuestas(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Boolean> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<Boolean> respuestas) {
        this.respuestas = respuestas;
    }
}
