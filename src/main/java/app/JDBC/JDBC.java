package app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import app.classes.Global;
import app.classes.Population;
import app.page.GlobalTracker;

public class JDBC {

    public static final String DATABASE = "jdbc:sqlite:database/Movies.db";

    public JDBC() {
        System.out.println("Created JDBC Connection Object");
    }

    // GET THE FIRST YEAR FOR GLOBAL TEMPERATURE DATA

    public Global getFirstYearTemp() {
        Global worldtemperature = new Global();

        try (Connection connection = DriverManager.getConnection(DATABASE);
                Statement statement = connection.createStatement();) {

            statement.setQueryTimeout(15);

            String query = """
                    SELECT *
                    FROM TempOfGlobal t
                    WHERE t.year IN
                        (SELECT MIN(year)
                        FROM TempOfGlobal)
                    """;
            ResultSet resultSet = statement.executeQuery(query);

            worldtemperature.setYear(resultSet.getInt("year"));
            worldtemperature.setAverageTemp(resultSet.getFloat("averageTemp"));
            worldtemperature.setMinimumTemp(resultSet.getFloat("minimumTemp"));
            worldtemperature.setMaximumTemp(resultSet.getFloat("maximumTemp"));

        } catch (SQLException e) {
            System.err.println("Error found :getFirstYearTemp() " + e.getMessage());
        }

        return worldtemperature;
    }

    // GET THE LAST YEAR FOR GLOBAL TEMPERATURE DATA

    public Global getLastYearTemp() {

        int year = 0;

        Global worldtemperature = new Global();

        try (Connection connection = DriverManager.getConnection(DATABASE);
                Statement statement = connection.createStatement();) {

            statement.setQueryTimeout(15);

            String query = """

                    SELECT *
                    FROM TempOfGlobal t
                    WHERE t.year IN
                        (SELECT MAX(year))
                        FROM TempOfGlobal
                    """;

            ResultSet result = statement.executeQuery(query);

            worldtemperature.setYear(result.getInt("year"));
            worldtemperature.setAverageTemp(result.getFloat("averageTemp"));
            worldtemperature.setMinimumTemp(result.getFloat("minimumTemp"));
            worldtemperature.setMaximumTemp(result.getFloat("maximumTemp"));

        } catch (SQLException e) {

            System.err.println("Error found :getFirstYearTemp() " + e.getMessage());
        }

        return worldtemperature;
    }

    // GET THE FIRST YEAR FOR GLOBAL POPULATION DATA

    public int getPopulationFirstYear(){
        
        int initialYear = 0;

          try (Connection connection = DriverManager.getConnection(DATABASE);
                Statement statement = connection.createStatement();) {

                    } catch (SQLException e) {
            System.err.println("Error found :getPopulationFirstYear() " + e.getMessage());

        return initialYear;
    }

    // GET THE LAST YEAR FOR GLOBAL POPULATION DATA

    public int getPopulationLastYear(){
        
        int initialYear = 0;


        return initialYear;
    }

    public Population getPopulation() {

        Population population = new Population();
        String query = """

                """;
        try (Connection connection = DriverManager.getConnection(DATABASE);
                Statement statement = connection.createStatement();) {

            statement.setQueryTimeout(15);

            ResultSet result = statement.executeQuery(query);
            if(result.next()){
                population.setPopulation(result.getLong("population"));
                population.setYear(result.getInt("year"));
                population.setCountryID(result.getString("countryID"));
            }

        } catch (SQLException e) {
            System.err.println("Error found :getPopulation() " + e.getMessage());
        }

        return population;
    }

}
