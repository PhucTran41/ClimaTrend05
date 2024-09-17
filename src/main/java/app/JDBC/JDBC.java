package app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.classes.Global;

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
    
        return initialYear;
    }

}
