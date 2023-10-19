import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main{
    public static void main(String[] args) {
            try{
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PostgreSQL", "root", "root");
                System.out.println("Connected to database");
                Statement statement = connection.createStatement();
            // Table account
                ResultSet accountResult = statement.executeQuery("SELECT * FROM account");
                System.out.println("Records from table account: ");
                while (accountResult.next()) {
                    System.out.printf("%-30.30s  %-30.30s%n", accountResult.getInt("user_id") , accountResult.getString("username"),  accountResult.getString("password"));
                }
           // Table sesion 
                ResultSet sessionResult = statement.executeQuery("SELECT * FROM session");
                System.out.println("Records from table session:  ");
                while (sessionResult.next()) {
                    System.out.printf("%-30.30s  %-30.30s%n", sessionResult.getInt("session_id"), sessionResult.getInt("user_id") , sessionResult.getTimestamp("session_start"),  sessionResult.getTimestamp("session_end"));
                }

            // Insert into account 
            // ResultSet insert = statement.executeQuery("INSERT INTO account");

        }catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }
}

