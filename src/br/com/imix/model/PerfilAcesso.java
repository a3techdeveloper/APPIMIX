package br.com.imix.model;

public class PerfilAcesso {
    
    private int id;
    private String perfil;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return this.getPerfil();
    }   
}