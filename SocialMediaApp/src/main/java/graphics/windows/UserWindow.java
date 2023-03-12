package graphics.windows;

import graphics.panels.mainUtilityPanels.NavigationPanel;
import graphics.panels.userWindowPanels.PostPanel;
import people.Post;
import people.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UserWindow extends JFrame
{
    
    private User user;
    private JLabel usernameLabel;
    private JPanel navigationPanel;
    private JPanel feedsPanel;
    private ArrayList<JFrame> pastFrames;


    public UserWindow(User user)
    {
        super("Feed window");
        this.user = user;
        pastFrames = new ArrayList<>();
        pastFrames.add(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);

        usernameLabel = new JLabel("Welcome, " + user.getUsername() + "!");
        usernameLabel.setFont(new Font("Helvetica", Font.BOLD,20));
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);


        navigationPanel = new NavigationPanel(this,user,null);

        feedsPanel = new JPanel();
        feedsPanel.setLayout(new BoxLayout(feedsPanel,BoxLayout.Y_AXIS));

        for(Post p: user.getPosts())
        {
            PostPanel pp = new PostPanel(p);
            pp.setPreferredSize(new Dimension(800,100));
            pp.setMaximumSize(new Dimension(800,100));
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

    private ArrayList<JFrame> getPastFrames()
    {
        return pastFrames;
    }



}
