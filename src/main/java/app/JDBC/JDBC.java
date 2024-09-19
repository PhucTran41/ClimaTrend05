package app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.classes.Global;
import app.classes.Population;

public class JDBC {

    public static final String DATABASE = "jdbc:sqlite:OfficialDatabase.db";

    public JDBC() {
        System.out.println("Created JDBC Connection Object");
    }

    // GET THE FIRST YEAR FOR GLOBAL TEMPERATURE DATA + GET THE TEMPERATURE FOR THAT
    // YEAR

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

    public Population getPopulation(String region, int year) {
        Population population = new Population();
    
        String query = """
                SELECT *
                FROM Population
                WHERE countryID = ?
                AND year = ?
                """;
    
        try (Connection connection = DriverManager.getConnection(DATABASE);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
            // Correct the order of parameters
            preparedStatement.setString(1, region); // Set the region first
            preparedStatement.setInt(2, year);      // Set the year second
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    population.setCountryID(resultSet.getString("countryID"));
                    population.setYear(resultSet.getInt("year"));
                    population.setPopulation(resultSet.getLong("population")); // Set the population value
                    System.out.println("Population found: " + population.getPopulation());
                } else {
                    System.err.println("No data found for the given year and region");
                }
            }
    
        } catch (SQLException e) {
            System.err.println("Error found in getPopulation method: " + e.getMessage());
        }
    
        return population;
    }
    

    
   // GET THE FIRST YEAR FOR GLOBAL POPULATION DATA
public int getPopulationFirstYear() {
    int year = -1; // Set initial
    String query = """
            SELECT year AS firstYear
                FROM Population
                ORDER BY year
                LIMIT 1
        """;
    try (Connection connection = DriverManager.getConnection(DATABASE);
         Statement statement = connection.createStatement()) {

        statement.setQueryTimeout(60);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            year = resultSet.getInt("firstYear");
            System.out.println("First year found: " + year);
        }

    } catch (SQLException e) {
        System.err.println("Error found: getPopulationFirstYear() " + e.getMessage());
    }

    return year;
}


    // GET THE LAST YEAR FOR GLOBAL POPULATION DATA
public int getPopulationLastYear() {
    int year = -1;
    String query = """
            SELECT MAX(year) AS lastYear
            FROM Population
            
            """;
    try (Connection connection = DriverManager.getConnection(DATABASE);
         Statement statement = connection.createStatement()) {

        statement.setQueryTimeout(60);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            year = resultSet.getInt("lastYear");  
        }

    } catch (SQLException e) {
        System.err.println("Error found in getPopulationLastYear() " + e.getMessage());
    }

    return year;
}

}
