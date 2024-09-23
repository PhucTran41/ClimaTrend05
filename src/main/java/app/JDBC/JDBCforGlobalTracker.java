package app.JDBC;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import app.classes.Country;
import app.classes.Global;


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

        String temperatureChange;
        if ("Proportion".equals(OutputType)) {
            temperatureChange = "CASE WHEN A.AverageTemperature <> 0 THEN ((B.AverageTemperature - A.AverageTemperature) / A.AverageTemperature) * 100 ELSE NULL END AS \"Temp Change\"";
        } else {
            temperatureChange = "(B.AverageTemperature - A.AverageTemperature) AS \"Temp Change\"";
        }
    
        // Handle change calculation for population
        String populationChange;
        if ("Proportion".equals(OutputType)) {
            // Added CASE WHEN to handle division by zero
            populationChange = "CASE WHEN A.Population <> 0 THEN ((B.Population - A.Population) / A.Population) * 100 ELSE NULL END AS \"Population Change\"";
        } else {
            populationChange = "(B.Population - A.Population) AS \"Population Change\"";
        }
    
        
        String query = String.format("""
            SELECT A.countryName AS "Country", A.`Start Year`, A.AverageTemperature AS "Start Year Temperature", 
                   B.`End Year`, B.AverageTemperature AS "End Year Temperature", 
                   A.Population AS "Start Year Population", B.Population AS "End Year Population", 
                   %s, %s
            FROM
                (SELECT c.countryName, tc.Year AS "Start Year", tc.AverageTemperature, p.Population 
                 FROM Country c
                 JOIN TempOfCountry tc ON c.countryID = tc.countryID
                 JOIN Population p ON c.countryID = p.countryID AND tc.Year = p.year
                 WHERE tc.Year = ?) AS A
            JOIN
                (SELECT c.countryName, tc.Year AS "End Year", tc.AverageTemperature, p.Population 
                 FROM Country c
                 JOIN TempOfCountry tc ON c.countryID = tc.countryID
                 JOIN Population p ON c.countryID = p.countryID AND tc.Year = p.year
                 WHERE tc.Year = ?) AS B
            ON A.countryName = B.countryName
            WHERE A.countryName IN (%s)
        """, temperatureChange, populationChange, placeholders);

        System.out.println(query);

        try (Connection connection = DriverManager.getConnection(DATABASE);
        PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, startYear);
                statement.setString(2, endYear);

                for (int i = 0; i < countries.length; i++) {
                    statement.setString(3 + i, countries[i]);
                }
             
            statement.setQueryTimeout(15);  // Set query timeout
    
            // Execute the query and process the results
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Country country = new Country();
                    country.setCountryName(rs.getString("Country"));
                    country.setStartYear(rs.getInt("Start Year"));
                    country.setEndYear(rs.getInt("End Year"));
                    country.setStartYearTemperature(rs.getFloat("Start Year Temperature"));
                    country.setEndYearTemperature(rs.getFloat("End Year Temperature"));
                    country.setChange(rs.getFloat("Temp Change"));
                    country.setStartYearPopulation(rs.getLong("Start Year Population"));
                    country.setEndYearPopulation(rs.getLong("End Year Population"));
                    country.setPopulationChange(rs.getDouble("Population Change"));
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
            SELECT tg.Year, p.population, tg.AverageTemperature
                FROM TempOfGLobal tg
                LEFT JOIN Population p ON p.year = tg.Year
                LEFT JOIN Country c ON p.countryID = c.countryID
                WHERE tg.Year BETWEEN ? AND ?
                  AND (c.countryName = 'World' OR p.countryID IS NULL)
            """;
      
                // Adding conditions based on the user's selection for output and order
            if ("Population".equals(output)) {
                query += " ORDER BY p.population ";  // Sort by population
            } else if ("Average Temperature".equals(output)) {
                query += " ORDER BY tg.AverageTemperature ";  // Sort by temperature
            } else {
                query += " ORDER BY tg.Year ";
            }

            // Adding ascending or descending order based on user's selection
            if ("Ascending".equals(orderby)) {
                query += " ASC;";
            } else if ("Descending".equals(orderby)) {
                query += " DESC;";
            }


        
            
        try (Connection connection = DriverManager.getConnection(DATABASE);
        PreparedStatement statement = connection.prepareStatement(query)) {

   
            
            statement.setString(1, startYear);
            statement.setString(2, endYear);
            System.out.println("Executing query: " + query);
            statement.setQueryTimeout(15);  // Set query timeout
            
            // Execute the query and process the results
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    
                    Global global = new Global();
                    global.setYear(resultSet.getInt("Year"));
                    global.setAverageTemp(resultSet.getFloat("AverageTemperature"));
                    
    
                    long population = resultSet.getLong("population");
                    if (resultSet.wasNull()) {
                        global.setPopulation(0);
                    } else {
                        global.setPopulation(population);
                    }
    
                    globalData.add(global);
                    
                }
            }
    
        } catch (SQLException e) {
            System.err.println("Error executing getGlobalDatafromWord: " + e.getMessage());
        }
    
        return globalData;
    }
    
}



