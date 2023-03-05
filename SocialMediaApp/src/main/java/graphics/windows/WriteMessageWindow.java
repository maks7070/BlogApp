package graphics.windows;

import graphics.panels.messageWindow.MessagePanel;
import people.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WriteMessageWindow extends JFrame
{
    private User user;
    private JPanel createMessagePanel;
    private JTextField senderUsername;
    private JButton createMessage;


    private JPanel navigationPanel;
    private JButton back;
    private JButton createPostButton;
    private JButton addFriendsButton;
    private JButton acceptRequestButton;
    private JButton profileButton;
    private JButton logoutButton;


    private JPanel mainPanel;


    /**
     * Message window 3 panels one panel to create new message e.g. you type in
     * the username and a special window with
     * @param user
     */
    public WriteMessageWindow(User user)
    {
        super("Write message");
        this.user = user;

        createMessagePanel = new JPanel(new GridBagLayout());
        senderUsername = new JTextField(20);
        createMessage = new JButton("Create message");
        mainPanel = new JPanel();

        createMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO add action listener to change mainPanel to message panel for selected user

            }
        });


        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));

        //TODO add past conversation
        MessagePanel mp = new MessagePanel(user,"max");
        JScrollPane scrollPane = new JScrollPane(mp);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane);










        setSize(new Dimension(800,600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        createMessagePanel.add(senderUsername,gbc);
        gbc.gridy++;
        createMessagePanel.add(createMessage,gbc);

        setLayout(new BorderLayout());
        add(createMessagePanel,BorderLayout.NORTH);
        add(mainPanel,BorderLayout.CENTER);





        setVisible(true);



    }
}
