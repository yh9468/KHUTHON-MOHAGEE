package com.example.maedin.mohagee.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MessageListActivity;
import com.example.maedin.mohagee.item.RoomItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChattingRoomAdapter extends BaseAdapter{
    private ArrayList<RoomItem> RoomList = null;
    private int listCnt = 0;

    LayoutInflater inflater = null;

    //생성자 : 데이터 셋팅
    public ChattingRoomAdapter(ArrayList<RoomItem> RoomList) {
        if(RoomList==null)
            RoomList=new ArrayList<>();
        else
            this.RoomList = RoomList;

        listCnt = RoomList.size();

    }

    //화면 갱신 전 호출, 아이템 갯수 결정
    @Override
    public int getCount() {
        return listCnt;
    }


    //리스트 뷰에 데이터를 넣어줌 - 화면 표시, position: 몇번째아이템
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null)
        {
            if (inflater == null)
                inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_chatroom, parent, false);
        }
        //아이템 클릭시
        convertView.setOnClickListener(new View.OnClickListener()
        {
           @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MessageListActivity.class));


            }
        });
        //위젯과 연결
        TextView room_name = (TextView) convertView.findViewById(R.id.list_chat_name);
        TextView room_member=(TextView) convertView.findViewById(R.id.list_chat_member);


        RoomItem temp = RoomList.get(position);

        room_name.setText(temp.getRoom_name());

        String tempstr = "";
        ArrayList<String> strings = temp.getWith_who();
        for(int i = 0 ; i<strings.size() ; i++)
        {
            tempstr = tempstr + strings.get(i) + ", ";
        }
        room_member.setText(tempstr);

        convertView.setTag(""+position);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return RoomList.get(position);
    }



}
