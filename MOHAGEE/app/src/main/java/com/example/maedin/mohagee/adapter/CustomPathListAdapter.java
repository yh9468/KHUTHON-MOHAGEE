package com.example.maedin.mohagee.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.SearchActivity;
import com.example.maedin.mohagee.thread.ServerThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomPathListAdapter extends BaseAdapter {

    //넣어줄 데이터 리스트
    private ArrayList<JSONObject> customList = null;
    private int listCnt = 0;
    Context context;

    LayoutInflater inflater = null;

    private ServerThread serverThread = ServerThread.getInstance();
    private String myResult;

    //생성자 : 데이터 셋팅
    public CustomPathListAdapter(ArrayList<JSONObject> customList, Context context) {
        this.customList = customList;
        listCnt = customList.size();
        this.context = context;

        serverThread.setFgHandler(mHandler);
    }

    //화면 갱신 전 호출, 아이템 갯수 결정
    @Override
    public int getCount() {
        return listCnt;
    }


    //리스트 뷰에 데이터를 넣어줌 - 화면 표시, position: 몇번째아이템
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

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
        TextView name = (TextView) convertView.findViewById(R.id.txt_custom_path_name);
        Button map = (Button) convertView.findViewById(R.id.btn_custom_map);

        //아이템 내 각 위젯에 데이터 반영
        num.setText("♥ MY CUSTOM "+Integer.toString(pos+1));
        String cName = "";

        try {
            cName = customList.get(position).getString("custom_name");
        }catch (JSONException e)
        {
        }

        name.setText(cName);

        //PlaceItem temp = placeList.get(position);

        map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("Custom","클릭!");
                String ids = "";
                try {
                    ids = customList.get(position).getString("loc_ids");
                }catch (JSONException e)
                {
                }
                Message msg = new Message();
                msg.what = 14;
                serverThread.setDetailCustomPath(ids,myResult);
                serverThread.getFgHandler().sendMessage(msg);
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                //삭제 다이얼로그
                Log.d("Custom", "로오옹클릭!");
                dialogDelete(position);
                return true;
            }
        });

//        //아이템 클릭시
//        convertView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//
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


    private void dialogDelete(final int position)
    {
        final LinearLayout layout = (LinearLayout) View.inflate(context,R.layout.dialog_custom_path_delete, null);
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        final AlertDialog ad = dlg.create();
        ad.setTitle("커스텀 경로 삭제");
        ad.setCancelable(false);
        ad.setView(layout);
        Button btnDelete = layout.findViewById(R.id.btn_custom_delete);
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //삭제 요청

                Log.d("Custom","클릭!");
                String id = "";
                try {
                    id = customList.get(position).getString("custom_id");
                }catch (JSONException e)
                {
                }
                Message msg = new Message();
                msg.what = 15;
                serverThread.setDeleteCustomPath(id,myResult);
                serverThread.getFgHandler().sendMessage(msg);

//                customList.remove(customList.get(position));
                ad.dismiss();
            }
        });
        Button btnCancel = layout.findViewById(R.id.btn_custom_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });
        ad.show();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 14)
            {
                Log.d("Custom", msg.obj.toString());

                try {

                    JSONObject object = new JSONObject(msg.obj.toString());
                    JSONArray array = new JSONArray(object.getString("locations"));

                    JSONObject temp = new JSONObject();
                    temp.put("Lat","37.4980744");
                    temp.put("Lng", "127.0252673");
                    array.put(temp);
                    JSONObject json = new JSONObject();
                    json.put("locations", array);
                    Intent intent = new Intent(context, SearchActivity.class);
                    intent.putExtra("locations", json.toString());
                    context.startActivity(intent);

//                    for(int i=0; i < array.length(); i++){
//                        try {
//                            JSONObject jObject = array.getJSONObject(i);  // JSONObject 추출
//                            PlaceItem temp = new PlaceItem();
//                            temp.setId(jObject.getString("loc_id"));
//                            temp.setName(jObject.getString("loc_name"));
//                            temp.setCategory(jObject.getString("small_ctg"));
//                            temp.setTime(jObject.getString("loc_time"));
//                            temp.setBig_cat(jObject.getString("big_ctg"));
//                            temp.setAddress(jObject.getString("loc_addr"));
//                            temp.setLat(jObject.getString("latitude"));
//                            temp.setLng(jObject.getString("longitude"));
//                            temp.setStar(jObject.getString("star"));
//                            if(jObject.getString("theme1")=="null")
//                            {
//                                temp.setTheme("#"+"저렴"+" #"+"분위기"+" #"+"감성");
//
//                            }
//                            else
//                            {
//                                temp.setTheme("#"+jObject.getString("theme1")+" #"+jObject.getString("theme2")+" #"+jObject.getString("theme3"));
//
//                            }
//                            placeList.add(temp);
//
//                        }catch (JSONException e)
//                        {
//
//                        }
//                    }
                }
                catch (JSONException e)
                {

                }
            }
            if (msg.what == 15)
            {
                Log.d("Custom", msg.obj.toString());
                Toast.makeText(context, "경로가 삭제되었습니다. ",Toast.LENGTH_SHORT).show();
            }
        }
    };
}