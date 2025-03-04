package com.example.javafx;

import java.util.Date;

public class Erreserba {
    private int id;
    private String erreserbaIzena;
    private Date erreserbaDate;
    private int pertsonaKopurua;
    private int mahiaId;

    public Erreserba() {}
    public Erreserba(int id, String erreserbaIzena, Date erreserbaDate, int pertsonaKopurua, int mahiaId){
        this.id=id;
        this.erreserbaIzena = erreserbaIzena;
        this.erreserbaDate = erreserbaDate;
        this.pertsonaKopurua = pertsonaKopurua;
        this.mahiaId = mahiaId;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getErreserbaIzena() {
        return erreserbaIzena;
    }
    public void setErreserbaIzena(String erreserbaIzena) {
        this.erreserbaIzena = erreserbaIzena;
    }
    public java.sql.Date getErreserbaDate() {
        return (java.sql.Date) erreserbaDate;
    }
    public void setErreserbaDate(Date erreserbaDate) {
        this.erreserbaDate = erreserbaDate;
    }
    public int getPertsonaKopurua() {
        return pertsonaKopurua;
    }
    public int getMahiaId() {
        return mahiaId;
    }
    public void setMahiaId(int mahiaId) {
        this.mahiaId = mahiaId;
    }
    public void setPertsonaKopurua(int pertsonaKopurua) {
        this.pertsonaKopurua = pertsonaKopurua;
    }
}
