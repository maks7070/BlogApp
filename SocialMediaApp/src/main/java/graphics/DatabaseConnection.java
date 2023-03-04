package graphics;

import people.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseConnection
{
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/platform";
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


    public static int getIdByUsername(String username)
    {
        int index = -1;
        try(Connection conn = getConnection())
        {
            PreparedStatement statement = conn.prepareStatement("select id from users where username = ?");
            statement.setString(1,username);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                index = rs.getInt("id");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return index;
    }


    public static String getUsernameById(int id)
    {
        String username = null;
        try(Connection conn = getConnection()){
            PreparedStatement statement = conn.prepareStatement("select username from users where id = ?");
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                username = rs.getString("username");

            }



        }catch (Exception e){
            e.printStackTrace();
        }
        return username;
    }


    public static void acceptFriendRequest(User accepter, String username)
    {
        int user1 = getIdByUsername(accepter.getUsername());
        int user2 = getIdByUsername(username);

        try(Connection conn = getConnection())
        {
            PreparedStatement statement1 = conn.prepareStatement("update users set friend_list = array_append(friend_list,?) where id = ?");
            statement1.setInt(1,user1);
            statement1.setInt(2,user2);
            statement1.executeUpdate();

            PreparedStatement statement2 = conn.prepareStatement("update users set friend_list = array_append(friend_list,?) where id = ?");
            statement2.setInt(1,user2);
            statement2.setInt(2,user1);
            statement2.executeUpdate();


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
