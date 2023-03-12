package graphics.panels.mainUtilityPanels;

import graphics.windows.*;
import people.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class NavigationPanel extends JPanel
{
    private ArrayList<JButton> buttons;
    private HashMap<Class<? extends JFrame>,JButton> buttonMap;
    private JFrame currentFrame;
    private JFrame lastFrame;
    private User user;
    private ArrayList<JFrame> pastFrames;




    private JButton back;
    private JButton createNewPostButton;
    private JButton addFriendsButton;
    private JButton acceptFriendsButton;
    private JButton profileButton;
    private JButton messagerButton;
    private JButton logoutButton;





    public NavigationPanel(JFrame currentFrame, User user,JFrame lastFrame)
    {
        this.currentFrame = currentFrame;
        this.user = user;

        this.lastFrame = lastFrame;


        setLayout(new FlowLayout());

        buttons = new ArrayList<>();
        buttonMap = new HashMap<>();
        back = new JButton("Back");
        createNewPostButton = new JButton("Create new post");
        addFriendsButton = new JButton("Add friends");
        acceptFriendsButton = new JButton("Accept friends");
        profileButton = new JButton("Profile");
        messagerButton = new JButton("Messanger");
        logoutButton = new JButton("Logout");


        buttonMap.put(CreatePostWindow.class,createNewPostButton);
        buttonMap.put(AddFriendsWindow.class,addFriendsButton);
        buttonMap.put(AcceptFriendProposalsWindow.class,acceptFriendsButton);
        buttonMap.put(ProfileWindow.class,profileButton);
        buttonMap.put(WriteMessageWindow.class,messagerButton);







        buttons.add(back);
        buttons.add(createNewPostButton);
        buttons.add(addFriendsButton);
        buttons.add(acceptFriendsButton);
        buttons.add(profileButton);
        buttons.add(messagerButton);
        buttons.add(logoutButton);


        //TODO add mechanisms to create a new JPanel without all buttons

        Class<? extends JFrame> frameClass = currentFrame.getClass();

        if(frameClass.equals(UserWindow.class))
        {
            for(JButton b: buttons)
            {
                if(!b.getText().equals("Back"))
                {
                    add(b);
                }
            }
        }
        else{
            add(back);
            for(Class<? extends JFrame> frame : buttonMap.keySet())
            {
                if(!frame.equals(frameClass))
                {
                    add(buttonMap.get(frame));
                }
            }
            add(logoutButton);
        }

        //TODO add implementation
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame.setVisible(false);
                getPastObjects(currentFrame).setVisible(true);
            }
        });


        createNewPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame.setVisible(false);
                CreatePostWindow cpw = new CreatePostWindow(user,currentFrame);

            }
        });


        addFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame.setVisible(false);
                AddFriendsWindow afw = new AddFriendsWindow(user,currentFrame);

            }
        });

        acceptFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame.setVisible(false);
                AcceptFriendProposalsWindow afpw = new AcceptFriendProposalsWindow(user,currentFrame);


            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame.setVisible(false);
                ProfileWindow pw = new ProfileWindow(user);


            }
        });


        messagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame.setVisible(false);
                WriteMessageWindow wmw = new WriteMessageWindow(user);

            }
        });

//TODO implement better logout mechanism
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });













    }

    private JFrame getPastObjects(JFrame currentFrame)
    {
        Class<? extends JFrame> frameClass = currentFrame.getClass();
        if(frameClass.equals(AcceptFriendProposalsWindow.class))
        {
            return ((AcceptFriendProposalsWindow) currentFrame).getLastFrame();
        }


        else if(frameClass.equals(AddFriendsWindow.class))
        {
            return ((AddFriendsWindow) currentFrame).getLastFrame();
        }
        else{
            return null;
        }



    }








}
