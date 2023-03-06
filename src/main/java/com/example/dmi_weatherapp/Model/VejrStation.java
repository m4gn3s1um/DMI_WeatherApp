package com.example.dmi_weatherapp.Model;

public class VejrStation {

    private int stationID;

    private String stationNavn;

    private String opsatDato;

    private int højde;

    private String lgdBrdGrad;

    public VejrStation(int stationID, String stationNavn, String opsatDato, int højde, String lgdBrdGrad)
    {
        this.stationID = stationID;
        this.stationNavn = stationNavn;
        this.opsatDato = opsatDato;
        this.højde = højde;
        this.lgdBrdGrad = lgdBrdGrad;
    }

    @Override
    public String toString() {
        return stationNavn;
    }

    public int getStationID() {
        return stationID;
    }

    public String getStationNavn() {
        return stationNavn;
    }

    public String getOpsatDato() {
        return opsatDato;
    }

    public int getHøjde() {
        return højde;
    }

    public String getLgdBrdGrad() {
        return lgdBrdGrad;
    }
}
