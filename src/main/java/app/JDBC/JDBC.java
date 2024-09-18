package app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.classes.Global;

public class JDBC {

    public static final String DATABASE = "jdbc:sqlite:OfficialDatabase.db";

    public JDBC() {
        System.out.println("Created JDBC Connection Object");
    }

    // GET THE FIRST YEAR FOR GLOBAL TEMPERATURE DATA

    public Global getFirstYearTemp() {
        Global worldtemperature = new Global();
    
        try (Connection connection = DriverManager.getConnection(DATABASE);
             Statement statement = connection.createStatement();) {
    
            statement.setQueryTimeout(15);
    
            // Correct query to fetch all required fields
            String query = """
                    SELECT t.year
                    FROM TempOfGlobal t
                    WHERE t.year = (SELECT MIN(year) 
                    FROM TempOfGlobal)
                    """;
            ResultSet resultSet = statement.executeQuery(query);
    
            // Ensure the resultSet contains data
            if (resultSet.next()) {
                worldtemperature.setYear(resultSet.getInt("year"));
                worldtemperature.setAverageTemp(resultSet.getFloat("AverageTemperature"));
            }
    
        } catch (SQLException e) {
            System.err.println("Error found :getFirstYearTemp() " + e.getMessage());
        }
    
        return worldtemperature;
    }
    

    // GET THE LAST YEAR FOR GLOBAL TEMPERATURE DATA

    public Global getLastYearTemp() {

        Global worldtemperature = new Global();

        try (Connection connection = DriverManager.getConnection(DATABASE);
                Statement statement = connection.createStatement();) {

            statement.setQueryTimeout(15);

            String query = """

            SELECT t.year
            FROM TempOfGlobal t
            WHERE t.year = (SELECT MAX(year) 
            FROM TempOfGlobal)
            """;

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                worldtemperature.setYear(resultSet.getInt("year"));

            worldtemperature.setYear(resultSet.getInt("year"));
            }

        } catch (SQLException e) {

            System.err.println("Error found :getFirstYearTemp() " + e.getMessage());
        }

        return worldtemperature;
    }

    // GET THE FIRST YEAR FOR GLOBAL POPULATION DATA

    public int getPopulationFirstYear(){
        
        int initialYear = 0;

          try (Connection connection = DriverManager.getConnection(DATABASE);
                Statement statement = connection.createStatement();) {

                    } catch (SQLException e) {
            System.err.println("Error found :getPopulationFirstYear() " + e.getMessage());
                    }
        return initialYear;
    }

}