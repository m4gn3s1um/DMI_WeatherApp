package com.example.dmi_weatherapp.DAO_Impl;

import com.example.dmi_weatherapp.DAO.VejrStationDao;
import com.example.dmi_weatherapp.HelloController;
import com.example.dmi_weatherapp.Model.Måling;
import com.example.dmi_weatherapp.Model.VejrStation;
import com.example.dmi_weatherapp.Singleton.SingletonStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class VejrStationDaoImpl implements VejrStationDao {

    private Connection con;


    public VejrStationDaoImpl() throws SQLException {
        con = SingletonStrategy.getInstance();
    }

    @Override
    public List<VejrStation> getAllVejrStationer() {

        List<VejrStation> getAllStationer = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Station");
            ResultSet rs = ps.executeQuery();

            VejrStation vjs;
            while(rs.next())
            {
                int id = rs.getInt(1);
                String stationNavn = rs.getString(2);
                String opsatDato = rs.getString(3);
                int højde = rs.getInt(4);
                String lgdBrdGrad = rs.getString(5);

                vjs = new VejrStation(id, stationNavn, opsatDato, højde, lgdBrdGrad);
                getAllStationer.add(vjs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getAllStationer;
    }

    public List<VejrStation> getSearchedStation(String query) {
        List<VejrStation> searchedVejrStation= new LinkedList<>();
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Station WHERE Navn LIKE ?;");
            ps.setString(1, query + "%");
            ResultSet rs = ps.executeQuery();

            VejrStation vejrStation;
            while(rs.next())
            {
                int id = rs.getInt(1);
                String stationNavn = rs.getString(2);
                String opsatDato = rs.getString(3);
                int højde = rs.getInt(4);
                String lgdBrdGrad = rs.getString(5);

                vejrStation = new VejrStation(id,stationNavn,opsatDato,højde,lgdBrdGrad);
                searchedVejrStation.add(vejrStation);
            }
        }

        catch (SQLException e)
        {
            System.err.println("Kunne ikke finde stationen, " + e.getMessage());
        }
        return searchedVejrStation;
    }

    public List<Float> getTopChartData(String type, int sID, String fDate, String sDate){
        List<Float> chartData = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT Nedbør FROM MålingTest WHERE StationsID = ?");
            //ps.setString(1, type);
            ps.setInt(1,sID);
            ResultSet rs = ps.executeQuery();

            /*LocalDate målingDate = LocalDate.parse(rs.getString(2));
            LocalDate firstSelectedDate = LocalDate.parse(fDate);
            LocalDate secondSelectedDate = LocalDate.parse(sDate);
            System.out.println(målingDate);
            System.out.println(firstSelectedDate);
            System.out.println(secondSelectedDate);*/
            //while (rs.next() /*&& målingDate.isAfter(firstSelectedDate) && målingDate.isBefore(secondSelectedDate)*/){
              //  float måling = Float.parseFloat(rs.getString(1));

                //chartData.add(måling);
            //}

        }catch (SQLException e){
            System.err.println("Kunne ikke finde data " + e.getMessage());
        }

        return chartData;
    }

    @Override
    public List<Måling> getMålingData(int sID) {

        List<Måling> createChartData = new ArrayList<>();
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM MålingTest WHERE StationsID = ?");
            ps.setInt(1,sID);
            //ps.setString(2,date);
            ResultSet rs = ps.executeQuery();

            Måling måling;
            while(rs.next()){

                int målingID = rs.getInt(1);
                String målingDato = rs.getString(2);
                int stationsID = rs.getInt(3);
                String nedbør = rs.getString(4);
                String nedbørsMinutter = rs.getString(5);
                String middelTemp = rs.getString(6);
                String maksTemp = rs.getString(7);
                String minTemp = rs.getString(8);
                String solskin = rs.getString(9);
                String middelVind = rs.getString(10);
                String højesteVind = rs.getString(11);
                String skyhøjde = rs.getString(12);
                String skydække = rs.getString(13);

                måling = new Måling(målingID,målingDato,stationsID,nedbør,nedbørsMinutter,middelTemp,maksTemp,minTemp,solskin,
                        middelVind,højesteVind,skyhøjde,skydække);

                System.out.println("Måling er " + måling.getMålingID());
                createChartData.add(måling);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return createChartData;
    }
}