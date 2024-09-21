package app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;

public class JDBCforCityTracker {

    public static final String DATABASE = "jdbc:sqlite:OfficialDatabase.db";

    public JDBCforCityTracker() {
        System.out.println("Created JDBC Connection Object");
    }

    // Lấy danh sách tên quốc gia
    public ArrayList<String> getCountryNames() {
        ArrayList<String> countryNames = new ArrayList<>();

        String query = "SELECT countryName FROM Country";

        try (Connection connection = DriverManager.getConnection(DATABASE);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("countryName");
                countryNames.add(name);
            }

        } catch (SQLException e) {
            System.err.println("Error executing getCountryNames: " + e.getMessage());
        }
        return countryNames;
    }

    // Lấy năm đầu tiên từ bảng Population của một quốc gia
    public int getFirstYear(int countryID) {
        String query = "SELECT MIN(year) AS firstYear FROM Population WHERE countryID = ?";
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
        String query = "SELECT MAX(year) AS lastYear FROM Population WHERE countryID = ?";
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
                SELECT countryID FROM Country WHERE countryName = ?
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
}