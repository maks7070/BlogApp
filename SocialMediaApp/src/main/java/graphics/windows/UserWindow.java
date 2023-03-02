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

        addFriends = new JButton("Add friends");
        navigationPanel.add(addFriends);

        addFriends.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AddFriendsWindow afw = new AddFriendsWindow(user);
            }
        });




        myProfileButton = new JButton("Profile");
        navigationPanel.add(myProfileButton);

        writeMessage = new JButton("Message");
        navigationPanel.add(writeMessage);

        logoutButton = new JButton("Logout");
        navigationPanel.add(logoutButton);



        feedsPanel = new JPanel(new GridLayout(0,1));
        displayPosts(user.getPosts());





        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(usernameLabel,BorderLayout.NORTH);
        getContentPane().add(navigationPanel,BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(feedsPanel), BorderLayout.CENTER);

        //pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void displayPosts(ArrayList<Post> posts)
    {
        feedsPanel.removeAll();
        for(Post p:posts)
        {
            feedsPanel.add(new PostPanel(p));
        }
        feedsPanel.revalidate();
        feedsPanel.repaint();
    }
}
