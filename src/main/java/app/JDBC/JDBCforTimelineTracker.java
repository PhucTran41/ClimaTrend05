package app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;


import app.classes.Global;
import kotlin.Result;

public class JDBCforTimelineTracker {

    public static final String DATABASE = "jdbc:sqlite:OfficialDatabase.db";

    public ArrayList<String> getCountryName(){
        ArrayList<String> countryName = new ArrayList<>();

        String query = """
                    SELECT DISTINCT countryName cn FROM TempOfCountry tc
                    JOIN Country c
                    ON c.countryID = tc.countryID;
    """;

        try (Connection connection = DriverManager.getConnection(DATABASE);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            ResultSet resultSet = preparedStatement.executeQuery();
        
                    while (resultSet.next()) {
                    String name = resultSet.getString("cn");
                    countryName.add(name);
                }
        
                } catch (SQLException e) {
                    System.err.println("Error executing from getCountryName method" + e.getMessage());
                }
                return countryName;
        
    }
        
        public ArrayList<String> getCityName(){
            ArrayList<String> cityName = new ArrayList<>();
    
            String query = """    
                    SELECT ci.cityName
                    FROM CITY ci
                    JOIN TempOfCity TC ON ci.cityID = TC.cityID
                    GROUP BY ci.cityName, TC.cityID
                    HAVING COUNT(DISTINCT TC.latitude) = 1 
                    AND COUNT(DISTINCT TC.longitude) = 1;

        """;
    
            try (Connection connection = DriverManager.getConnection(DATABASE);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
            
                        while (resultSet.next()) {
                        String name = resultSet.getString("cityName");
                        cityName.add(name);
                    }
            
                    } catch (SQLException e) {
                        System.err.println("Error executing from getCityName method" + e.getMessage());
                    }
                    return cityName;
            
        }

        public ArrayList<String> getStateName(){
            ArrayList<String> stateName = new ArrayList<>();

            try(Connection connection = DriverManager.getConnection(DATABASE);
                Statement statement = connection.createStatement();){
                
                statement.setQueryTimeout(40);

                String query = """
                SELECT DISTINCT stateName
                FROM TempOfState ts
                JOIN State s
                ON s.stateID = ts.stateID;
                """;
                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next()){
                    String name = resultSet.getString("stateName");
                    stateName.add(name);
                }

            } catch (SQLException e) {
                System.err.println("Error executing from getStateName method" + e.getMessage());
            }
            return stateName;
        }


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

}

