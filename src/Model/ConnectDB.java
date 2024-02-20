package Model;
        
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    static Connection conn = null;
    public static Connection connect() throws ClassNotFoundException, SQLException {
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb","root","");
        System.out.println("Connection to Database has been established.");
        
        return conn;
    }

}

