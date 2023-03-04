package graphics.windows;

import graphics.DatabaseConnection;
import graphics.panels.AcceptFriendPanel;
import people.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AcceptFriendProposalsWindow extends JFrame
{
    private User user;
    private JPanel requestsPanel;
    private JPanel navigationPanel;
    private JButton back;
    private JButton createPostButton;
    private JButton addFriendsButton;
    private JButton profileButton;
    private JButton messageButton;
    private JButton logoutButton;




    public AcceptFriendProposalsWindow(User user)
    {
        super("Friend accept");
        this.user = user;

        requestsPanel = new JPanel();
        navigationPanel = new JPanel(new FlowLayout());

        back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UserWindow uw = new UserWindow(user);
            }
        });

        createPostButton = new JButton("Create post");
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO add action listener for create post button
            }
        });

        addFriendsButton = new JButton("Add friends");
        addFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AddFriendsWindow afw = new AddFriendsWindow(user);

            }
        });

        profileButton = new JButton("Profile");
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO implement action listener
            }
        });

        messageButton = new JButton("Message");
        messageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO imeplement action listener
            }
        });

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO implement action listener
            }
        });


        requestsPanel.setLayout(new BoxLayout(requestsPanel,BoxLayout.Y_AXIS));

        navigationPanel.add(back);
        navigationPanel.add(createPostButton);
        navigationPanel.add(addFriendsButton);
        navigationPanel.add(profileButton);
        navigationPanel.add(messageButton);
        navigationPanel.add(logoutButton);



        ArrayList<String> usernames = getFriendRequestsByReceiver(DatabaseConnection.getIdByUsername(user.getUsername()));
        for(String s: usernames)
        {
            AcceptFriendPanel afp = new AcceptFriendPanel(s,user);
            afp.setMaximumSize(new Dimension(800,100));
            afp.setSize(new Dimension(800,100));
            requestsPanel.add(afp);
            repaint();
            revalidate();
        }

        JScrollPane scrollPane = new JScrollPane(requestsPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
        add(navigationPanel,BorderLayout.SOUTH);
        System.out.println(getProposalCount());




        setSize(new Dimension(800,600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public int getProposalCount()
    {
        int count = 0;
        try(Connection conn = DatabaseConnection.getConnection())
        {

            PreparedStatement statement = conn.prepareStatement("select count(*) from proposals");
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                count = rs.getInt(1);
            }


        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return count;
    }


    public ArrayList<String> getFriendRequestsByReceiver(int receiverId)
    {
        ArrayList<String> senderUsernames = new ArrayList<>();
        ArrayList<Integer> senderIds = new ArrayList<>();
        try(Connection conn = DatabaseConnection.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement("select sender_id from proposals where receiver_id = ?");
            statement.setInt(1,receiverId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                senderIds.add(rs.getInt("sender_id"));
            }

            for(Integer i : senderIds){
                senderUsernames.add(DatabaseConnection.getUsernameById(i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return senderUsernames;
    }





}
