package com.example.dmi_weatherapp.DAO_Impl;

import com.example.dmi_weatherapp.DAO.VejrStationDao;
import com.example.dmi_weatherapp.Model.VejrStation;
import com.example.dmi_weatherapp.Singleton.SingletonStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<VejrStation> getSearchedStation(String query)
    {
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

}