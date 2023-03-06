package com.example.dmi_weatherapp.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonStrategy {

    private static Connection instance;


    private SingletonStrategy(){
        try
        {
            // Connector til vores database
            instance = DriverManager.getConnection("jdbc:sqlserver://MSI-MAGNUS;database=DMI_DB;userName=sa;password=12345;encrypt=true;trustServerCertificate=true");
        }

        catch (SQLException e)
        {
            System.err.println("Could not create connection  " + e.getMessage());
        }
        System.out.println("Connected");
    }

    public static Connection getInstance() throws SQLException {
        if(instance == null)
            instance = DriverManager.getConnection("jdbc:sqlserver://MSI-MAGNUS;database=DMI_DB;userName=sa;password=12345;encrypt=true;trustServerCertificate=true");
        return instance;
    }
}