package graphics.windows;

import graphics.panels.PostPanel;
import people.Post;
import people.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserWindow extends JFrame
{
    //TODO implement message writting
    private User user;
    private JLabel usernameLabel;
    private JPanel navigationPanel;
    private JPanel feedsPanel;
    private JButton addFriends;
    private JButton createPostButton;
    private JButton myProfileButton;
    private JButton logoutButton;
    private JButton writeMessage;
    private JButton acceptFriends;


    public UserWindow(User user)
    {
        super("Feed window");
        this.user = user;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);

        usernameLabel = new JLabel("Welcome, " + user.getUsername() + "!");
        usernameLabel.setFont(new Font("Helvetica", Font.BOLD,20));
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);


        navigationPanel = new JPanel(new FlowLayout());
        createPostButton = new JButton("Create new post");
        navigationPanel.add(createPostButton);

        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO implement createPostActionListener
            }
        });

        addFriends = new JButton("Add friends");
        navigationPanel.add(addFriends);

        addFriends.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AddFriendsWindow afw = new AddFriendsWindow(user);
            }
        });

        acceptFriends = new JButton("Accept friends");
        navigationPanel.add(acceptFriends);
        acceptFriends.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AcceptFriendProposalsWindow afpw = new AcceptFriendProposalsWindow(user);

            }
        });





        myProfileButton = new JButton("Profile");
        navigationPanel.add(myProfileButton);

        myProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO implement profile button action listener
            }
        });

        writeMessage = new JButton("Message");
        navigationPanel.add(writeMessage);

        writeMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO implement write message action listenter
            }
        });

        logoutButton = new JButton("Logout");
        navigationPanel.add(logoutButton);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO implement logout Action listener
            }
        });



        feedsPanel = new JPanel();
        feedsPanel.setLayout(new BoxLayout(feedsPanel,BoxLayout.Y_AXIS));

        for(Post p: user.getPosts())
        {
            PostPanel pp = new PostPanel(p);
            pp.setPreferredSize(new Dimension(800,100));
            feedsPanel.add(pp);
        }
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(usernameLabel,BorderLayout.NORTH);
        getContentPane().add(navigationPanel,BorderLayout.SOUTH);
        JScrollPane scrollPane = new JScrollPane(feedsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane);

        //pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }



}
