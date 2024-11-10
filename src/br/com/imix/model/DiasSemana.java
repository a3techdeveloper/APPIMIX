package br.com.imix.model;

public class DiasSemana {
    
    private int id;
    private String dias;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }
    
    @Override
    public String toString(){
        return this.getDias();
    }
}