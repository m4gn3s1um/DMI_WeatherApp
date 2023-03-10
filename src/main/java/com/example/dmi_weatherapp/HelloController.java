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
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    private LineChart<Number, Number> topChart, bottomChart, topChartHours, bottomChartHours;
    @FXML
    private Label topMiddelværdi, topMedian, topMidAfMidt, bottomMiddelværdi, bottomMedian, bottomMidAfMidt;
    @FXML
    private Button searchButton;

    public HelloController() throws SQLException {}

    public void initialize(){
            showWeatherStations();
        setDateValue();
        setChoiceboxValues();
        System.out.print(topLeftDate.getValue());
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
        System.out.println(Date.valueOf(String.valueOf(topLeftDate.getValue())));
    } //Opdaterer en String med dato, som kan bruges til SQL

    public void generateTop(ActionEvent actionEvent) {

        ObservableList valgteIndeks = vejrStationList.getSelectionModel().getSelectedIndices();

        for(Object indeks : valgteIndeks)
        {
            VejrStation vejrSt = (VejrStation) vejrStationList.getItems().get((int)indeks);

            System.out.println("GenerateTop");
            topChart.setVisible(true);

            Axis<Number> xAxis = topChart.getXAxis();
            xAxis.setLabel("Dato");
            Axis<Number> yAxis = topChart.getYAxis();
            yAxis.setLabel("Vejr");

            Axis<Number> hourxAxis = topChartHours.getXAxis();
            xAxis.setLabel("Dato");
            Axis<Number> houryAxis = topChartHours.getYAxis();
            yAxis.setLabel("Vejr");

            XYChart.Series<Number, Number> days = new XYChart.Series<Number,Number>();
            XYChart.Series<Number, Number> hours = new XYChart.Series<Number, Number>();

            String useThisDate = String.valueOf(topLeftDate.getValue());

            int dateCheck = Integer.valueOf(String.valueOf(topLeftDate.getValue()).substring(8,10)) + 6;
            String numbers = String.valueOf(dateCheck);
            char first = numbers.charAt(0);
            char second = numbers.charAt(1);

            StringBuilder dateString = new StringBuilder(String.valueOf(useThisDate));

            if(dateString.charAt(8) == '0' && dateString.charAt(9) =='3' + '2' + '1') {
                dateString.setCharAt(8, '0');  // Løs det så man ikke får en fejl hvis det er 01 - 09
                dateString.setCharAt(9,second);
            }
            else if(dateString.charAt(8) == '0' && dateString.charAt(9) == '4' + '5' + '6' + '7' + '8' + '9'){
                dateString.setCharAt(8,'1');
                dateString.setCharAt(9, second);
            }

            dateString.setCharAt(8, first);
            dateString.setCharAt(9, second);

            List<Måling> målinger = vjs.getMålingData(vejrSt.getStationID(), Timestamp.valueOf(String.valueOf(topLeftDate.getValue() + " 00:00:00")), Timestamp.valueOf(String.valueOf(topRightDate.getValue() + " 23:00:00")));
            List<Måling> hour_målinger = vjs.getHourData(vejrSt.getStationID(),Timestamp.valueOf(String.valueOf(topLeftDate.getValue() + " 00:00:00")), Timestamp.valueOf(String.valueOf(topLeftDate.getValue() + " 23:00:00")));
            List<Måling> week_målinger = vjs.getWeekData(vejrSt.getStationID(), Timestamp.valueOf(String.valueOf(topLeftDate.getValue() + " 00:00:00")), Timestamp.valueOf(String.valueOf(dateString + " 23:00:00")));

            if(topDataTypeChoice.getValue() == "Nedbør") {
                if (topIntervalChoice.getValue() == "Timer") {
                    topChart.getData().clear();
                    topChartHours.setVisible(true);
                    topChart.setVisible(false);
                    for (Måling mål : hour_målinger) {

                            hours.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(11, 13)), Float.valueOf(mål.getNedbør())));

                            System.out.println(mål.getMålingID());
                    }
                }
                if(topIntervalChoice.getValue() == "Døgn"){
                    topChart.getData().clear();
                    topChartHours.setVisible(false);
                    topChart.setVisible(true);
                    for (Måling mål : målinger) {
                        days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getNedbør())));
                        System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                        System.out.println(mål.getMålingID());
                    }
                }
                if(topIntervalChoice.getValue() == "Uger"){
                    topChart.getData().clear();
                    topChartHours.setVisible(false);
                    topChart.setVisible(true);
                    for (Måling mål : week_målinger) {
                        days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getNedbør())));
                        System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                        System.out.println(mål.getMålingID());
                    }
                }
            }

            if(topDataTypeChoice.getValue() == "Nedbørsminutter"){
                if (topIntervalChoice.getValue() == "Timer") {
                    topChart.getData().clear();
                    topChartHours.setVisible(true);
                    topChart.setVisible(false);
                    for (Måling mål : hour_målinger) {
                        hours.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(11, 13)), Float.valueOf(mål.getNedbørsMinutter())));
                        System.out.println(mål.getMålingID());
                    }
                }
                if(topIntervalChoice.getValue() == "Døgn"){
                    topChart.getData().clear();
                    topChartHours.setVisible(false);
                    topChart.setVisible(true);
                    for (Måling mål : målinger) {
                        days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getNedbørsMinutter())));
                        System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                        System.out.println(mål.getMålingID());
                    }
                }
                if(topIntervalChoice.getValue() == "Uger"){
                    topChart.getData().clear();
                    topChartHours.setVisible(false);
                    topChart.setVisible(true);
                    for (Måling mål : week_målinger) {
                        days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getNedbørsMinutter())));
                        System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                        System.out.println(mål.getMålingID());
                    }
                }
            }

            if(topDataTypeChoice.getValue() == "Middeltemperatur"){
                if (topIntervalChoice.getValue() == "Timer") {
                    topChart.getData().clear();
                    topChartHours.setVisible(true);
                    topChart.setVisible(false);
                    for (Måling mål : hour_målinger) {
                        hours.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(11, 13)), Float.valueOf(mål.getMiddelTemp())));
                        System.out.println(mål.getMålingID());
                    }
                }
                if(topIntervalChoice.getValue() == "Døgn"){
                    topChart.getData().clear();
                    topChartHours.setVisible(false);
                    topChart.setVisible(true);
                    for (Måling mål : målinger) {
                        days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getMiddelTemp())));
                        System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                        System.out.println(mål.getMålingID());
                    }
                }
                if(topIntervalChoice.getValue() == "Uger"){
                    topChart.getData().clear();
                    topChartHours.setVisible(false);
                    topChart.setVisible(true);
                    for (Måling mål : week_målinger) {
                        days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getMiddelTemp())));
                        System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                        System.out.println(mål.getMålingID());
                    }
                }
            }

            if(topDataTypeChoice.getValue() == "Middelvindhastighed"){
                if (topIntervalChoice.getValue() == "Timer") {
                    topChart.getData().clear();
                    topChartHours.setVisible(true);
                    topChart.setVisible(false);
                    for (Måling mål : hour_målinger) {
                        hours.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(11, 13)), Float.valueOf(mål.getMiddelVind())));
                        System.out.println(mål.getMålingID());
                    }
                }
                if(topIntervalChoice.getValue() == "Døgn"){
                    topChart.getData().clear();
                    topChartHours.setVisible(false);
                    topChart.setVisible(true);
                    for (Måling mål : målinger) {
                        days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getMiddelVind())));
                        System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                        System.out.println(mål.getMålingID());
                    }
                }
                if(topIntervalChoice.getValue() == "Uger"){
                    topChart.getData().clear();
                    topChartHours.setVisible(false);
                    topChart.setVisible(true);
                    for (Måling mål : week_målinger) {
                        days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getMiddelVind())));
                        System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                        System.out.println(mål.getMålingID());
                    }
                }
            }

            topChart.getData().addAll(days);
            topChartHours.getData().addAll(hours);
        }
    }//Opsætter data i øverste chart

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

            Axis<Number> hourxAxis = bottomChartHours.getXAxis();
            xAxis.setLabel("Dato");
            Axis<Number> houryAxis = bottomChartHours.getYAxis();
            yAxis.setLabel("Vejr");

            XYChart.Series<Number, Number> days = new XYChart.Series<Number,Number>();
            XYChart.Series<Number, Number> hours = new XYChart.Series<Number, Number>();

            String useThisDate = String.valueOf(bottomLeftDate.getValue());

            int dateCheck = Integer.valueOf(String.valueOf(bottomLeftDate.getValue()).substring(8,10)) + 6;
            String numbers = String.valueOf(dateCheck);
            char first = numbers.charAt(0);
            char second = numbers.charAt(1);

            StringBuilder dateString = new StringBuilder(String.valueOf(useThisDate));

            if(dateString.charAt(8) == '0' && dateString.charAt(9) =='3' + '2' + '1') {
                dateString.setCharAt(8, '0');  // Løs det så man ikke får en fejl hvis det er 01 - 09
                dateString.setCharAt(9,second);
            }
            else if(dateString.charAt(8) == '0' && dateString.charAt(9) == '4' + '5' + '6' + '7' + '8' + '9'){
                dateString.setCharAt(8,'1');
                dateString.setCharAt(9, second);
            }

            dateString.setCharAt(8, first);
            dateString.setCharAt(9, second);

            List<Måling> målinger = vjs.getMålingData(vejrSt.getStationID(), Timestamp.valueOf(String.valueOf(bottomLeftDate.getValue() + " 00:00:00")), Timestamp.valueOf(String.valueOf(bottomRightDate.getValue() + " 23:00:00")));
            List<Måling> hour_målinger = vjs.getHourData(vejrSt.getStationID(),Timestamp.valueOf(String.valueOf(bottomLeftDate.getValue() + " 00:00:00")), Timestamp.valueOf(String.valueOf(bottomLeftDate.getValue() + " 23:00:00")));
            List<Måling> week_målinger = vjs.getWeekData(vejrSt.getStationID(), Timestamp.valueOf(String.valueOf(bottomLeftDate.getValue() + " 00:00:00")), Timestamp.valueOf(String.valueOf(dateString + " 23:00:00")));

            if(bottomDataTypeChoice.getValue() == "Nedbør") {
                if (bottomIntervalChoice.getValue() == "Timer") {
                    bottomChart.getData().clear();
                    bottomChartHours.setVisible(true);
                    bottomChart.setVisible(false);
                    for (Måling mål : hour_målinger) {

                        hours.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(11, 13)), Float.valueOf(mål.getNedbør())));

                        System.out.println(mål.getMålingID());
                    }
                }
                if (bottomIntervalChoice.getValue() == "Døgn") {
                    bottomChart.getData().clear();
                    bottomChartHours.setVisible(false);
                    bottomChart.setVisible(true);
                    for (Måling mål : målinger) {
                        days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getNedbør())));
                        System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                        System.out.println(mål.getMålingID());
                    }
                }
                if (bottomIntervalChoice.getValue() == "Uger") {
                    bottomChart.getData().clear();
                    bottomChartHours.setVisible(false);
                    bottomChart.setVisible(true);
                    for (Måling mål : week_målinger) {
                        days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getNedbør())));
                        System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                        System.out.println(mål.getMålingID());
                    }
                }

                if (bottomDataTypeChoice.getValue() == "Nedbørsminutter") {
                    if (bottomIntervalChoice.getValue() == "Timer") {
                        bottomChart.getData().clear();
                        bottomChartHours.setVisible(true);
                        bottomChart.setVisible(false);
                        for (Måling mål : hour_målinger) {
                            hours.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(11, 13)), Float.valueOf(mål.getMiddelTemp())));
                            System.out.println(mål.getMålingID());
                        }
                    }
                    if (bottomIntervalChoice.getValue() == "Døgn") {
                        bottomChart.getData().clear();
                        bottomChartHours.setVisible(false);
                        bottomChart.setVisible(true);
                        for (Måling mål : målinger) {
                            days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getMiddelTemp())));
                            System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                            System.out.println(mål.getMålingID());
                        }
                    }
                    if (bottomIntervalChoice.getValue() == "Uger") {
                        bottomChart.getData().clear();
                        bottomChartHours.setVisible(false);
                        bottomChart.setVisible(true);
                        for (Måling mål : week_målinger) {
                            days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getMiddelTemp())));
                            System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                            System.out.println(mål.getMålingID());
                        }
                    }
                }

                if (bottomDataTypeChoice.getValue() == "Middeltemperatur") {
                    if (bottomIntervalChoice.getValue() == "Timer") {
                        bottomChart.getData().clear();
                        bottomChartHours.setVisible(true);
                        bottomChart.setVisible(false);
                        for (Måling mål : hour_målinger) {
                            hours.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(11, 13)), Float.valueOf(mål.getMiddelTemp())));
                            System.out.println(mål.getMålingID());
                        }
                    }
                    if (bottomIntervalChoice.getValue() == "Døgn") {
                        bottomChart.getData().clear();
                        bottomChartHours.setVisible(false);
                        bottomChart.setVisible(true);
                        for (Måling mål : målinger) {
                            days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getMiddelTemp())));
                            System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                            System.out.println(mål.getMålingID());
                        }
                    }
                    if (bottomIntervalChoice.getValue() == "Uger") {
                        bottomChart.getData().clear();
                        bottomChartHours.setVisible(false);
                        bottomChart.setVisible(true);
                        for (Måling mål : week_målinger) {
                            days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getMiddelTemp())));
                            System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                            System.out.println(mål.getMålingID());
                        }
                    }
                }

                if (bottomDataTypeChoice.getValue() == "Middelvindhastighed") {
                    if (bottomIntervalChoice.getValue() == "Timer") {
                        bottomChart.getData().clear();
                        bottomChartHours.setVisible(true);
                        bottomChart.setVisible(false);
                        for (Måling mål : hour_målinger) {
                            hours.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(11, 13)), Float.valueOf(mål.getMiddelVind())));
                            System.out.println(mål.getMålingID());
                        }
                    }
                    if (bottomIntervalChoice.getValue() == "Døgn") {
                        bottomChart.getData().clear();
                        bottomChartHours.setVisible(false);
                        bottomChart.setVisible(true);
                        for (Måling mål : målinger) {
                            days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getMiddelVind())));
                            System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                            System.out.println(mål.getMålingID());
                        }
                    }
                    if (bottomIntervalChoice.getValue() == "Uger") {
                        bottomChart.getData().clear();
                        bottomChartHours.setVisible(false);
                        bottomChart.setVisible(true);
                        for (Måling mål : week_målinger) {
                            days.getData().add(new XYChart.Data<>(Integer.valueOf(mål.getMålDato().substring(8, 10)), Float.valueOf(mål.getMiddelVind())));
                            System.out.println(Integer.valueOf(mål.getMålDato().substring(8, 10)));
                            System.out.println(mål.getMålingID());
                        }
                    }
                }
            }

            bottomChart.getData().addAll(days);
            bottomChartHours.getData().addAll(hours);
        }
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

        for(Object indeks : valgteIndeks)
        {
            VejrStation vejrSt = (VejrStation) vejrStationList.getItems().get((int)indeks);
            System.out.println(vejrSt.getStationID() + " - " + vejrSt.getStationNavn() + " Has been selected");
        }
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
                bottomRightDate.setDisable(true);
            }
        });
    } //Tjek om nederste datepicker skal være disabled

    public void topDataTypeValgt(MouseEvent mouseEvent){
        topDataTypeChoice.setOnAction((e) -> {

            topDataType = (String) topDataTypeChoice.getValue();
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
        //topChartHours.setVisible(false);
        //topChartHours.getData().clear();
        //bottomChartHours.setVisible(false);
        //bottomChartHours.getData().clear();
    } //Viser og gemmer charts

    VejrStationDao vjs = new VejrStationDaoImpl();

}