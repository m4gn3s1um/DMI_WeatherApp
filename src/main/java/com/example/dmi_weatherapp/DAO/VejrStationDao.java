package com.example.dmi_weatherapp.DAO;

import com.example.dmi_weatherapp.Model.Måling;
import com.example.dmi_weatherapp.Model.VejrStation;

import java.sql.Time;
import java.sql.Timestamp;

import java.sql.Date;
import java.util.List;

public interface VejrStationDao {

    public List<VejrStation> getAllVejrStationer();

    public List<VejrStation> getSearchedStation(String query);

    public List<Float> getTopChartData(String type, int sID, String fDate, String sDate);

    public List<Måling> getMålingData(int sID, Timestamp fDate, Timestamp sDate);

    public List<Måling> getHourData(int sID, Timestamp fDate, Timestamp sDate);

    public List<Måling> getWeekData(int sID, Timestamp fDate, Timestamp sDate);
}