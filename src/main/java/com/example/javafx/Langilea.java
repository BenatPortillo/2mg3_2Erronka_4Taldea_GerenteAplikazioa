package com.example.javafx;

public class Langilea {
    private int id;
    private String dni;
    private String izena;
    private String abizena;
    private String pasahitza;
    private String korreoa;
    private String telefonoa;
    private String postua;
    private boolean txatBaimena;

    public Langilea() {}
    public Langilea(int id, String dni, String izena,String abizena, String pasahitza,String korreoa,String telefonoa,String postua, boolean txatBaimena) {
        this.dni=dni;
        this.id = id;
        this.izena = izena;
        this.abizena = abizena;
        this.pasahitza = pasahitza;
        this.korreoa=korreoa;
        this.telefonoa=telefonoa;
        this.postua=postua;
        this.txatBaimena = txatBaimena;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getIzena() {
        return izena;
    }
    public void setIzena(String izena) {
        this.izena = izena;
    }
    public String getAbizena() {
        return abizena;
    }
    public void setAbizena(String abizena) {
        this.abizena = abizena;
    }

    public String getKorreoa() {
        return korreoa;
    }
    public void setKorreoa(String korreoa) {
        this.korreoa = korreoa;
    }
    public String getTelefonoa() {
        return telefonoa;
    }
    public void setTelefonoa(String telefonoa) {
        this.telefonoa = telefonoa;
    }
    public String getPostua() {
        return postua;
    }
    public void setPostua(String postua) {
        this.postua = postua;
    }
    public String getPasahitza() {
        return pasahitza;
    }
    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }
    public boolean isTxatBaimena() {
        return txatBaimena;
    }

    public void setTxatBaimena(boolean txatBaimena) {
        this.txatBaimena = txatBaimena;
    }
}
