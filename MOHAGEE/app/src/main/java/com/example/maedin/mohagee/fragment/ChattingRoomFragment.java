package com.example.maedin.mohagee.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MainActivity;
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
        view = inflater.inflate(R.layout.fragment_chatroom, container, false);
        txtInfo = view.findViewById(R.id.txt_chattingroom_info);
        btn_make_rooms=(Button)view.findViewById(R.id.btn_make_room);
        btn_make_rooms.setOnClickListener(this);

        listView = (ListView) view.findViewById(R.id.chatting_list);


        serverThread.getFgHandler().sendEmptyMessage(17);
        Log.d("loglog", "12");
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_make_room:
                Intent intent=new Intent(getActivity(),add_chatroom.class);
                startActivityForResult(intent,0);


                break;


        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(((MainActivity)getActivity()), "성공!", Toast.LENGTH_SHORT).show();
        Log.d("loglog", "132");
        serverThread.getFgHandler().sendEmptyMessage(17);

    }



    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            RoomList = new ArrayList<>();
            try {
                System.out.println(msg.toString());

                JSONObject object = new JSONObject(msg.obj.toString());
                JSONArray array = new JSONArray(object.getString("rooms"));
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject jObject = array.getJSONObject(i);  // JSONObject 추출
                        RoomItem temp = new RoomItem();
                        temp.setRoom_name(jObject.getString("room_name"));
                        String users = jObject.getString("users");
                        String[] data = users.split("&");
                        for(int k = 0 ; k<data.length ; k++)
                        {
                            temp.setWith_who(data[k]);
                        }
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
                e.printStackTrace();
            }
        }


    };

}



