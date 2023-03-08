package com.example.dmi_weatherapp;

import com.example.dmi_weatherapp.Model.Måling;
import com.example.dmi_weatherapp.Model.VejrStation;
import com.example.dmi_weatherapp.DAO.VejrStationDao;
import com.example.dmi_weatherapp.DAO_Impl.VejrStationDaoImpl;
import com.example.dmi_weatherapp.Model.VejrStation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
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
import java.security.spec.ECField;
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
    private String topFirstDate, topSecondDate, bottomFirstDate, bottomSecondDate, topDataType, bottomDataType;
    @FXML
    private MFXButton generateTop, generateBottom;
    @FXML
    private MFXTextField searchBar;
    @FXML
    private LineChart<Number, Number> topChart, bottomChart;
    @FXML
    private Label topMiddelværdi, topMedian, topMidAfMidt, bottomMiddelværdi, bottomMedian, bottomMidAfMidt;
    @FXML
    private Button searchButton;

    public HelloController() throws SQLException {}

    public void initialize(){
        showWeatherStations();
        setDateValue();
        setChoiceboxValues();
    }

    public void setDateValue(){
        topLeftDate.setValue(java.time.LocalDate.of(2023,01,01));
        topRightDate.setValue(java.time.LocalDate.of(2023,01,31));
        bottomLeftDate.setValue(java.time.LocalDate.of(2023,01,01));
        bottomRightDate.setValue(java.time.LocalDate.of(2023,01,31));
    } //Sætter dato i datepicker start jan til slut jan

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

    public void topLeftDateSelected(ActionEvent actionEvent) {
        if (topLeftDate.getValue().compareTo(topRightDate.getValue()) > 0 ){
            topRightDate.setValue(topLeftDate.getValue());
        }
        topFirstDate = String.valueOf(topLeftDate.getValue());
        System.out.println(topFirstDate);
    } //Opdaterer en String med dato, som kan bruges til SQL

    public void generateTop(ActionEvent actionEvent) {
        System.out.println("GenerateTop");
        fjernVisibility();
        topChart.setAnimated(false);
        topChart.setVisible(true);

        Axis<Number> xAxis = topChart.getXAxis();
        xAxis.setLabel("Date");

        Axis<Number> yAxis = topChart.getYAxis();
        yAxis.setLabel("Weather");

        XYChart.Series<Number, Number> degrees = new XYChart.Series<Number,Number>();


        List<Måling> målinger = vjs.getMålingData(6041);

        System.out.println(målinger.get(0));

           for (Måling mål : målinger){
               degrees.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)),Double.valueOf(mål.getNedbør())));
               System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
               System.out.println(mål.getMålingID());
           }
        topChart.getData().addAll(degrees);
    } //Opsætter data i øverste chart

    public void setTopLabels(){
        //Beregn middelværdi, median og midt af midel, og setLabels
    } //Median og middelværdi for top chart

    public void bottomRightDateSelected(ActionEvent actionEvent) {
        if (bottomLeftDate.getValue().compareTo(bottomRightDate.getValue()) > 0 ){
            bottomRightDate.setValue(bottomLeftDate.getValue());
        }
        bottomSecondDate = String.valueOf(bottomRightDate.getValue());
        System.out.println(bottomSecondDate);
    } //Opdaterer en String med dato, som kan bruges til SQL

    public void bottomDateLeftSelected(ActionEvent actionEvent) {
        if (bottomLeftDate.getValue().compareTo(bottomRightDate.getValue()) > 0 ){
            bottomRightDate.setValue(bottomLeftDate.getValue());
        }
        bottomFirstDate = String.valueOf(bottomLeftDate.getValue());
        System.out.println(bottomFirstDate);
    } //Opdaterer en String med dato, som kan bruges til SQL

    public void generateBottom(ActionEvent actionEvent) {

        ObservableList valgteIndeks = vejrStationList.getSelectionModel().getSelectedIndices();

        for(Object indeks : valgteIndeks) {
            VejrStation vejrSt = (VejrStation) vejrStationList.getItems().get((int) indeks);

            System.out.println("GenerateBottom");
            fjernVisibility();
            bottomChart.setAnimated(false);
            bottomChart.setVisible(true);

            Axis<Number> xAxis = bottomChart.getXAxis();
            xAxis.setLabel("Dato");

            Axis<Number> yAxis = bottomChart.getYAxis();
            yAxis.setLabel("Vejr");

            XYChart.Series<Number, Number> degrees = new XYChart.Series<Number, Number>();

            List<Måling> målinger = vjs.getMålingData(vejrSt.getStationID(),Timestamp.valueOf(String.valueOf(bottomLeftDate)), Timestamp.valueOf(String.valueOf(bottomRightDate)));

            if (bottomDataTypeChoice.getValue() == "Nedbør") {
                for (Måling mål : målinger) {
                    degrees.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getNedbør())));
                    System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                    System.out.println(mål.getMålingID());
                }
            }

            if (bottomDataTypeChoice.getValue() == "Nedbørsminutter") {
                for (Måling mål : målinger) {
                    degrees.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getNedbørsMinutter())));
                    System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                    System.out.println(mål.getMålingID());
                }
            }

            if (bottomDataTypeChoice.getValue() == "Middeltemperatur") {
                for (Måling mål : målinger) {
                    degrees.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getMiddelTemp())));
                    System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                    System.out.println(mål.getMålingID());
                }
            }

            if (bottomDataTypeChoice.getValue() == "Middelvindhastighed") {
                for (Måling mål : målinger) {
                    degrees.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getMiddelVind())));
                    System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                    System.out.println(mål.getMålingID());
                }
            }

            bottomChart.getData().addAll(degrees);
        }} //opsætter data i nederste chart

    public void setBottomLabels(){
        //Beregn middelværdi, median og midt af midel, og setLabels
    } //Median og middelværdi for bottom chart

    public void search(ActionEvent actionEvent) {
        List<VejrStation> searchVejrStation = vjs.getSearchedStation(searchBar.getText());
        vejrStationList.getItems().clear();
        for(VejrStation vejr: searchVejrStation)
        {
            vejrStationList.getItems().add(vejr);
        }
    } //Søger blandt vejrstationer

    public void vejrStationClicked(MouseEvent mouseEvent) {
        ObservableList valgteIndeks = vejrStationList.getSelectionModel().getSelectedIndices();
        System.out.println("Weather station is selected");
    } // Tjekker hvad der bliver valgt på listen med VejrStationer

    public void topIntervalValgt(MouseEvent mouseEvent) {
        topIntervalChoice.setOnAction((e) -> {

            System.out.println(topIntervalChoice.getValue() + " er valgt");

            if (topIntervalChoice.getValue() == "Timer"){
                topRightDate.setDisable(true);

            } if (topIntervalChoice.getValue() == "Døgn"){
                topRightDate.setDisable(false);

            } else if (topIntervalChoice.getValue() == "Uger") {
                topRightDate.setDisable(true);
            }
        });
    } //Tjek om øverste datepicker skal være disabled

    public void bottomIntervalValgt(MouseEvent mouseEvent) {
        bottomIntervalChoice.setOnAction((e) -> {

            System.out.println(bottomIntervalChoice.getValue() + " er valgt");

            if (bottomIntervalChoice.getValue() == "Timer"){
                bottomRightDate.setDisable(true);

            } if (bottomIntervalChoice.getValue() == "Døgn"){
                bottomRightDate.setDisable(false);

            } else if (bottomIntervalChoice.getValue() == "Uger") {
                bottomRightDate.setDisable(false);
            }
        });
    } //Tjek om nederste datepicker skal være disabled

    public void topDataTypeValgt(MouseEvent mouseEvent){
        topDataTypeChoice.setOnAction((e) -> {

            topDataType = (String) bottomDataTypeChoice.getValue();
            System.out.println(topDataType + " er valgt");
        });
    }

    public void bottomDataTypeValgt(MouseEvent mouseEvent){
        bottomDataTypeChoice.setOnAction((e) -> {

            bottomDataType = (String) bottomDataTypeChoice.getValue();
            System.out.println(bottomDataType + " er valgt");
        });
    }

    public void fjernVisibility() {
        topChart.setVisible(false);
        topChart.getData().clear();
        bottomChart.setVisible(false);
        bottomChart.getData().clear();
    } //Viser og gemmer charts

    VejrStationDao vjs = new VejrStationDaoImpl();

}