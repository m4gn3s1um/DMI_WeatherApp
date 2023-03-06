package com.example.dmi_weatherapp;

import com.example.dmi_weatherapp.Model.VejrStation;
import com.example.dmi_weatherapp.DAO.VejrStationDao;
import com.example.dmi_weatherapp.DAO_Impl.VejrStationDaoImpl;
import com.example.dmi_weatherapp.Model.VejrStation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class HelloController
{

    @FXML
    private ChoiceBox topDataTypeChoice, bottomDataTypeChoice, topIntervalChoice, bottomIntervalChoice;
    @FXML
    private ListView vejrStationList;
    @FXML
    private DatePicker topRightDate, topLeftDate, bottomRightDate, bottomLeftDate;
    @FXML
    private MFXButton generateTop, generateBottom;
    @FXML
    private MFXTextField searchBar;
    @FXML
    private LineChart topChart, bottomChart;
    @FXML
    private Label topMiddelværdi, topMedian, topMidAfMidt, bottomMiddelværdi, bottomMedian, bottomMidAfMidt;
    @FXML
    private Button searchButton;

    public HelloController() throws SQLException {
    }

    public void initialize(){
        showWeatherStations();
        setDateValue();
        setChoiceboxValues();
    }

    public void setDateValue(){
        topLeftDate.setValue(java.time.LocalDate.now());
        topRightDate.setValue(java.time.LocalDate.now());
        bottomLeftDate.setValue(java.time.LocalDate.now());
        bottomRightDate.setValue(java.time.LocalDate.now());
    } //Sætter dato i datepicker til dags dato

    public void setChoiceboxValues (){
        topIntervalChoice.getItems().addAll("Timer", "Døgn", "Uger");
        topIntervalChoice.setValue("Vælg tid");
        bottomIntervalChoice.getItems().addAll("Timer", "Døgn", "Uger");
        bottomIntervalChoice.setValue("Vælg tid");
        topDataTypeChoice.getItems().addAll("Nedbør", "Nedbørsminutter","Middeltemperatur", "Middelvindhastighed");
        topDataTypeChoice.setValue("Middeltemperatur");
        bottomDataTypeChoice.getItems().addAll("Nedbør", "Nedbørsminutter","Middeltemperatur", "Middelvindhastighed");
        bottomDataTypeChoice.setValue("Middeltemperatur");
    } //sætter muligheder op i choicebox

    public void showWeatherStations(){
        List<VejrStation> vejrSts = vjs.getAllVejrStationer();
        for (VejrStation vejr : vejrSts)
        {
            vejrStationList.getItems().add(vejr);
        }
    } // Viser alle VejrStationer der findes

    public void topRightDateSelected(ActionEvent actionEvent) {
        if (topLeftDate.getValue().compareTo(topRightDate.getValue()) > 0 ){
            topRightDate.setValue(topLeftDate.getValue());
        }
        topSecondDate = String.valueOf(topRightDate.getValue());
        System.out.println(topSecondDate);
    } //Opdaterer en String med dato, som kan bruges til SQL

    public void topLeftDateSelected(ActionEvent actionEvent)
    {
    }

    public void generateTop(ActionEvent actionEvent)
    {
    }

    public void bottomRightDateSelected(ActionEvent actionEvent)
    {
    }

    public void bottomDateLeftSelected(ActionEvent actionEvent)
    {
    }

    public void generateBottom(ActionEvent actionEvent)
    {
    }

    public void search(ActionEvent actionEvent)
    {
    }

    public void vejrStationClicked(MouseEvent mouseEvent)
    {
    }

    public void bottomDataTypeValgt(MouseEvent mouseEvent)
    {
    }

    public void topDataTypeValgt(MouseEvent mouseEvent)
    {
    }

    public void topIntervalValgt(MouseEvent mouseEvent)
    {
    }

    public void bottomIntervalValgt(MouseEvent mouseEvent)
    {
    }

}