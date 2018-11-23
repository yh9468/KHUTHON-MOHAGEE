package com.example.maedin.mohagee.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.application.App;
import com.example.maedin.mohagee.item.User;
import com.example.maedin.mohagee.thread.ServerThread;

import java.util.ArrayList;

public class add_chatroom extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> friend_ids;
    Button input_friend, btn_join_ok, btn_join_cancle;
    private ServerThread serverThread=ServerThread.getInstance();
    EditText m_id, m_name;
    User curuser;
    String myresult;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_chat_room);

        input_friend = (Button)findViewById(R.id.input_friend);
        btn_join_cancle = (Button)findViewById(R.id.btn_join_cancle);
        btn_join_ok = (Button)findViewById(R.id.btn_join_ok);

        curuser = ((App)getApplication()).getUser();

        input_friend.setOnClickListener(this);
        btn_join_ok.setOnClickListener(this);
        btn_join_cancle.setOnClickListener(this);

        m_id = (EditText) findViewById(R.id.m_id);
        m_name = (EditText) findViewById(R.id.m_name);
        friend_ids = new ArrayList<>();
        friend_ids.add(curuser.getId());
    }

    @Override
    public void onClick(View v)
    {
        Button b = findViewById(v.getId());
        switch (v.getId())
        {
            case R.id.input_friend:
                String string = m_id.getText().toString();
                friend_ids.add(string);
                Toast.makeText(getApplicationContext(), "친구를 추가하셧습니다!", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_join_cancle:
                finish();
                break;
            case R.id.btn_join_ok:
                String tempname = m_name.getText().toString();
                serverThread.setAddroom(friend_ids, tempname, myresult);
                serverThread.getFgHandler().sendEmptyMessage(18);
                break;
        }
    }

}
