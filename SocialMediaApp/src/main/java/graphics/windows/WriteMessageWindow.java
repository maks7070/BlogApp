package graphics.windows;

import people.User;

import javax.swing.*;

public class WriteMessageWindow extends JFrame
{
    private User user;
    private JPanel createMessagePanel;
    private JTextField senderUsername;
    private JTextArea messageContent;




    public WriteMessageWindow(User user)
    {
        super("Write message");
        this.user = user;

        createMessagePanel = new JPanel();
        senderUsername = new JTextField(20);
        messageContent = new JTextArea();

    }
}
