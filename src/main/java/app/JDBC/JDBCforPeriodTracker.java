package app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import app.classes.City;
import app.classes.Global;
import app.classes.Population;
import app.classes.State;

public class JDBCforPeriodTracker {

    public static final String DATABASE = "jdbc:sqlite:database/Movies.db";

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
            System.err.println("Error found :getFirstYearTemp() " + e.getMessage());
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
            System.err.println("Error found :getLastYearTemp() " + e.getMessage());
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
            System.err.println("Error executing from getCountryName method" + e.getMessage());
        }
        return countryName;

    }

    // get city name
    public ArrayList<String> getCityName() {
        ArrayList<String> cityName = new ArrayList<>();

        String query = """
                                SELECT DISTINCT c.cityName AS cn
                                    FROM TempOfCity tc
                                    JOIN City c ON c.cityID = tc.cityID
                                    ORDER BY cn;
                """;

        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("cn");
                cityName.add(name);
            }

        } catch (SQLException e) {
            System.err.println("Error executing from getCityName method" + e.getMessage());
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
            System.err.println("Error executing from getStateName method" + e.getMessage());
        }
        return stateName;
    }

    public ArrayList<Global> getPeriodData(String level, String[] regions, String startYear, String yearLength,
            String comparedRange, String term) {
        ArrayList<Global> periodData = new ArrayList<>();
        Population Population = new Population();
        String query = "";

        if ("Country".equals(level)) {
            query = """
                    SELECT c.countryName AS Name, t1.year AS StartYear, t2.year AS EndYear,
                           t1.averageTemperature AS StartTemp, t2.averageTemperature AS EndTemp,
                           (t2.averageTemperature - t1.averageTemperature) AS Change
                           """ + (("population".equals(term) || "both".equals(term)) ? ", p.value AS Population" : "")
                    + """
                            FROM TempOfCountry t1
                            JOIN TempOfCountry t2 ON t1.countryID = t2.countryID
                            JOIN Country c ON c.countryID = t1.countryID
                            """
                    + (("population".equals(term) || "both".equals(term))
                            ? "LEFT JOIN Population p ON p.countryID = c.countryID AND p.year = t1.year"
                            : "")
                    + """
                                WHERE c.countryName = ?
                                  AND t1.year = ?
                                  AND t2.year = t1.year + ?
                            """;
        } else if ("State".equals(level)) {
            query = """
                        SELECT s.stateName AS Name, t1.year AS StartYear, t2.year AS EndYear,
                               t1.averageTemperature AS StartTemp, t2.averageTemperature AS EndTemp,
                               (t2.averageTemperature - t1.averageTemperature) AS Change
                        FROM TempOfState t1
                        JOIN TempOfState t2 ON t1.stateID = t2.stateID
                        JOIN State s ON s.stateID = t1.stateID
                        WHERE s.stateName = ?
                          AND t1.year = ?
                          AND t2.year = t1.year + ?
                    """;
        } else if ("City".equals(level)) {
            query = """
                        SELECT c.cityName AS Name, t1.year AS StartYear, t2.year AS EndYear,
                               t1.averageTemperature AS StartTemp, t2.averageTemperature AS EndTemp,
                               (t2.averageTemperature - t1.averageTemperature) AS Change
                        FROM TempOfCity t1
                        JOIN TempOfCity t2 ON t1.cityID = t2.cityID
                        JOIN City c ON c.cityID = t1.cityID
                        WHERE c.cityName = ?
                          AND t1.year = ?
                          AND t2.year = t1.year + ?
                    """;
        }

        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, regions[0]);
            preparedStatement.setInt(2, Integer.parseInt(startYear));
            preparedStatement.setInt(3, Integer.parseInt(yearLength));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Global global = new Global();
                    global.setName(resultSet.getString("Name"));
                    global.setYear(resultSet.getInt("StartYear"));
                    global.setPeriod(resultSet.getInt("EndYear"));
                    global.setFirstYearTemperature(resultSet.getFloat("StartTemp"));
                    global.setLastYearTemperature(resultSet.getFloat("EndTemp"));
                    global.setChange(resultSet.getFloat("Change"));
                    if ("Country".equals(level) && ("population".equals(term) || "both".equals(term))) {
                        // Global.setPopulation(resultSet.getLong("Population"));
                    }
                    periodData.add(global);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing getPeriodData: " + e.getMessage());
        }
        return periodData;
    }

    public ArrayList<Global> findSimilarPeriods(String level, String[] regions, String startYear, String yearLength,
            String comparedRange, String term, int topN) {
        ArrayList<Global> allPeriods = new ArrayList<>();
        String query = "";

        if ("Country".equals(level)) {
            query = """
                        SELECT c.countryName AS Name, t1.year AS StartYear, t2.year AS EndYear,
                               t1.averageTemperature AS StartTemp, t2.averageTemperature AS EndTemp,
                               (t2.averageTemperature - t1.averageTemperature) AS Change,
                               p.value AS Population
                        FROM TempOfCountry t1
                        JOIN TempOfCountry t2 ON t1.countryID = t2.countryID
                        JOIN Country c ON c.countryID = t1.countryID
                        LEFT JOIN Population p ON p.countryID = c.countryID AND p.year = t1.year
                        WHERE t2.year = t1.year + ?
                          AND t1.year BETWEEN ? AND ?
                    """;
        } else if ("State".equals(level)) {
            query = """
                        SELECT s.stateName AS Name, t1.year AS StartYear, t2.year AS EndYear,
                               t1.averageTemperature AS StartTemp, t2.averageTemperature AS EndTemp,
                               (t2.averageTemperature - t1.averageTemperature) AS Change
                        FROM TempOfState t1
                        JOIN TempOfState t2 ON t1.stateID = t2.stateID
                        JOIN State s ON s.stateID = t1.stateID
                        WHERE t2.year = t1.year + ?
                          AND t1.year BETWEEN ? AND ?
                    """;
        } else if ("City".equals(level)) {
            query = """
                        SELECT c.cityName AS Name, t1.year AS StartYear, t2.year AS EndYear,
                               t1.averageTemperature AS StartTemp, t2.averageTemperature AS EndTemp,
                               (t2.averageTemperature - t1.averageTemperature) AS Change
                        FROM TempOfCity t1
                        JOIN TempOfCity t2 ON t1.cityID = t2.cityID
                        JOIN City c ON c.cityID = t1.cityID
                        WHERE t2.year = t1.year + ?
                          AND t1.year BETWEEN ? AND ?
                    """;
        }

        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int yearLengthInt = Integer.parseInt(yearLength);
            int startYearInt = Integer.parseInt(startYear);
            int comparedRangeInt = Integer.parseInt(comparedRange);

            preparedStatement.setInt(1, yearLengthInt);
            preparedStatement.setInt(2, startYearInt - comparedRangeInt);
            preparedStatement.setInt(3, startYearInt + comparedRangeInt);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Global global = new Global();
                    Population Population = new Population();
                    global.setName(resultSet.getString("Name"));
                    global.setYear(resultSet.getInt("StartYear"));
                    global.setPeriod(resultSet.getInt("EndYear"));
                    global.setFirstYearTemperature(resultSet.getFloat("StartTemp"));
                    global.setLastYearTemperature(resultSet.getFloat("EndTemp"));
                    global.setChange(resultSet.getFloat("Change"));
                    if ("Country".equals(level) && ("population".equals(term) || "both".equals(term))) {
                        Population.setPopulation(resultSet.getLong("Population"));
                    }
                    allPeriods.add(global);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing findSimilarPeriods: " + e.getMessage());
        }

        // sort periods by similarity
        Global selectedPeriod = getPeriodData(level, regions, startYear, yearLength, comparedRange, term).get(0);
        allPeriods.sort((p1, p2) -> compareByTerm(p1, p2, selectedPeriod, term));

        // remove the selected period if it's in the list
        allPeriods
                .removeIf(p -> p.getName().equals(selectedPeriod.getName()) && p.getYear() == selectedPeriod.getYear());

        // return top N similar periods
        return new ArrayList<>(allPeriods.subList(0, Math.min(topN, allPeriods.size())));
    }

    private int compareByTerm(Global p1, Global p2, Global selected, String term) {
        double diff1, diff2;
        if ("averageTemperature".equals(term)) {
            diff1 = Math.abs(p1.getFirstYearTemperature() - selected.getFirstYearTemperature());
            diff2 = Math.abs(p2.getFirstYearTemperature() - selected.getFirstYearTemperature());
        } else if ("population".equals(term)) {
            diff1 = Math.abs(p1.getPopulation() - selected.getPopulation());
            diff2 = Math.abs(p2.getPopulation() - selected.getPopulation());
        } else { // both
            double tempDiff1 = Math.abs(p1.getFirstYearTemperature() - selected.getFirstYearTemperature());
            double popDiff1 = Math.abs(p1.getPopulation() - selected.getPopulation());
            double tempDiff2 = Math.abs(p2.getFirstYearTemperature() - selected.getFirstYearTemperature());
            double popDiff2 = Math.abs(p2.getPopulation() - selected.getPopulation());
            diff1 = (tempDiff1 + popDiff1) / 2;
            diff2 = (tempDiff2 + popDiff2) / 2;
        }
        return Double.compare(diff1, diff2);
    }

    public State getStateAvgTemp(String state, int startYear, int endYear) {
        State states = new State();
        String query = """
                    WITH TemperatureData AS (
                    SELECT averagetemperature, year
                    FROM TempOfState
                    WHERE year BETWEEN ? AND ?
                    AND AverageTemperature IS NOT NULL
                    AND StateID = (SELECT StateID FROM State WHERE stateName = ?)
                )
                SELECT AVG(averagetemperature) AS AverageTemperature,
                    MIN(year) AS StartYear,
                    MAX(year) AS EndYear
                FROM TemperatureData;

                         """;
        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, startYear);
            statement.setInt(2, endYear);
            statement.setString(3, state);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                states.setEndYear(result.getInt("StartYear"));
                states.setStartYear((result.getInt("EndYear")));
                states.setAverageTemp(result.getFloat("AverageTemperature"));
                states.setName(state);
            }
        } catch (SQLException e) {
            System.err.println("Error found: getStateAvgTemp() " + e.getMessage());
        }
        return states;
    }

    public City getCityAvgTemp(String cityname, int startYear, int endYear) {
        City city = new City();
        String query = """

                    WITH TemperatureData AS (
                    SELECT averagetemperature, year
                    FROM TempOfCity
                    WHERE year BETWEEN ? AND ?
                    AND AverageTemperature IS NOT NULL
                    AND CityID = (SELECT CityID FROM city WHERE cityName = ?)
                )
                SELECT AVG(averagetemperature) AS AverageTemperature,
                    MIN(year) AS StartYear,
                    MAX(year) AS EndYear
                FROM TemperatureData;

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
            System.err.println("Error found: getCountryAvgTemp() " + e.getMessage());
        }
        return city;

    }

}
