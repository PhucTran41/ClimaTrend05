package app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.classes.City;
import app.classes.Global;
import app.classes.State;

public class JDBCforCityTracker {

    public static final String DATABASE = "jdbc:sqlite:OfficialDatabase.db";

    public JDBCforCityTracker() {
        System.out.println("Created JDBC Connection Object");
    }

   
    // getcountry names
    public ArrayList<String> getCountryNames() {
        ArrayList<String> countryNames = new ArrayList<>();

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
                countryNames.add(name);
            }

        } catch (SQLException e) {
            System.err.println("Error executing getCountryNames: " + e.getMessage());
        }
        return countryNames;
    }

    // get cityname from country
    public ArrayList<String> getCityNameFromCountry() {
        ArrayList<String> cityNames = new ArrayList<>();
        String query = """
                SELECT DISTINCT cityName cn FROM TempOfCity tc
                    JOIN City c
                    ON c.cityID = tc.cityID;
                                """;

        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("cityName");
                cityNames.add(name);
            }

        } catch (SQLException e) {
            System.err.println("Error executing getCityNameFromCountry: " + e.getMessage());
        }
        return cityNames;
    }

    // Lấy năm đầu tiên từ bảng Population của một quốc gia
    public int getFirstYear(int countryID) {
        String query = """
                    SELECT MIN(year)
                    AS firstYear
                    FROM Population
                    WHERE countryID = ?
                """;

        int firstYear = -1;

        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, countryID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                firstYear = resultSet.getInt("firstYear");
            }

        } catch (SQLException e) {
            System.err.println("Error executing getFirstYear: " + e.getMessage());
        }

        return firstYear;
    }

    // Lấy năm cuối cùng từ bảng Population của một quốc gia
    public int getLastYear(int countryID) {
        String query = """
                SELECT MAX(year)
                    AS lastYear
                    FROM Population
                    WHERE countryID = ?
                """;
        int lastYear = -1;

        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, countryID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lastYear = resultSet.getInt("lastYear");
            }

        } catch (SQLException e) {
            System.err.println("Error executing getLastYear: " + e.getMessage());
        }

        return lastYear;
    }

    // Ví dụ về cách hiển thị thông tin quốc gia với năm đầu tiên và năm cuối cùng
    public void displayCountriesWithYears() {
        ArrayList<String> countryNames = getCountryNames();

        for (String countryName : countryNames) {
            int countryID = getCountryIDByName(countryName); // Hàm để lấy countryID dựa vào countryName
            int firstYear = getFirstYear(countryID);
            int lastYear = getLastYear(countryID);

            System.out.println("Country: " + countryName + ", First Year: " + firstYear + ", Last Year: " + lastYear);
        }
    }

    // Lấy countryID dựa vào tên quốc gia
    public int getCountryIDByName(String countryName) {
        String query = """
                SELECT countryID
                    FROM Country
                    WHERE countryName = ?
                """;

        int countryID = -1;

        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, countryName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                countryID = resultSet.getInt("countryID");
            }

        } catch (SQLException e) {
            System.err.println("Error executing getCountryIDByName: " + e.getMessage());
        }

        return countryID;
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

    
    public List<City> getCityDatafromCountry(String countryName, int endYear, int startYear, String statistic, String outputType) {
        List<City> cities = new ArrayList<>();
        
        String tempColumn = "AverageTemperature";  // Default to Average
        if ("Minimum Temperature".equals(statistic)) {
            tempColumn = "MinimumTemperature";
        } else if ("Maximum Temperature".equals(statistic)) {
            tempColumn = "MaximumTemperature";
        }
    
        String sql = "SELECT c.cityName, t1." + tempColumn + " as start_temp, " +
                     "t2." + tempColumn + " as end_temp, " +
                     "(t2." + tempColumn + " - t1." + tempColumn + ") as temp_change " +
                     "FROM City c " +
                     "JOIN TempOfCity t1 ON c.cityID = t1.cityID AND t1.year = ? " +
                     "JOIN TempOfCity t2 ON c.cityID = t2.cityID AND t2.year = ? " +
                     "JOIN Country co ON c.countryID = co.countryID " +
                     "WHERE co.countryName = ? " +
                     "GROUP BY c.cityID, c.cityName " +
                     "ORDER BY temp_change DESC";
        
        try (Connection conn = DriverManager.getConnection(DATABASE);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, startYear);
            pstmt.setInt(2, endYear);
            pstmt.setString(3, countryName);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                City city = new City();
                city.setName(rs.getString("cityName"));
                city.setFirstyear(startYear);
                city.setEndYear(endYear);
                city.setFirstYearTemp(rs.getFloat("start_temp"));
                city.setLastYtemp(rs.getFloat("end_temp"));
                
                float change = rs.getFloat("temp_change");
                if ("Percentage".equals(outputType)) {
                    if (city.getFirstYearTemp() != 0) {
                        float percentageChange = (change / city.getFirstYearTemp()) * 100;
                        city.setChanges(percentageChange);
                    } else {
                        city.setChanges(0); // Handle division by zero
                    }
                } else {
                    city.setChanges(change);
                }
    
                if ((endYear - startYear) != 0) {
                    city.setAverageChange(change / (endYear - startYear));
                } else {
                    city.setAverageChange(0); // Handle division by zero
                }
    
                // Average of first and last year temp for display
                city.setAverageTemp((city.getFirstYearTemp() + city.getLastYtemp()) / 2);
                
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return cities;
    }
    
    
        public ArrayList<State> getStateDatafromCountry(String stateName, int startYear, int endYear, String outputType) {
            ArrayList<State> stateData = new ArrayList<>();
    
            // FIX THISSSSS!!!!
            String query = """
                    SELECT 
                        s.stateName, 
                        AVG(CASE WHEN ts.Year = ? THEN ts.AverageTemperature END) AS start_temp,
                        AVG(CASE WHEN ts.Year = ? THEN ts.AverageTemperature END) AS end_temp,
                        AVG(CASE WHEN ts.Year = ? THEN ts.AverageTemperature END) - AVG(CASE WHEN ts.Year = ? THEN ts.AverageTemperature END) AS temp_change
                    FROM 
                        State s
                        JOIN TempOfState ts ON s.stateID = ts.stateID
                        JOIN Country co ON s.countryID = co.countryID
                    WHERE 
                        co.countryName = ?
                        AND ts.Year IN (?, ?)
                    GROUP BY
                        s.stateID, s.stateName
                    ORDER BY temp_change DESC
                """;
            try (Connection connection = DriverManager.getConnection(DATABASE);
                 PreparedStatement statement = connection.prepareStatement(query)) {
    
                statement.setInt(1, startYear);
                statement.setInt(2, endYear);
                statement.setInt(3, endYear);
                statement.setInt(4, startYear);
                statement.setString(5, stateName);
                statement.setInt(6, startYear);
                statement.setInt(7, endYear);
    
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        State state = new State();
                        state.setName(resultSet.getString("stateName"));
                        state.setFirstyear(resultSet.getInt("firstyear"));
                        state.setEndYear(resultSet.getInt("lastyear"));
                        state.setFirstYearTemp(resultSet.getFloat("firstyTemp"));
                        state.setLastYtemp(resultSet.getFloat("lastyTemp"));
                        float change = resultSet.getFloat("change");
                        state.setChanges(change);
                        
                        if ("Percentage".equals(outputType)) {
                            float percentageChange = (change / state.getFirstYearTemp()) * 100;
                            state.setChangeByPercentage(percentageChange);
                        } else {
                            state.setChangeByPercentage(change);
                        }
                        
                        state.setAverageChange(change / (endYear - startYear));
                        stateData.add(state);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error executing getStateDatafromCountry: " + e.getMessage());
            }
    
            return stateData;
        }

    // public ArrayList<State> getStateDatafromCountry(String countryName, int startYear, int endYear, String outputType) {
    //     ArrayList<State> stateData = new ArrayList<>();

    //     String query = """
    //         SELECT 
    //             s.stateName, 
    //             MIN(ts.Year) AS firstyear, 
    //             MAX(ts.Year) AS lastyear,
    //             AVG(CASE WHEN ts.Year = MIN(ts.Year) THEN ts.%s END) AS firstyTemp,
    //             AVG(CASE WHEN ts.Year = MAX(ts.Year) THEN ts.%s END) AS lastyTemp,
    //             AVG(CASE WHEN ts.Year = MAX(ts.Year) THEN ts.%s END) - AVG(CASE WHEN ts.Year = MIN(ts.Year) THEN ts.%s END) AS change,
    //             (AVG(CASE WHEN ts.Year = MAX(ts.Year) THEN ts.%s END) - AVG(CASE WHEN ts.Year = MIN(ts.Year) THEN ts.%s END)) / ABS(AVG(CASE WHEN ts.Year = MIN(ts.Year) THEN ts.%s END)) * 100 AS changebypercentage
    //         FROM 
    //             State s
    //             JOIN TempOfState ts ON s.stateID = ts.stateID
    //             JOIN Country co ON s.countryID = co.countryID
    //         WHERE 
    //             co.countryName = ?
    //             AND ts.Year BETWEEN ? AND ?
    //             AND ts.%s IS NOT NULL
    //         GROUP BY
    //             s.stateName
    //     """.formatted(outputType, outputType, outputType, outputType, outputType, outputType, outputType, outputType);

    //     try (Connection connection = DriverManager.getConnection(DATABASE);
    //          PreparedStatement statement = connection.prepareStatement(query)) {

    //         statement.setString(1, countryName);
    //         statement.setInt(2, startYear);
    //         statement.setInt(3, endYear);

    //         try (ResultSet resultSet = statement.executeQuery()) {
    //             while (resultSet.next()) {
    //                 State state = new State();
    //                 state.setName(resultSet.getString("stateName"));
    //                 state.setFirstyear(resultSet.getInt("firstyear"));
    //                 state.setLastyear(resultSet.getInt("lastyear"));
    //                 state.setFirstYearTemp(resultSet.getFloat("firstyTemp"));
    //                 state.setLastYtemp(resultSet.getFloat("lastyTemp"));
    //                 state.setChanges(resultSet.getFloat("change"));
    //                 state.setChangeByPercentage(resultSet.getFloat("changebypercentage"));
    //                 stateData.add(state);
    //             }
    //         }
    //     } catch (SQLException e) {
    //         System.err.println("Error executing getStateDatafromCountry: " + e.getMessage());
    //     }

    //     return stateData;
    // }

}