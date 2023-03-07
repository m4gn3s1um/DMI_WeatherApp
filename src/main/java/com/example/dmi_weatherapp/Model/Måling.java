package com.example.dmi_weatherapp.Model;

public class Måling {

    private int målingID;
    private String målDato;
    private int stationsID;
    private String nedbør;
    private String nedbørsMinutter;
    private String middelTemp;
    private String maksTemp;
    private String minTemp;
    private String solskin;
    private String middelVind;
    private String højesteVind;
    private String skyhøjde;
    private String skydække;

    public Måling(int målingID, String målDato, int stationsID,String nedbør, String nedbørsMinutter, String middelTemp,
                  String maksTemp, String minTemp, String solskin, String middelVind, String højesteVind, String skyhøjde, String skydække){

        this.målingID = målingID;
        this.målDato = målDato;
        this.stationsID = stationsID;
        this.nedbør = nedbør;
        this.nedbørsMinutter = nedbørsMinutter;
        this.middelTemp = middelTemp;
        this.maksTemp = maksTemp;
        this.minTemp = minTemp;
        this.solskin = solskin;
        this.middelVind = middelVind;
        this.højesteVind = højesteVind;
        this.skyhøjde = skyhøjde;
        this.skydække = skydække;
    }

    public int getMålingID() {
        return målingID;
    }

    public String getMålDato() {
        return målDato;
    }

    public int getStationsID() {
        return stationsID;
    }

    public String getNedbør() {
        return nedbør;
    }

    public String getNedbørsMinutter() {
        return nedbørsMinutter;
    }

    public String getMiddelTemp() {
        return middelTemp;
    }

    public String getMaksTemp() {
        return maksTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getSolskin() {
        return solskin;
    }

    public String getMiddelVind() {
        return middelVind;
    }

    public String getHøjesteVind() {
        return højesteVind;
    }

    public String getSkyhøjde() {
        return skyhøjde;
    }

    public String getSkydække() {
        return skydække;
    }

    @Override
    public String toString() {
        return "Måling{" +
                "målingID=" + målingID +
                ", stationsID=" + stationsID +
                '}';
    }
}
