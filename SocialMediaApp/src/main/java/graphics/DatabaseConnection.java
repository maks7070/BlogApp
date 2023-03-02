package graphics;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection
{
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/social_media_app";
    private static final String username = "postgres";
    private static final String pass = "admin";


    public static Connection getConnection(){
        Connection conn = null;

        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DB_URL,username,pass);

            if(conn.isValid(5)){
                System.out.println("Connection was established");
            }
            else{
                System.out.println("Connection flailed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
