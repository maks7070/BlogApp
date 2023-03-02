package graphics.windows;

import people.User;

import javax.swing.*;
import java.awt.*;

public class AddFriendsWindow extends JFrame
{
    private User user;
    private JTextField username;
    private JPanel searchPanel;
    private JPanel resultPanel;
    //private JPanel mainPanel;
    private JButton searchButton;
    private JPanel navigationPanel;


    private JButton back;
    private JButton createPostButton;
    private JButton myProfileButton;
    private JButton logoutButton;
    private JButton writeMessage;




    public AddFriendsWindow(User user)
    {
        super("Friends panel");
        this.user = user;
        username = new JTextField(20);
        searchPanel = new JPanel(new GridBagLayout());
        resultPanel = new JPanel();
        navigationPanel = new JPanel(new FlowLayout());

        searchButton = new JButton("Search");



        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Create searchPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(new JLabel("Search for the username"),gbc);
        gbc.gridy++;
        searchPanel.add(username,gbc);
        gbc.gridy++;
        searchPanel.add(searchButton,gbc);


        back = new JButton("Back");
        createPostButton = new JButton("Create new post");
        myProfileButton = new JButton("Profile");
        writeMessage = new JButton("Message");
        logoutButton = new JButton("Logout");


        navigationPanel.add(back);
        navigationPanel.add(createPostButton);
        navigationPanel.add(myProfileButton);
        navigationPanel.add(writeMessage);
        navigationPanel.add(logoutButton);




        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(searchPanel,BorderLayout.NORTH);
        getContentPane().add(resultPanel,BorderLayout.CENTER);
        getContentPane().add(navigationPanel,BorderLayout.SOUTH);

        setVisible(true);


    }

}
