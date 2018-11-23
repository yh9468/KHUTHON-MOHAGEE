package com.example.maedin.mohagee.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.add_chatroom;
import com.example.maedin.mohagee.adapter.ChattingRoomAdapter;
import com.example.maedin.mohagee.item.RoomItem;
import com.example.maedin.mohagee.thread.ServerThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChattingRoomFragment extends Fragment implements View.OnClickListener{
    ArrayList<RoomItem> RoomList = null;
    private ServerThread serverThread = ServerThread.getInstance();
    private ListView listView;
    private ChattingRoomAdapter adapter;
    private TextView txtInfo;
    private Button btn_make_rooms;

    private String myResult = "";
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        serverThread.setFgHandler(mHandler);
        listView = (ListView) view.findViewById(R.id.chatting_list);

        view = inflater.inflate(R.layout.fragment_chatroom, container, false);
        txtInfo = view.findViewById(R.id.txt_chattingroom_info);
        btn_make_rooms=(Button)view.findViewById(R.id.btn_make_room);
        btn_make_rooms.setOnClickListener(this);



        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_make_room:
                Intent intent=new Intent(getContext(),add_chatroom.class);
                getContext().startActivity(intent);
                serverThread.getFgHandler().sendEmptyMessage(17);

                adapter = new ChattingRoomAdapter(RoomList);
                listView.setAdapter(adapter);

                break;


        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            RoomList = new ArrayList<>();
            try {

                JSONObject object = new JSONObject(msg.obj.toString());
                JSONArray array = new JSONArray(object.getString("chat_room"));
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject jObject = array.getJSONObject(i);  // JSONObject 추출
                        RoomItem temp = new RoomItem();
                        temp.setRoom_name(jObject.getString("room_name"));
                        temp.setWith_who(jObject.getString("member"));

                        RoomList.add(temp);

                    } catch (JSONException e) {

                    }
                }
                adapter = new ChattingRoomAdapter(RoomList);
                listView.setAdapter(adapter);
                if (RoomList.size() == 0)
                {
                    txtInfo.bringToFront();
                    txtInfo.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {

            }
        }


    };

}



