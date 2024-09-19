package app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;


import app.classes.Global;
import app.page.GlobalTracker;

public class JDBCforGlobalTracker {

    public static final String DATABASE = "jdbc:sqlite:OfficialDatabase.db";

    public JDBCforGlobalTracker() {
        System.out.println("Created JDBC Connection Object");
    }

    // get city name from country
    public ArrayList<String> getCityNameFromCountry() {
        ArrayList<String> cityName = new ArrayList<String>();

        try (Connection connection = DriverManager.getConnection(DATABASE);
                Statement statement = connection.createStatement();) {
            statement.setQueryTimeout(15);
            String query = "";
            statement.executeQuery(query);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                String name = resultSet.getString("cityName");
                cityName.add(name);
            }

        } catch (SQLException e) {
            System.err.println("Error executing from getCityNameFromCountry method " + e.getMessage());
        }

        return cityName;
    }

    // get state name from country
    public ArrayList<String> getStateNameFromCountry() {
        ArrayList<String> stateName = new ArrayList<String>();

        try (Connection connection = DriverManager.getConnection(DATABASE);
                Statement statement = connection.createStatement();) {
            statement.setQueryTimeout(15);
            String query = "";
            statement.executeQuery(query);
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()) {
                String name = resultSet.getString("stateName");
                stateName.add(name);
            }

        } catch (SQLException e) {
            System.err.println("Error executing from getStateNameFromCountry method " + e.getMessage());
        }

        return stateName;
    }

    // get first year from global temperature

    public Global getFirstYear() {
        Global global = new Global();

        String query = "";

    try (Connection connection = DriverManager.getConnection(DATABASE);
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        
        ResultSet resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                global.setEndYear(resultSet.getInt("year"));
            }

        } catch (SQLException e) {
            System.err.println("Error executing from getFirstYear method" + e.getMessage());
        }
        return global;
    }

    // get last year from global temperature

    public Global getLastYear() {
            Global global = new Global();
    
            String query = "";

    try (Connection connection = DriverManager.getConnection(DATABASE);
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        
        ResultSet resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                global.setEndYear(resultSet.getInt("year"));
            }
    
            } catch (SQLException e) {
                System.err.println("Error executing from getLastYear method" + e.getMessage());
            }
            return global;
        }


    //get country Name 
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
}



