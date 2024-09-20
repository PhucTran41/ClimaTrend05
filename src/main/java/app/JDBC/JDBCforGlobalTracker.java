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

    //retrieve datas from back end and get it to the table
    public ArrayList<Global> getGlobalDatafromCountry(String displayRegion, String output, String orderby, 
                                           String outputType, String[] countries, String startYear, String endYear) {
        ArrayList<Global> globalData = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT * FROM TempOfCountry WHERE 1=1");

        if (countries != null && countries.length > 0) {
            query.append(" AND countryName IN (").append(String.join(",", countries)).append(")");
        }
        if (startYear != null && !startYear.isEmpty()) {
            query.append(" AND year >= ").append(startYear);
        }
        if (endYear != null && !endYear.isEmpty()) {
            query.append(" AND year <= ").append(endYear);
        }
        if (orderby != null && !orderby.isEmpty()) {
            query.append(" ORDER BY ").append(output != null ? output : "year").append(" ").append(orderby.equals("Ascending") ? "ASC" : "DESC");
        }

        try (Connection connection = DriverManager.getConnection(DATABASE);
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
             
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Global global = new Global();
                global.setYear(resultSet.getInt("Year"));
                global.setName(resultSet.getString("countryName"));
                global.setFirstYearTemperature(resultSet.getDouble("firstYearTemperature"));
                global.setLastYearTemperature(resultSet.getDouble("lastYearTemperature"));
                global.setChange(resultSet.getDouble("change"));
                globalData.add(global);
            }

        } catch (SQLException e) {
            System.err.println("Error executing getGlobalDatafromCountry " + e.getMessage());
        }

        return globalData;
    }

    //Get data from word
    public ArrayList<Global> getGlobalDatafromWord(String output, String orderby, String startYear, String endYear) {
        ArrayList<Global> globalData = new ArrayList<>();
        
        String query = """
            SELECT p.Year, p.population, tg.AverageTemperature 
            FROM TempOfGLobal tg
            JOIN Population p ON p.year = tg.Year
            """;


        //ORDER BY p.year DESC;
        //

        
        
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
    
}



