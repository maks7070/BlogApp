package graphics.panels.messageWindowPanels;

import people.Message;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

public class SingleChatPanel extends JPanel
{
    private Message message;
    private String layout;


    /**
     * Class for single chat cloud its either on the right or on the left
     * @param message
     * @param layout
     */
    public SingleChatPanel(Message message, String layout)
    {
        this.message = message;
        this.layout = layout;


        if(layout.equals("receiver"))
        {
            setLayout(new FlowLayout(FlowLayout.RIGHT, 10,10));
        }
        else{
            setLayout(new FlowLayout(FlowLayout.LEFT, 10,10));
        }

        setPreferredSize(new Dimension(800,100));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setMaximumSize(new Dimension(800,100));



    }


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        FontMetrics fm = g.getFontMetrics();
        //TODO fix one line of the message
        String messagePreview = message.getMessage();
        g.drawString(message.getMessage(),10,40);

        DateFormat df = new SimpleDateFormat("yyy-MM-dd");
        String dateString = df.format(Date.from(message.getSentAt().atZone(ZoneId.systemDefault()).toInstant()));
        g.drawString(dateString,getWidth() - fm.stringWidth(dateString) - 10,getHeight()-10);


    }
}
