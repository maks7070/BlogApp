package graphics.windows;

import graphics.DatabaseConnection;
import graphics.panels.addFriendWindowPanels.AddFriendsPanel;
import graphics.panels.mainUtilityPanels.NavigationPanel;
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
    private JFrame lastFrame;


    private JButton back;
    private JButton createPostButton;
    private JButton myProfileButton;
    private JButton logoutButton;
    private JButton writeMessage;
    private JButton acceptFriendsButton;




    public AddFriendsWindow(User user, JFrame lastFrame)
    {
        super("Friends panel");
        this.user = user;

        this.lastFrame = lastFrame;
        username = new JTextField(20);
        searchPanel = new JPanel(new GridBagLayout());
        navigationPanel = new NavigationPanel(this,user,lastFrame);

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





        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);








        setLayout(new BorderLayout());
        add(searchPanel,BorderLayout.NORTH);
        add(scrollPane,BorderLayout.CENTER);
        add(navigationPanel,BorderLayout.SOUTH);

        setVisible(true);


    }



    public JFrame getLastFrame() {
        return lastFrame;
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
