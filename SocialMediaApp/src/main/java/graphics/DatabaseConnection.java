package graphics;

import people.Message;
import people.Post;
import people.User;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public static void createConversation(int user1, int user2)
    {
        try(Connection conn = getConnection())
        {
            PreparedStatement statement = conn.prepareStatement("insert into conversations (user1_id, user2_id) values (?,?)");
            statement.setInt(1,user1);
            statement.setInt(2,user2);
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static int checkConversationExists(int userId1, int userId2) {
        try (Connection conn = getConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT id FROM conversations WHERE (user1_id = ? AND user2_id = ?) OR (user1_id = ? AND user2_id = ?)");
            ps.setInt(1, userId1);
            ps.setInt(2, userId2);
            ps.setInt(3, userId2);
            ps.setInt(4, userId1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int conversationId = rs.getInt("id");
                rs.close();
                ps.close();
                conn.close();
                return conversationId;
            } else {
                rs.close();
                ps.close();
                conn.close();
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static ArrayList<Message> getMessagesFromConversationBetweenUsers(int user1, int user2)
    {
        int conversationID = checkConversationExists(user1,user2);
        if(conversationID == -1)
        {
            return null;
        }

        ArrayList<Message> messages = new ArrayList<>();
        try(Connection conn = getConnection())
        {
            PreparedStatement statement = conn.prepareStatement("select * from messages where conversation_id = ?");
            statement.setInt(1,conversationID);
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                int messageId = rs.getInt("id");
                int senderId = rs.getInt("sender_id");
                int receiverId = rs.getInt("receiver_id");
                String content = rs.getString("content");
                LocalDateTime timestamp = rs.getTimestamp("created_at").toLocalDateTime();
                Message m = new Message(senderId,receiverId,content,timestamp);
                messages.add(m);
            }



        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        messages = sortMessages(messages);
        return messages;
    }

    public static ArrayList<Message> sortMessages(ArrayList<Message> messages)
    {
        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o1.getSentAt().compareTo(o2.getSentAt());
            }
        });
        return messages;
    }



    public static ArrayList<ArrayList<Message>> getMessagesFromAndToUsers(ArrayList<Message> messages, User user)
    {
        ArrayList<ArrayList<Message>> bothMessages = new ArrayList<>();

        ArrayList<Message> userSend = new ArrayList<>();
        ArrayList<Message> userReceiver = new ArrayList<>();


        int userId = DatabaseConnection.getIdByUsername(user.getUsername());

        for(Message m: messages)
        {
            if(m.getReceiver() == userId)
            {
                userReceiver.add(m);
            }
            else{
                userSend.add(m);
            }
        }
        bothMessages.add(userSend);
        bothMessages.add(userReceiver);


        return bothMessages;







    }



    public static ArrayList<Integer> getSenderFromRecipientID(int id)
    {
        ArrayList<Integer> ids = new ArrayList<>();
        try(Connection conn = DatabaseConnection.getConnection()){
            int secondID = -1;
            PreparedStatement statement = conn.prepareStatement("select user1_id from conversations where user2_id = ?");
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int user2 = rs.getInt("user1_id");
                ids.add(user2);
            }




        }catch (Exception e){
            e.printStackTrace();
        }
        return ids;
    }



    public static ArrayList<Message> getMessagesFromUserByID(int userId)
    {
        ArrayList<Message> messages = new ArrayList<>();
        try(Connection conn = getConnection())
        {
            PreparedStatement statement = conn.prepareStatement("select * from messages where sender_id=?");
            statement.setInt(1,userId);
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                int receivedId = rs.getInt("receiver_id");
                String content = rs.getString("content");
                Timestamp timestamp = rs.getTimestamp("created_at");

                Message m = new Message(userId,receivedId, content,timestamp.toLocalDateTime() );
                messages.add(m);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return messages;
    }





    public static void addPostToDatabase(int userID, String text, String title)
    {
        try(Connection conn = getConnection())
        {
            PreparedStatement statement = conn.prepareStatement("insert into posts (title,content,author_id) values (?,?,?);");
            statement.setString(1,title);
            statement.setString(2,text);
            statement.setInt(3,userID);
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static ArrayList<Post> getAllPosts()
    {
        ArrayList<Post> posts = new ArrayList<>();
        try(Connection conn = getConnection()){
            PreparedStatement statement = conn.prepareStatement("select * from posts");

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String text = rs.getString("content");
                String title = rs.getString("title");
                String userName = getUsernameById(rs.getInt("author_id"));
                Post p = new Post(userName,title,text);
                posts.add(p);

            }



        }catch (Exception e){
            e.printStackTrace();
        }
        return posts;




    }











}
