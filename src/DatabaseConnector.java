/**
@author: Malik Bellamy
This file contains the code to connect to the SQL database. 
**/



import java.sql.*;

public class DatabaseConnector {

    static final String DB_URL = "jdbc:mysql://localhost:3306/id_management_lookup";
    static final String USER = "root";
    static final String PASS = "";

    /*Constructor that connects to the SQL database and allows the user to enter the user data. */
    public DatabaseConnector(PersonInfo p){

        String query = "INSERT INTO person_id_info(FirstName, LastName, Date_of_Birth, " +
                "Country, Age) VALUES (?, ?, ?, ?, ?)";

        try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Driver not found", e);
            }

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setString(3, p.getDOB());
            ps.setString(4, p.getCountry());
            ps.setString(5, p.calculateAge());

            ps.addBatch();
            ps.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* Static method that allows the user to retrieve the data from the database*/

    public static void retrieveData(String data) throws SQLException {
        final String DB_URL = "jdbc:mysql://localhost:3306/id_management_lookup";
        final String USER = "root";
        final String PASS = "@";
        final String query = "SELECT * FROM person_id_info";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver not found", e);
        }

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = con.createStatement()){

            ResultSet dataSet = stmt.executeQuery(query);






        }

    }
}
