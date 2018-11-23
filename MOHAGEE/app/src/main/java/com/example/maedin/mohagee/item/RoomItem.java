package com.example.maedin.mohagee.item;

public class RoomItem {
    String room_name;
    String with_who;
    public RoomItem()
    {
        room_name="";
        with_who="nobody";
    }
    public RoomItem(String name, String who)
    {
        room_name=name;
        with_who=who;
    }
    public void setData(String name,String member)
    {
        room_name=name;
        with_who=member;
    }
    public String getRoom_name()
    {
        return room_name;
    }
    public String getWith_who()
    {
        return with_who;
    }
    public void setRoom_name(String name)
    {
        room_name=name;
    }
    public void setWith_who(String who)
    {
        with_who=who;
    }
}
