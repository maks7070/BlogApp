package graphics.panels.acceptWindowPanels;

import graphics.DatabaseConnection;
import people.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcceptFriendPanel extends JPanel
{
    private String username;
    private User currentUser;
    private JLabel usernameLabel;
    private JButton acceptButton;
    private JButton rejectButton;


    public AcceptFriendPanel(String username, User currentUser){
        super();
        this.username = username;
        this.currentUser = currentUser;

        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.setPreferredSize(new Dimension(800,100));
        this.setMaximumSize(new Dimension(800,100));

        usernameLabel = new JLabel(username);
        acceptButton = new JButton("Accept");
        rejectButton = new JButton("Reject");


        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection.acceptFriendRequest(currentUser,username);
                acceptButton.setText("accepted");
                remove(rejectButton);
                revalidate();
                repaint();
            }
        });



        usernameLabel.setFont(new Font("Arial",Font.BOLD,18));
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rejectButton.setAlignmentX(RIGHT_ALIGNMENT);

        add(usernameLabel);
        add(acceptButton);
        add(rejectButton);



    }
}
