package graphics.windows;

import graphics.DatabaseConnection;
import graphics.panels.acceptWindowPanels.AcceptFriendPanel;
import graphics.panels.mainUtilityPanels.NavigationPanel;
import people.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


//TODO fix this mess
public class AcceptFriendProposalsWindow extends JFrame
{
    private User user;

    private JFrame lastFrame;


    private JPanel requestsPanel;
    private JPanel navigationPanel;
    private JButton back;
    private JButton createPostButton;
    private JButton addFriendsButton;
    private JButton profileButton;
    private JButton messageButton;
    private JButton logoutButton;




    public AcceptFriendProposalsWindow(User user, JFrame lastFrame)
    {
        super("Friend accept");
        this.user = user;
        this.lastFrame = lastFrame;

        requestsPanel = new JPanel();
        navigationPanel = new NavigationPanel(this,user,lastFrame);





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



    public JFrame getLastFrame() {
        return lastFrame;
    }
}
