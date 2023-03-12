package graphics.panels.messageWindowPanels;

import graphics.DatabaseConnection;
import people.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;


/**
 * Panel ktory ma imitowac wyswietlenie elementu mowiacego ze taka konwersacja juz istnieje
 *
 */
public class SingleMessagePanel extends JPanel
{
    private Message message;


    public SingleMessagePanel(Message message)
    {
        this.message = message;
        setLayout(new FlowLayout(FlowLayout.LEFT, 10,10));
        setPreferredSize(new Dimension(800,100));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });




    }


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.setFont(new Font("Arial",Font.BOLD,16));
        int senderID = message.getSender();
        g.drawString(DatabaseConnection.getUsernameById(senderID),10,20);

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
