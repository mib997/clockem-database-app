/**
@author: Malik Bellamy
This file contains the code to connect to the SQL database. 
**/

import java.sql.*;

public class DatabaseConnector {

    static final String DB_URL = "jdbc:mysql://localhost:3306/id_management_lookup";

    private final Connection con;
    private final PreparedStatement insertStmt;

    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            throw new RuntimeException("Driver not found", e);
        }
    }

    /*Constructor that connects to the SQL database */
    public DatabaseConnector(String userName, String password) throws SQLException {
        this.con = DriverManager.getConnection(DB_URL, userName, password);

        String query = "INSERT INTO person_id_info(FirstName, LastName, Date_of_Birth, " +
                "Country, Age) VALUES (?, ?, ?, ?, ?)";

        this.insertStmt = con.prepareStatement(query);
    }

    public void insertData(PersonInfo p) throws SQLException{

            insertStmt.setString(1, p.getFirstName());
            insertStmt.setString(2, p.getLastName());
            insertStmt.setString(3, p.getDOB());
            insertStmt.setString(4, p.getCountry());
            insertStmt.setString(5, p.calculateAge());

            insertStmt.executeUpdate();

    }

    /* Static method that allows the user to retrieve the data from the database.
    * STILL IN PROGRESS */

    public static void retrieveData(String username,String password) throws SQLException {
        final String dbUrl = "jdbc:mysql://localhost:3306/id_management_lookup";
        final String query = "SELECT * FROM person_id_info";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver not found", e);
        }

        try (Connection con = DriverManager.getConnection(dbUrl, username, password);
        Statement stmt = con.createStatement()){

            ResultSet dataSet = stmt.executeQuery(query);

            while (dataSet.next()){

                String firstName = dataSet.getString("FirstName");
                String lastName = dataSet.getString("LastName");
                String dateOfBirth = dataSet.getString("Date_of_Birth");
                String country = dataSet.getString("Country");
                String age = dataSet.getString("Age");

                System.out.println("FirstName :"+firstName+", LastName: "+lastName+", " +
                        "Date of Birth: "+dateOfBirth+", Country: "+country+", Age: "+age);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
