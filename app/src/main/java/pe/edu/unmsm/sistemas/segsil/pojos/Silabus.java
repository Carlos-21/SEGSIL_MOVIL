package pe.edu.unmsm.sistemas.segsil.pojos;

public class Silabus {
    private String curso;

    public Silabus(String curso) {
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
