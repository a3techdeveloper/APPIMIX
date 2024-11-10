package br.com.imix.model;

public class Horario {
    
    private int id;
    private String horario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public String toString(){
        return this.getHorario();
    }
}