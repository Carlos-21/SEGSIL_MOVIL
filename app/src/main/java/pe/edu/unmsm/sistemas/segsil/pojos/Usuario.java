package pe.edu.unmsm.sistemas.segsil.pojos;

public class Usuario {
    private String id;
    private String nombres;
    private String apellidos;
    private String password;
    private Perfil perfil;


    public Usuario(String id, String nombres, String apellidos, String password, Perfil perfil) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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
