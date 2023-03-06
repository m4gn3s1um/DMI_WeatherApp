package com.example.dmi_weatherapp;

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
    private LineChart<String, String> topChart, bottomChart;
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

    public void topLeftDateSelected(ActionEvent actionEvent) {
        if (topLeftDate.getValue().compareTo(topRightDate.getValue()) > 0 ){
            topRightDate.setValue(topLeftDate.getValue());
        }
        topFirstDate = String.valueOf(topLeftDate.getValue());
        System.out.println(topFirstDate);
    } //Opdaterer en String med dato, som kan bruges til SQL

    public void generateTop(ActionEvent actionEvent) {
        fjernVisibility();
        topChart.setVisible(true);
        Axis<String> xAxis = topChart.getXAxis();
        xAxis.setLabel("Date");

        Axis<String> yAxis = topChart.getYAxis();
        yAxis.setLabel("Weather");

        XYChart.Series<String, String> degrees = new XYChart.Series<String,String>();
        degrees.setName(String.valueOf(topIntervalChoice));

        degrees.getData().add(new XYChart.Data<>("Date",String.valueOf(topIntervalChoice)));


        /*degrees.getData().add(new XYChart.Data<>(1,5));
        degrees.getData().add(new XYChart.Data<>(2,5));
        degrees.getData().add(new XYChart.Data<>(3,8));
        degrees.getData().add(new XYChart.Data<>(4,7));
        degrees.getData().add(new XYChart.Data<>(5,6));
        degrees.getData().add(new XYChart.Data<>(6,9));*/

        topChart.getData().add(degrees);

        /*XYChart.Series<Number, Number> wind = new XYChart.Series<Number,Number>();
        wind.setName("Vind");

        wind.getData().add(new XYChart.Data<>(0,4.6));
        wind.getData().add(new XYChart.Data<>(0.5,5.8));
        wind.getData().add(new XYChart.Data<>(1,2.2));
        wind.getData().add(new XYChart.Data<>(1.5,1.6));
        wind.getData().add(new XYChart.Data<>(2,3.1));
        wind.getData().add(new XYChart.Data<>(2.5,3.3));
        wind.getData().add(new XYChart.Data<>(3,4.1));
        wind.getData().add(new XYChart.Data<>(3.5,7.8));
        wind.getData().add(new XYChart.Data<>(4,5.1));
        wind.getData().add(new XYChart.Data<>(4.5,7.4));
        wind.getData().add(new XYChart.Data<>(5,7.3));
        wind.getData().add(new XYChart.Data<>(5.5,7.8));
        wind.getData().add(new XYChart.Data<>(6,9.4));
        wind.getData().add(new XYChart.Data<>(6.5,6.4));

        topChart.getData().add(wind);

        XYChart.Series<Number, Number> rain = new XYChart.Series<Number,Number>();
        rain.setName("Nedbør");

        rain.getData().add(new XYChart.Data<>(0,0));
        rain.getData().add(new XYChart.Data<>(1,0));
        rain.getData().add(new XYChart.Data<>(2,0));
        rain.getData().add(new XYChart.Data<>(3,0));
        rain.getData().add(new XYChart.Data<>(4,3.3));
        rain.getData().add(new XYChart.Data<>(5,0));
        rain.getData().add(new XYChart.Data<>(6,0));

        topChart.getData().add(rain);*/

    } //Opsætter data i øverste chart

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
        fjernVisibility();
        bottomChart.setVisible(true);
        Axis<String> xAxis = bottomChart.getXAxis();
        xAxis.setLabel("Date");

        Axis<String> yAxis = bottomChart.getYAxis();
        yAxis.setLabel("Weather");

        XYChart.Series<String, String> degrees = new XYChart.Series<String,String>();
        degrees.setName(String.valueOf(bottomIntervalChoice));

        degrees.getData().add(new XYChart.Data<>("Date",String.valueOf(bottomIntervalChoice)));
        /*degrees.getData().add(new XYChart.Data<>(1,5));
        degrees.getData().add(new XYChart.Data<>(2,5));
        degrees.getData().add(new XYChart.Data<>(3,8));
        degrees.getData().add(new XYChart.Data<>(4,7));
        degrees.getData().add(new XYChart.Data<>(5,6));
        degrees.getData().add(new XYChart.Data<>(6,9));*/

        topChart.getData().add(degrees);

        /*XYChart.Series<Number, Number> wind = new XYChart.Series<Number,Number>();
        wind.setName("Vind");

        wind.getData().add(new XYChart.Data<>(0,4.6));
        wind.getData().add(new XYChart.Data<>(0.5,5.8));
        wind.getData().add(new XYChart.Data<>(1,2.2));
        wind.getData().add(new XYChart.Data<>(1.5,1.6));
        wind.getData().add(new XYChart.Data<>(2,3.1));
        wind.getData().add(new XYChart.Data<>(2.5,3.3));
        wind.getData().add(new XYChart.Data<>(3,4.1));
        wind.getData().add(new XYChart.Data<>(3.5,7.8));
        wind.getData().add(new XYChart.Data<>(4,5.1));
        wind.getData().add(new XYChart.Data<>(4.5,7.4));
        wind.getData().add(new XYChart.Data<>(5,7.3));
        wind.getData().add(new XYChart.Data<>(5.5,7.8));
        wind.getData().add(new XYChart.Data<>(6,9.4));
        wind.getData().add(new XYChart.Data<>(6.5,6.4));

        bottomChart.getData().add(wind);

        XYChart.Series<Number, Number> rain = new XYChart.Series<Number,Number>();
        rain.setName("Nedbør");

        rain.getData().add(new XYChart.Data<>(0,0));
        rain.getData().add(new XYChart.Data<>(1,0));
        rain.getData().add(new XYChart.Data<>(2,0));
        rain.getData().add(new XYChart.Data<>(3,0));
        rain.getData().add(new XYChart.Data<>(4,3.3));
        rain.getData().add(new XYChart.Data<>(5,0));
        rain.getData().add(new XYChart.Data<>(6,0));

        bottomChart.getData().add(rain);*/
    } //opsætter data i nederste chart

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
                topRightDate.setDisable(false);
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

    public void fjernVisibility() {
        topChart.setVisible(false);
        topChart.getData().clear();
        bottomChart.setVisible(false);
        bottomChart.getData().clear();
    } //Viser og gemmer charts

    VejrStationDao vjs = new VejrStationDaoImpl();

}