package app.JDBC;


public class JDBCConnection {

    public static final String DATABASE = "jdbc:sqlite:database/Movies.db";

    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }


}