package com.example.maedin.mohagee.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.Select_Location_Activity;
import com.example.maedin.mohagee.item.PlaceItem;

import org.json.JSONObject;

import java.util.ArrayList;

public class CustomPathListAdapter extends BaseAdapter {

    //넣어줄 데이터 리스트
    private ArrayList<JSONObject> customList = null;
    private int listCnt = 0;

    LayoutInflater inflater = null;

    //생성자 : 데이터 셋팅
    public CustomPathListAdapter(ArrayList<JSONObject> customList) {
        this.customList = customList;
        listCnt = customList.size();
    }

    //화면 갱신 전 호출, 아이템 갯수 결정
    @Override
    public int getCount() {
        return listCnt;
    }


    //리스트 뷰에 데이터를 넣어줌 - 화면 표시, position: 몇번째아이템
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //postion: List View의 위치
        //첫번째면 position = 0;
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null)
        {
            if (inflater == null)
                inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_custom_path, parent, false);
        }

        //위젯과 연결
        TextView num = (TextView) convertView.findViewById(R.id.txt_custom_path_num);
        TextView list = (TextView) convertView.findViewById(R.id.txt_custom_path_list);
        Button map = (Button) convertView.findViewById(R.id.btn_custom_map);

        //아이템 내 각 위젯에 데이터 반영
        num.setText("♥ MY CUSTOM "+Integer.toString(pos+1));

        //PlaceItem temp = placeList.get(position);

        map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

//        convertView.setOnLongClickListener(new View.OnLongClickListener(){
//            @Override
//            public boolean onLongClick(View v) {
//                //삭제 다이얼로그
//                return true;
//            }
//        });

        //아이템 클릭시
//        convertView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//            }
//        });


        convertView.setTag(""+position);

        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return customList.get(position);
    }

}