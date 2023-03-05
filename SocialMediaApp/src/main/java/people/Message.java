package people;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Message
{
    private int sender;
    private int receiver;
    private String message;
    private LocalDateTime sentAt;


    public Message(int sender, int receiver, String message, LocalDateTime sentAt){
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.sentAt = sentAt;
    }

    public int getSender() {
        return sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}
