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

    // GET THE FIRST YEAR FOR GLOBAL TEMPERATURE DATA + GET THE TEMPERATURE FOR THAT YEAR

    public Global getFirstYearTemp() {
        Global worldtemperature = new Global();
    
        try (Connection connection = DriverManager.getConnection(DATABASE);
             Statement statement = connection.createStatement();) {
    
            statement.setQueryTimeout(15);
    
            // Correct query to fetch all required fields
            String query = """
                    SELECT t.year, ROUND(t.AverageTemperature, 2) AS AverageTemperature
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

        // Query to fetch both year and average temperature for the last year
            String query = """
                    SELECT t.year, ROUND(t.AverageTemperature, 2) AS AverageTemperature
                    FROM TempOfGlobal t
                    WHERE t.year = (SELECT MAX(year) 
                    FROM TempOfGlobal)
                    """;
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                worldtemperature.setYear(resultSet.getInt("year"));
                worldtemperature.setAverageTemp(resultSet.getFloat("AverageTemperature"));
            }

        } catch (SQLException e) {
            System.err.println("Error found :getLastYearTemp() " + e.getMessage());
        }

        return worldtemperature;
    }


    // GET THE POPULATION FOR THE FIRST YEAR
    public Global getPopulationFirstYear() {
        
    Global populationData = new Global();

    try (Connection connection = DriverManager.getConnection(DATABASE);
         
        Statement statement = connection.createStatement();) {
        
        statement.setQueryTimeout(15);
        
        String query = """
            SELECT p.year, p.population
            FROM Population p
            WHERE p.year = (
                SELECT MIN(year)
                FROM Population
            )
            AND p.countryID = 'WLD'
            """ ;

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            populationData.setYear(resultSet.getInt("year"));
            populationData.setPopulation(resultSet.getInt("population"));

                        // Check if the population value is valid
                        int population = resultSet.getInt("population");
                        if (resultSet.wasNull()) {
                            System.err.println("Population value is null");
                        } else {
                            populationData.setPopulation(population);
                        }

        }

    } catch (SQLException e) {
        System.err.println("Error found: getPopulationFirstYear() " + e.getMessage());
    }

    return populationData;
}

    


// GET THE LAST YEAR FOR GLOBAL POPULATION DATA
public int getPopulationLastYear() {

    int lastYear = -1;  // Initialize with a default value

    try (Connection connection = DriverManager.getConnection(DATABASE);
         Statement statement = connection.createStatement()) {
        
        statement.setQueryTimeout(15);

        // Query to get the last year for population data
        String query = """
            SELECT p.year
            FROM Population p
            WHERE p.year = (SELECT MAX(year)
            FROM Population)
            """;

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            lastYear = resultSet.getInt("year");
        }

    } catch (SQLException e) {
        System.err.println("Error found in getPopulationLastYear(): " + e.getMessage());
    }

    return lastYear;
}





}
