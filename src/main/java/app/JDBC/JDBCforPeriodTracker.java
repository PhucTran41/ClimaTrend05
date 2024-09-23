package app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import app.classes.City;
import app.classes.Country;
import app.classes.Global;
import app.classes.Population;
import app.classes.State;

public class JDBCforPeriodTracker {

    public static final String DATABASE = "jdbc:sqlite:OfficialDatabase.db";

    public JDBCforPeriodTracker() {
        System.out.println("Created JDBC Connection Object");
    }

    // get first year temperature
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
            System.err.println("Error found :getFirstYearTemp() in period tracker jdbc  " + e.getMessage());
        }

        return worldtemperature;
    }

    // get last
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
            System.err.println("Error found :getLastYearTemp() in period tracker jdbc " + e.getMessage());
        }

        return worldtemperature;
    }

    // get cuntry name
    public ArrayList<String> getCountryName() {
        ArrayList<String> countryName = new ArrayList<>();

        String query = """
                                SELECT DISTINCT c.countryName AS cn
                                    FROM TempOfCountry tc
                                    JOIN Country c ON c.countryID = tc.countryID
                                    ORDER BY cn;
                """;

        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("cn");
                countryName.add(name);
            }

        } catch (SQLException e) {
            System.err.println("Error executing from getCountryName method in period tracker jdbc " + e.getMessage());
        }
        return countryName;

    }

    // get city name
    public ArrayList<String> getCityName() {
        ArrayList<String> cityName = new ArrayList<>();

        String query = """
                          SELECT c1.cityname AS cn
                            FROM city c1
                            WHERE c1.cityname NOT IN (
                                SELECT c2.cityname
                                FROM city c2
                                WHERE c1.cityname = c2.cityname
                                AND (c1.latitude <> c2.latitude OR c1.longitude <> c2.longitude)
                            );

                """;

        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("cn");
                cityName.add(name);
            }

        } catch (SQLException e) {
            System.err.println("Error executing from getCityName method  in period tracker jdbc " + e.getMessage());
        }
        return cityName;

    }

    public ArrayList<String> getStateName() {
        ArrayList<String> stateName = new ArrayList<>();

        String query = """
                                SELECT DISTINCT s.stateName AS sn
                                    FROM TempOfState ts
                                    JOIN State s ON s.stateID = ts.stateID
                                    ORDER BY sn;
                """;

        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("sn");
                stateName.add(name);
            }

        } catch (SQLException e) {
            System.err.println("Error executing from getStateName method  in period tracker jdbc " + e.getMessage());
        }
        return stateName;
    }

    public State getStateAvgTemp(String state, int startYear, int endYear) {
        State states = new State();
        String query = """
                SELECT AVG(averagetemperature) AS AverageTemperature,
                    MIN(year) AS StartYear,
                    MAX(year) AS EndYear
                FROM TempOfState
                WHERE year BETWEEN ? AND ?
                    AND AverageTemperature IS NOT NULL
                    AND StateID = (SELECT StateID FROM State WHERE stateName = ?)
                """;
        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, startYear);
            statement.setInt(2, endYear);
            statement.setString(3, state);

            System.out.println(query);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                states.setStartYear(result.getInt("StartYear"));
                states.setEndYear(result.getInt("EndYear"));
                states.setAverageTemp(result.getFloat("AverageTemperature"));
                states.setName(state);
            }
        } catch (SQLException e) {
            System.err.println("Error found: getStateAvgTemp() in period tracker jdbc " + e.getMessage());
        }
        return states;
    }

    public City getCityAvgTemp(String cityname, int startYear, int endYear) {
        City city = new City();
        String query = """
                   SELECT AVG(averagetemperature) AS AverageTemperature,
                min(year) AS StartYear,
                max(year) AS EndYear
                FROM TempOfCity
                WHERE year BETWEEN ? AND ? AND
                averagetemperature IS NOT NULL AND
                cityID = ?;

                """;
        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, startYear);
            statement.setInt(2, endYear);
            statement.setString(3, cityname);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                city.setStartYear((result.getInt("StartYear")));
                city.setEndYear(result.getInt("EndYear"));
                city.setAverageTemp(result.getFloat("AverageTemperature"));
                city.setName(cityname);
            }
        } catch (SQLException e) {
            System.err.println("Error found: getCountryAvgTemp()  in period tracker jdbc " + e.getMessage());
        }
        return city;

    }

    public Country getAvgCountryTemp(String countryname, int startyear, int endyear) {
        Country country = new Country();

        String query = """
                SELECT AVG(averagetemperature) AS AverageTemperature,
                min(year) AS StartYear,
                max(year) AS EndYear
                FROM TempOfCountry
                WHERE year BETWEEN ? AND ? AND
                averagetemperature IS NOT NULL AND
                countryID = ?;
                """;

        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, startyear);
            statement.setInt(2, endyear);
            statement.setString(3, countryname);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                country.setStartYear(result.getInt("StartYear"));
                country.setEndYear(result.getInt("EndYear"));
                country.setAverageTemp(result.getFloat("AverageTemperature"));
            }
        } catch (SQLException e) {
            System.err.println("Error found: getAvgCountryTemp() in period tracker jdbc " + e.getMessage());
        }

        return country;

    }

    public Country getPopulationavgfromcCountry(String countryname, int starty, int endy) {

        Country country = new Country();
        String query = """
                    SELECT AVG(Population) AS AverageTemperature,
                    min(year) AS StartYear,
                    max(year) AS EndYear
                    FROM Population
                    WHERE year BETWEEN ? AND ? AND
                    population IS NOT NULL AND
                    countryID = ?;
                    """;

        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, starty);
            statement.setInt(2, endy);
            statement.setString(3, countryname);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                country.setStartYear(result.getInt("StartYear"));
                country.setEndYear(result.getInt("EndYear"));
                country.setAveragePopulation(result.getFloat("AverageTemperature"));
            }
        } catch (SQLException e) {
            System.err.println("Error found: getPopulation() in period tracker jdbc " + e.getMessage());
        }
        return country;
    }
}
