package com.example.javafx;

public class Mahaia {
    private int id;
    private int gehienezkoKopurua;
    private int zenbakia;

    // Constructor con todos los parámetros
    public Mahaia(int id, int gehienezkoKopurua, int zenbakia) {
        this.id = id;
        this.zenbakia = zenbakia;
        this.gehienezkoKopurua = gehienezkoKopurua;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGehienezkoKopurua() {
        return gehienezkoKopurua;
    }

    public void setGehienezkoKopurua(int gehienezkoKopurua) {
        this.gehienezkoKopurua = gehienezkoKopurua;
    }

    public int getZenbakia() {
        return zenbakia;
    }

    public void setZenbakia(int zenbakia) {
        this.zenbakia = zenbakia;
    }
}
