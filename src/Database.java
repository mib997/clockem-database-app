import java.sql.*;
import java.util.Scanner;

public class Database {

    static final String DB_URL = "jdbc:mysql://localhost:3306/idsystem";
    static final String USER = "root";
    static final String PASS = "Immavegeta1997@";

    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        Scanner scanstr = new Scanner(System.in);

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement state = con.createStatement();
            System.out.println("Connection established successfully!");

            String query = "INSERT INTO gfgArticles(articleID, Author, Title, Likes)" +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            System.out.println("What is the article ID?: ");
            int articleID = scan.nextInt();

            System.out.println("Enter the author's name: ");
            String authorName = scanstr.nextLine();

            System.out.println("Enter the title: ");
            String title = scanstr.nextLine();

            System.out.println("Enter the likes: ");
            int likes = scan.nextInt();

            String id = Integer.toString(articleID);
            String like = Integer.toString(likes);


            ps.setString(1, id);
            ps.setString(2, authorName);
            ps.setString(3, title);
            ps.setString(4, like);

            ps.addBatch();
            ps.executeBatch();



        } catch (Exception e) {
            System.out.println(e.toString());;
        }

    }
}
