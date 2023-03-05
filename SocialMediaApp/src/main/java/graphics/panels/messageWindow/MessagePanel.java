package graphics.panels.messageWindow;

import graphics.DatabaseConnection;
import people.Message;
import people.User;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Panel ktory przechowuje sinleMessagePanele jak w messangerze
 *
 */
public class MessagePanel extends JPanel  {



    private User user;
    private String friendUsername;


    public MessagePanel(User user, String friendUsername)
    {
        this.user = user;
        this.friendUsername = friendUsername;

        int userID = DatabaseConnection.getIdByUsername(user.getUsername());
        int friendId = DatabaseConnection.getIdByUsername(friendUsername);
        //ArrayList<Message> messages = DatabaseConnection.getMessagesFromConversationBetweenUsers(userID,friendId);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(800,100));


        ArrayList<Integer> ids = DatabaseConnection.getSenderFromRecipientID(DatabaseConnection.getIdByUsername(user.getUsername()));

        for(int id: ids)
        {
            ArrayList<Message> tmp = DatabaseConnection.getMessagesFromUserByID(id);
            tmp = DatabaseConnection.sortMessages(tmp);
            Message m = tmp.get(tmp.size()-1);
            SingleMessagePanel smp = new SingleMessagePanel(m);
            smp.setMaximumSize(new Dimension(800,100));
            add(smp);

        }
        revalidate();
        repaint();















    }



    private Message getLastMessage(ArrayList<Message> m1)
    {
        ArrayList<ArrayList<Message>> messages = DatabaseConnection.getMessagesFromAndToUsers(m1,user);
        ArrayList<Message> received = messages.get(1);

        return received.get(received.size()-1);
    }






}
