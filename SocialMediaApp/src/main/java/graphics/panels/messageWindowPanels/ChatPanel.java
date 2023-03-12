package graphics.panels.messageWindowPanels;

import graphics.DatabaseConnection;
import people.Message;
import people.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChatPanel extends JPanel
{
    private User currentUser;
    private String username2;

    private JPanel chatPanel;
    private JPanel writeMessagePanel;
    private JTextArea messageArea;
    private JButton sendButton;


    public ChatPanel(User currentUser, String username2)
    {
        this.currentUser = currentUser;
        this.username2 = username2;
        int current_id = DatabaseConnection.getIdByUsername(currentUser.getUsername());
        int user2_id = DatabaseConnection.getIdByUsername(username2);


        ArrayList<Message> allMessages = DatabaseConnection.getMessagesFromConversationBetweenUsers(current_id,user2_id);
        //ArrayList<ArrayList<Message>> seperatedMessages = DatabaseConnection.getMessagesFromAndToUsers(allMessages,currentUser);


        //ArrayList<Message> sentMessages = seperatedMessages.get(1);
       // ArrayList<Message> receivedMessages = seperatedMessages.get(0);


        setLayout(new BorderLayout());


        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel,BoxLayout.Y_AXIS));

        if(allMessages != null)
        {
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
            JScrollPane scrollPane = new JScrollPane(chatPanel);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            add(scrollPane,BorderLayout.CENTER);
        }
        else{
            JLabel lab = new JLabel("No messages");
            chatPanel.add(lab,BorderLayout.CENTER);
            repaint();
            revalidate();
        }


        repaint();
        revalidate();




        writeMessagePanel = new JPanel();
        writeMessagePanel.setLayout(new FlowLayout());

        messageArea = new JTextArea();
        sendButton = new JButton("Send");


        writeMessagePanel.add(messageArea);
        writeMessagePanel.add(sendButton);

        add(writeMessagePanel,BorderLayout.SOUTH);
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
