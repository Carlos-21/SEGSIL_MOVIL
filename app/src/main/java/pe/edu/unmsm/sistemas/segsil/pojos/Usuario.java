package pe.edu.unmsm.sistemas.segsil.pojos;

public class Usuario {
    private String id;
    private String password;
    private Perfil perfil;

    public Usuario(String id, String password, Perfil perfil) {
        this.id = id;
        this.password = password;
        this.perfil = perfil;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
