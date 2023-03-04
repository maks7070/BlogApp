package graphics.panels;

import people.Post;

import javax.swing.*;
import java.awt.*;

public class PostPanel extends JPanel
{
    private Post post;
    private JLabel authorLabel;
    private JLabel titleLabel;
    private JTextArea contentLabel;



    public PostPanel(Post post)
    {
        super();
        this.post = post;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(800,100));
        //this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        authorLabel = new JLabel(post.getUsername());
        titleLabel = new JLabel(post.getTitle());
        contentLabel = new JTextArea(post.getContent());
        contentLabel.setEditable(false);
        contentLabel.setMaximumSize(new Dimension(800,50));


        authorLabel.setFont(new Font("Arial",Font.BOLD,18));
        titleLabel.setFont(new Font("Arial",Font.BOLD,16));
        contentLabel.setFont(new Font("Arial",Font.BOLD,14));

        authorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.add(authorLabel);
        this.add(titleLabel);
        this.add(contentLabel);





    }
}
