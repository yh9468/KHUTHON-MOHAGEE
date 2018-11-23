package com.example.maedin.mohagee.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.adapter.Message_List_Adapter;
import com.example.maedin.mohagee.application.App;
import com.example.maedin.mohagee.item.BaseMessage;
import com.example.maedin.mohagee.item.User;

import java.util.ArrayList;


public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private Message_List_Adapter mMessageAdapter;

    private ArrayList<BaseMessage> messageList = null;
    BaseMessage t1,t2,t3;
    User temp;
    User temp2;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chatting);

        t1=new BaseMessage();
        t2=new BaseMessage();
        t3=new BaseMessage();
        temp=new User();
        temp2=new User();
        messageList=new ArrayList<>();

        t1.setSender(((App)getApplication()).getUser());
        t1.setMessage("so tired, isnt it?");
        t1.setCreatedAt();
        temp.setData("joohee","joohee","vic","female","980619");
        t2.setSender(temp);
        t2.setMessage("i agree");
        t2.setCreatedAt();
        temp2.setData("hiasince","HBI","hbi","male","940903");
        t3.setSender(temp2);
        t3.setCreatedAt();
        t3.setMessage("iffy iffy yeah yeah");
        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        messageList.add(t1);
        messageList.add(t2);
        messageList.add(t3);

        mMessageAdapter = new Message_List_Adapter(this, messageList,((App)getApplication()).getUser());
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
    }
}