package pe.edu.unmsm.sistemas.segsil.pojos;

public class Silabus {
    private String id;
    private String curso;

    public Silabus(String id, String curso) {
        this.id = id;
        this.curso = curso;
    }

    public Silabus() {
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
