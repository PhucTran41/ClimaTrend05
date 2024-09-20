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

    public JDBCforTimelineTracker() {
        System.out.println("Created JDBC Connection Object");
    }
        public ArrayList<String> getCountryName(){
            ArrayList<String> countryName = new ArrayList<String>();
            
            try (Connection connection = DriverManager.getConnection(DATABASE);
                Statement statement = connection.createStatement();) {
            statement.setQueryTimeout(15);
                
                String query = """
                    Select DISTINCT CountryName cn 
                    FROM Country;
                """;
                statement.executeQuery(query);
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    String name = resultSet.getString("countryName");
                    countryName.add(name);
                }
    
            } catch (SQLException e) {
                System.err.println("Error executing from getCityNameFromCountry method " + e.getMessage());
            }
            return countryName;
        }

}

