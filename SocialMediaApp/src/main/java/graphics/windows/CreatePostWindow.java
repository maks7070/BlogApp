package graphics.windows;

import graphics.DatabaseConnection;
import graphics.panels.mainUtilityPanels.NavigationPanel;
import people.Post;
import people.User;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreatePostWindow extends JFrame
{

    private User user;
    private JPanel createPostPanel;
    private JPanel navigationPanel;
    private JTextArea postArea;
    private JButton postButton;
    private JTextField titleField;
    private JFrame lastFrame;



    //TODO work on design and bariers
    public CreatePostWindow(User user,JFrame lastFrame)
    {
        this.user = user;
        this.lastFrame = lastFrame;

        createPostPanel = new JPanel();
        navigationPanel = new NavigationPanel(this,user,lastFrame);

        createPostPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel postLabel = new JLabel("Create your dream post");
        createPostPanel.add(postLabel,gbc);
        gbc.gridy++;
        titleField = new JTextField(20);
        createPostPanel.add(titleField,gbc);
        gbc.gridy++;
        postArea = new JTextArea(8,20);
        createPostPanel.add(postArea,gbc);
        postButton = new JButton("Post");
        gbc.gridy++;
        createPostPanel.add(postButton,gbc);



        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = postArea.getText();
                String title = titleField.getText();
                titleField.setText("");
                postArea.setText("");

                int userId = DatabaseConnection.getIdByUsername(user.getUsername());
                DatabaseConnection.addPostToDatabase(userId,text,title);
                ArrayList<Post> posts = DatabaseConnection.getAllPosts();
                System.out.println(posts);





                //Add post to database function
            }
        });







        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);




        setLayout(new BorderLayout());
        add(createPostPanel,BorderLayout.NORTH);
        add(navigationPanel,BorderLayout.SOUTH);

        setVisible(true);













    }


}
