package com.example.dmi_weatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.input.MouseEvent;

import java.awt.*;

public class HelloController
{

    @FXML
    private ChoiceBox topDataTypeChoice, bottomDataTypeChoice, topIntervalChoice, bottomIntervalChoice;
    @FXML
    private MFXListView vejrStationList;
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

    public void topRightDateSelected(ActionEvent actionEvent)
    {
    }

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