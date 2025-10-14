import java.sql.*;
import java.sql.DriverManager;
import java.util.Scanner;

public class SQLDatabase {
    static final String DB_URL = "jdbc:mysql://localhost:3306/id_management_lookup";
    static final String USER = "root";
    static final String PASS = "@";
    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement statement = null;
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to database......");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Enter user name: ");
            String userName = scanner.nextLine();

            System.out.println("Enter user email: ");
            String userEmail = scanner.nextLine();

            String sql = "INSERT INTO users (name, email) VALUES (?, ?)";

            statement = connection.prepareStatement(sql);

            statement.setString(1, userName);
            statement.setString(2, userEmail);

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows inserted successfully");

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException se2) {

            }
        }
        try {
            if (connection != null) connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }

        if (scanner != null) {
            scanner.close();
        }
    }
}
