package graphics.panels.addFriendWindowPanels;

import graphics.DatabaseConnection;
import people.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddFriendsPanel extends JPanel
{
    private User user;
    private User friend;
    private JLabel usernameLabel;
    private JButton addFriendButton;


    public AddFriendsPanel(User user, User friend)
    {
        super();
        this.user = user;
        this.friend = friend;

        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.setPreferredSize(new Dimension(800,100));
        this.setMaximumSize(new Dimension(800,100));

        usernameLabel = new JLabel(friend.getUsername());
        addFriendButton = new JButton("Add friend");
        addFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sendFriendRequest(user,friend);



                addFriendButton.setText("Added");
            }
        });

        usernameLabel.setFont(new Font("Arial",Font.BOLD,18));
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        addFriendButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(usernameLabel);
        this.add(addFriendButton);




    }

    //TODO add function to dont inculde friends in search

    private void sendFriendRequest(User sender, User receiver)
    {
        try(Connection conn = DatabaseConnection.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement("insert into proposals (sender_id,receiver_id,status) values (?,?,?)");
            statement.setInt(1,getUserId(sender.getUsername()));
            statement.setInt(2,getUserId(receiver.getUsername()));
            statement.setString(3,"pending");
            statement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private int getUserId(String username){
        int userID = -1;
        try (Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement("select id from users where username = ?");
            statement.setString(1,username);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                userID = rs.getInt("id");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return userID;
    }
}
