package graphics.panels.messageWindow;

import graphics.DatabaseConnection;
import people.Message;
import people.User;

import javax.swing.*;
import java.util.ArrayList;

public class ChatPanel extends JPanel
{
    private User currentUser;
    private String username2;

    private JPanel chatPanel;
    private JPanel writeMessagePanel;


    public ChatPanel(User currentUser, String username2)
    {
        this.currentUser = currentUser;
        this.username2 = username2;
        int current_id = DatabaseConnection.getIdByUsername(currentUser.getUsername());
        int user2_id = DatabaseConnection.getIdByUsername(username2);


        ArrayList<Message> allMessages = DatabaseConnection.getMessagesFromConversationBetweenUsers(current_id,user2_id);
        ArrayList<ArrayList<Message>> seperatedMessages = DatabaseConnection.getMessagesFromAndToUsers(allMessages,currentUser);


        ArrayList<Message> sentMessages = seperatedMessages.get(1);
        ArrayList<Message> receivedMessages = seperatedMessages.get(0);

        chatPanel = new JPanel();
        for(Message m : allMessages)
        {
            if(isReceived(m))
            {
                SingleChatPanel scp = new SingleChatPanel(m,"received");
                chatPanel.add(scp);
            }
            else{
                SingleChatPanel scp = new SingleChatPanel(m,"send");
                chatPanel.add(scp);
            }
        }

        repaint();
        revalidate();

        JScrollPane scrollPane = new JScrollPane(chatPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);











    }




    public boolean isReceived(Message m)
    {
        if(m.getReceiver() == DatabaseConnection.getIdByUsername(currentUser.getUsername()))
        {
            return true;
        }
        else{
            return false;
        }
    }
}
