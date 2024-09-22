package app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import app.classes.Global;

public class JDBCforPeriodTracker {

    public static final String DATABASE = "jdbc:sqlite:database/Movies.db";

    public JDBCforPeriodTracker() {
        System.out.println("Created JDBC Connection Object");
    }

    //GET DATA FOR CITY
    public ArrayList<Global> getCityName(String yearLength, String[] selectedCities, String startYear, String comparedRange) {
        ArrayList<Global> globalData = new ArrayList<>();
        
        String query = """
            SELECT p.Year, p.population, tg.AverageTemperature 
            FROM TempOfGLobal tg
            JOIN Population p ON p.year = tg.Year
            """;        
        
        try (Connection connection = DriverManager.getConnection(DATABASE);
             Statement statement = connection.createStatement()) {
            
            statement.setQueryTimeout(15);  // Set query timeout
    
            // Execute the query and process the results
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Global global = new Global();
                    global.setYear(resultSet.getInt("Year"));
                    global.setAverageTemp(resultSet.getFloat("AverageTemperature"));  // Use getFloat() for floating point data
                    global.setPopulation(resultSet.getInt("Population"));
                    globalData.add(global);
                }
            }
    
        } catch (SQLException e) {
            System.err.println("Error executing getGlobalDatafromWord: " + e.getMessage());
        }
    
        return globalData;
    }

    //GET DATA FOR STATE
    public ArrayList<Global> getStateData(String yearLength, String[] selectedStates, String startYear, String comparedRange) {
        ArrayList<Global> globalData = new ArrayList<>();
        
        String query = """
            SELECT p.Year, p.population, tg.AverageTemperature
            FROM TempOfGLobal tg
            JOIN Population p ON p.year = tg.Year
            WHERE p.state IN ('%s')
            AND p.year >= '%s'
            """.formatted(String.join("','", selectedStates), startYear);
        
        try (Connection connection = DriverManager.getConnection(DATABASE);
             Statement statement = connection.createStatement()) {
            
            statement.setQueryTimeout(15);  // Set query timeout
            
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Global global = new Global();
                    global.setYear(resultSet.getInt("Year"));
                    global.setAverageTemp(resultSet.getFloat("AverageTemperature"));
                    global.setPopulation(resultSet.getInt("Population"));
                    globalData.add(global);
                }
            }
        
        } catch (SQLException e) {
            System.err.println("Error executing getStateData: " + e.getMessage());
        }
        
        return globalData;
    }
    
    //GET DATA FOR COUNTRY
    public ArrayList<Global> getCountryData(String yearLength, String[] selectedCountries, String startYear, String comparedRange) {
        ArrayList<Global> globalData = new ArrayList<>();
        
        String query = """
            SELECT p.Year, p.population, tg.AverageTemperature
            FROM TempOfGLobal tg
            JOIN Population p ON p.year = tg.Year
            WHERE p.country IN ('%s')
            AND p.year >= '%s'
            """.formatted(String.join("','", selectedCountries), startYear);
        
        try (Connection connection = DriverManager.getConnection(DATABASE);
             Statement statement = connection.createStatement()) {
            
            statement.setQueryTimeout(15);  // Set query timeout
            
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Global global = new Global();
                    global.setYear(resultSet.getInt("Year"));
                    global.setAverageTemp(resultSet.getFloat("AverageTemperature"));
                    global.setPopulation(resultSet.getInt("Population"));
                    globalData.add(global);
                }
            }
        
        } catch (SQLException e) {
            System.err.println("Error executing getCountryData: " + e.getMessage());
        }
        
        return globalData;
    }
    
    //COMPARE WITH SAMPLE SPACE (I.e. If user input 5, then it will retrieve the next 5 rows for comparison)
    public ArrayList<Global> comparePopulationByRange(String startYear, int range) {
        ArrayList<Global> globalData = new ArrayList<>();
        
        String query = """
            SELECT p.Year, p.population, tg.AverageTemperature
            FROM TempOfGLobal tg
            JOIN Population p ON p.year = tg.Year
            WHERE p.year >= '%s'
            ORDER BY p.year
            LIMIT %d
            """.formatted(startYear, range);
        
        try (Connection connection = DriverManager.getConnection(DATABASE);
             Statement statement = connection.createStatement()) {
            
            statement.setQueryTimeout(15);  // Set query timeout
            
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Global global = new Global();
                    global.setYear(resultSet.getInt("Year"));
                    global.setAverageTemp(resultSet.getFloat("AverageTemperature"));
                    global.setPopulation(resultSet.getInt("Population"));
                    globalData.add(global);
                }
            }
        
        } catch (SQLException e) {
            System.err.println("Error executing comparePopulationByRange: " + e.getMessage());
        }
        
        return globalData;
    }
    

}
