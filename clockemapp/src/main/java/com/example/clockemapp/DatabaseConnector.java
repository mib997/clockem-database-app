package com.example.clockemapp;

import java.sql.*;
import java.util.ArrayList;

/**
 * DatabaseConnector is responsible for interacting with a MySQL database to manage
 * person records. This class provides methods to insert, retrieve, update, and delete
 * person information in the database. It uses JDBC for database connection and operations.
 *
 * The database connection is initialized through the constructor which accepts a username
 * and password for the database credentials. The class is designed to handle a specific
 * table named `person_id_info`.
 *
 * A static block is included to ensure the MySQL JDBC Driver is loaded before any database
 * operation is performed.
 */
public class DatabaseConnector {


    private Connection connection;
    private PreparedStatement insertStmt;

    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            throw new RuntimeException("Driver not found", e);
        }
    }

    /*Constructor that connects to the SQL database */
    public DatabaseConnector(String userName, String password) throws SQLException {

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/id_management_lookup",
                    userName,
                    password
            );

            String insertQuery = "INSERT INTO person_id_info (FirstName, LastName, " +
                    "Date_of_Birth, Country, Age) VALUES (?, ?, ?, ?, ?)";
            insertStmt = connection.prepareStatement(insertQuery);


    }

    public void insertData(PersonInfo p) throws SQLException{

            insertStmt.setString(1, p.getFirstName());
            insertStmt.setString(2, p.getLastName());
            insertStmt.setString(3, p.getDOB());
            insertStmt.setString(4, p.getCountry());
            insertStmt.setString(5, p.getAge());

            insertStmt.executeUpdate();

    }

    public ArrayList<PersonInfo> retrieveData() throws SQLException{
        ArrayList<PersonInfo> people = new ArrayList<>();

        String query = "SELECT * FROM person_id_info";

        try(Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()){
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String dob = rs.getString("Date_of_Birth");
                String country = rs.getString("Country");

                PersonInfo person = new PersonInfo(firstName, lastName, dob, country);
                people.add(person);
            }
        }
        return people;
    }

    public void deletePerson(PersonInfo person) throws SQLException{

        String query = "DELETE FROM person_id_info WHERE FirstName = ? AND LastName = ? " +
                "AND Date_of_Birth = ? AND Country = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            stmt.setString(3, person.getDOB());
            stmt.setString(4, person.getCountry());

            stmt.executeUpdate();

        }
    }

    public void updatePerson(PersonInfo oldPerson, PersonInfo newPerson) throws SQLException{

        String query = "UPDATE person_id_info SET FirstName = ?, LastName = ?, Date_of_Birth = ?, Country = ? " +
                "WHERE FirstName = ? AND LastName = ? AND Date_of_Birth = ? AND Country = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPerson.getFirstName());
            stmt.setString(2, newPerson.getLastName());
            stmt.setString(3, newPerson.getDOB());
            stmt.setString(4, newPerson.getCountry());

            stmt.setString(5, oldPerson.getFirstName());
            stmt.setString(6, oldPerson.getLastName());
            stmt.setString(7, oldPerson.getDOB());
            stmt.setString(8, oldPerson.getCountry());

            stmt.executeUpdate();
        }
    }
}
