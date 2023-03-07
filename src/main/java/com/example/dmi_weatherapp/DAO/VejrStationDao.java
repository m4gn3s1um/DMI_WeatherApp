package com.example.dmi_weatherapp.DAO;

import com.example.dmi_weatherapp.Model.Måling;
import com.example.dmi_weatherapp.Model.VejrStation;

import java.util.List;

public interface VejrStationDao {

    public List<VejrStation> getAllVejrStationer();

    public List<VejrStation> getSearchedStation(String query);
}