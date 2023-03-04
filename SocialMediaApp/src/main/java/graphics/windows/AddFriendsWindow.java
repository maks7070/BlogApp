package graphics.windows;

import graphics.DatabaseConnection;
import graphics.panels.AddFriendsPanel;
import people.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
    private JButton acceptFriendsButton;




    public AddFriendsWindow(User user)
    {
        super("Friends panel");
        this.user = user;
        username = new JTextField(20);
        searchPanel = new JPanel(new GridBagLayout());
        navigationPanel = new JPanel(new FlowLayout());

        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel,BoxLayout.Y_AXIS));



        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultPanel.removeAll();
                ArrayList<User> users = getSearchResult(username.getText());
                for(User u : users)
                {
                    AddFriendsPanel afp = new AddFriendsPanel(user,u);
                    afp.setMaximumSize(new Dimension(800,100));
                    resultPanel.add(afp);
                }
                revalidate();
                repaint();
                users.clear();

            }
        });



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
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UserWindow uw = new UserWindow(user);
            }
        });
        createPostButton = new JButton("Create new post");
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO implement Action listener
            }
        });
        myProfileButton = new JButton("Profile");
        myProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO implement action listener
            }
        });
        writeMessage = new JButton("Message");
        writeMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO implement action listener
            }
        });
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO implement action listener
            }
        });
        acceptFriendsButton = new JButton("Accept friends");
        acceptFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AcceptFriendProposalsWindow afpw = new AcceptFriendProposalsWindow(user);
            }
        });


        navigationPanel.add(back);
        navigationPanel.add(createPostButton);
        navigationPanel.add(acceptFriendsButton);
        navigationPanel.add(myProfileButton);
        navigationPanel.add(writeMessage);
        navigationPanel.add(logoutButton);


        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);





//        getContentPane().setLayout(new BorderLayout());
//        getContentPane().add(searchPanel,BorderLayout.NORTH);
//        getContentPane().add(scrollPane,BorderLayout.CENTER);
//        getContentPane().add(navigationPanel,BorderLayout.SOUTH);


        setLayout(new BorderLayout());
        add(searchPanel,BorderLayout.NORTH);
        add(scrollPane,BorderLayout.CENTER);
        add(navigationPanel,BorderLayout.SOUTH);

        setVisible(true);


    }

    //TODO add modifications to not include your account when searching
    private ArrayList<User> getSearchResult(String username)
    {
        ArrayList<User> ret = new ArrayList<>();
        try(Connection conn = DatabaseConnection.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement("select * from users");
            ResultSet rs = statement.executeQuery();

            while(rs.next())
            {
                String tmp = rs.getString("username");
                if(tmp.contains(username))
                {
                    String pass = rs.getString("password");
                    ret.add(new User(tmp,pass));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

}
