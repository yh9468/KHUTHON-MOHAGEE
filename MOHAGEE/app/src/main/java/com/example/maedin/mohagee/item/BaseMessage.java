package com.example.maedin.mohagee.item;

public class BaseMessage {
    String message;
    User sender;
    long createdAt;
    public String getMessage()
    {
        return message;
    }

    public long getCreatedAt()
    {
        return createdAt;
    }
    public User getSender()
    {
        return sender;
    }
    public void setMessage(String messages)
    {
        message=messages;
    }
    public void setSender(User Sender)
    {
        this.sender=Sender;
    }
    public void setCreatedAt()
    {
        createdAt=System.currentTimeMillis();
    }
}
