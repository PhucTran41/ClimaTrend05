package app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.util.Arrays;
import app.classes.Country;
import app.classes.Global;
import app.page.GlobalTracker;
import java.util.Collections;


public class JDBCforGlobalTracker {

    public static final String DATABASE = "jdbc:sqlite:OfficialDatabase.db";

    public JDBCforGlobalTracker() {
        System.out.println("Created JDBC Connection Object");
    }

    // get city name from country
    public ArrayList<String> getCityNameFromCountry() {
        ArrayList<String> cityName = new ArrayList<String>();

        String query = """
            SELECT DISTINCT c.cityName FROM CITY c
            JOIN Country ct 
            ON c.countryID = ct.countryID
        """;

        ////FIX THE QUERY

        try (Connection connection = DriverManager.getConnection(DATABASE);
                Statement statement = connection.createStatement()) {

                statement.setQueryTimeout(15);

              
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()){
                    String name = resultSet.getString("cityName");
                    cityName.add(name);
                    }
                }
            
        } catch (SQLException e) {
            System.err.println("Error executing from getCityNameFromCountry method " + e.getMessage());
        }

        return cityName;
        
    }

    // get state name from country
    public ArrayList<String> getStateNameFromCountry() {
        ArrayList<String> stateName = new ArrayList<String>();
        String query = """
                SELECT DISTINCT s.stateName FROM State s
                JOIN Country c
                ON s.countryID = c.countryID
                ORDER BY stateName

                """;
                ////FIX THE QUERY
        
    
        try (Connection connection = DriverManager.getConnection(DATABASE);
                Statement statement = connection.createStatement();) {

            statement.setQueryTimeout(15);
            
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                String name = resultSet.getString("stateName");
                stateName.add(name);
            }
        }

        } catch (SQLException e) {
            System.err.println("Error executing from getStateNameFromCountry method " + e.getMessage());
        }

        return stateName;
    }

    // get first year from global temperature

    public Global getFirstYear() {
        Global global = new Global();

        String query = """
                SELECT year 
                FROM TempOfGlobal 
                ORDER BY year 
                LIMIT 1;


                """;;

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
    
            String query = """
                    SELECT year 
                    FROM TempOfGlobal 
                    ORDER BY year DESC 
                    LIMIT 1;

                    """;;

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

    //GEt global datas from the country 
    public ArrayList<Country> getGlobalDatafromCountry(String [] countries, String OutputType, String startYear, String endYear) {
        ArrayList<Country> countryData = new ArrayList<>();
        
        String placeholders = String.join(",", Collections.nCopies(countries.length, "?"));

        String changeOnceChosen;
        if ("Proportion".equals(OutputType)) {
            changeOnceChosen = "CASE WHEN A.AverageTemperature <> 0 THEN ((B.AverageTemperature - A.AverageTemperature) / A.AverageTemperature) * 100 ELSE NULL END AS \"CHANGE\"";
        } else {
            changeOnceChosen = "(B.AverageTemperature - A.AverageTemperature) AS \"CHANGE\"";
        }
    
        
        String query = String.format("""
            SELECT A.countryName AS "Country", A.`Start Year`, A.AverageTemperature AS "Start Year Temperature", 
                   B.`End Year`, B.AverageTemperature AS "End Year Temperature", 
                   %s
            FROM
                (SELECT c.countryName, tc.Year AS "Start Year", tc.AverageTemperature 
                 FROM Country c
                 JOIN TempOfCountry tc ON c.countryID = tc.countryID
                 WHERE tc.Year = ?) AS A
            JOIN
                (SELECT c.countryName, tc.Year AS "End Year", tc.AverageTemperature 
                 FROM Country c
                 JOIN TempOfCountry tc ON c.countryID = tc.countryID
                 WHERE tc.Year = ?) AS B
            ON A.countryName = B.countryName
           WHERE A.countryName IN (%s)
    """, changeOnceChosen, placeholders);

   

        try (Connection connection = DriverManager.getConnection(DATABASE);
        PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, startYear);
                statement.setString(2, endYear);
                for (int i = 0; i < countries.length; i++) {
                    statement.setString(3 + i, countries[i]);
                }
                
            
            statement.setQueryTimeout(15);  // Set query timeout
    
            // Execute the query and process the results
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Country country = new Country();
                    country.setCountryName(resultSet.getString("Country"));
                    country.setStartYear(resultSet.getInt("Start Year"));
                    country.setEndYear(resultSet.getInt("End Year"));
                    country.setStartYearTemperature(resultSet.getFloat("Start Year Temperature"));  // Use getFloat() for floating point data
                    country.setEndYearTemperature(resultSet.getFloat("End Year Temperature"));  // Use getFloat() for floating point data
                    country.setChange(resultSet.getFloat("CHANGE"));  // Use getFloat() for floating point data
                    countryData.add(country);
                }
            }
    
        } catch (SQLException e) {
            System.err.println("Error executing getGlobalDatafromCountry: " + e.getMessage());
        }
    
        return countryData;
    }






            
    //Get global data from WORD
    public ArrayList<Global> getGlobalDatafromWord(String output, String orderby, String startYear, String endYear) {
        ArrayList<Global> globalData = new ArrayList<>();
        
        String query = """
            SELECT p.Year, p.population, tg.AverageTemperature 
            FROM TempOfGLobal tg
            JOIN Population p ON p.year = tg.Year
            WHERE p.Year BETWEEN ? AND ?
            """;
      
                // Adding conditions based on the user's selection for output and order
    if ("Population".equals(output)) {
        query += "ORDER BY p.population ";  // Sort by population
    } else if ("Temperature".equals(output)) {
        query += "ORDER BY CAST(tg.AverageTemperature AS DECIMAL) ";  // Sort by temperature
    } else {
        query += "ORDER BY p.Year ";  // Default: sort by year
    }

    // Adding ascending or descending order based on user's selection
    if ("Ascending".equals(orderby)) {
        query += "ASC";
    } else if ("Descending".equals(orderby)) {
        query += "DESC";
    }


        
        
        try (Connection connection = DriverManager.getConnection(DATABASE);
        PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, startYear);
            statement.setString(2, endYear);

            statement.setQueryTimeout(15);  // Set query timeout
    
            // Execute the query and process the results
            try (ResultSet resultSet = statement.executeQuery()) {
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



