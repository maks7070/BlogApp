package people;

import graphics.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private ArrayList<Post> posts;
    private ArrayList<User> friends;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        posts = new ArrayList<>();
        friends = new ArrayList<>();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //TODO add function which loads users post when the user logs in
    private void loadPosts() {

    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    //TODO add function to load the friend list
    private void loadFriends() {

    }




    private void refreshPostsArray()
    {

    }
    private void refreshFriendsArray()
    {

    }
    private void refreshRequestArray()
    {
        
    }
}
