package com.example.maedin.mohagee.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MainActivity;
import com.example.maedin.mohagee.item.PlaceItem;
import com.example.maedin.mohagee.thread.ServerThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentSearchName extends Fragment {

    View view;
    EditText editSearch;
    Button btnSearch;

    private ServerThread serverThread = ServerThread.getInstance();
    private String myResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_name, container, false);


        editSearch = (EditText) view.findViewById(R.id.edit_search);
        btnSearch = (Button) view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 16;
                String loc_name = editSearch.getText().toString();
                serverThread.setSearchName(loc_name,myResult);
                serverThread.getFgHandler().sendMessage(msg);
            }
        });

        serverThread.setFgHandler(mHandler);

        return view;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 16)
            {
                ArrayList<PlaceItem> list = new ArrayList<>();

                Log.d("Result", msg.obj.toString());
                try {

                    JSONObject object = new JSONObject(msg.obj.toString());
                    JSONArray array = new JSONArray(object.getString("locations"));
                    for(int i=0; i < array.length(); i++){
                        try {
                            JSONObject jObject = array.getJSONObject(i);  // JSONObject 추출
                            PlaceItem temp = new PlaceItem();
                            temp.setId(jObject.getString("loc_id"));
                            temp.setStar(jObject.getString("star"));
                            temp.setAddress( jObject.getString("loc_addr"));
                            temp.setName(jObject.getString("loc_name"));
                            temp.setLat(jObject.getString("latitude"));
                            temp.setLng(jObject.getString("longitude"));
                            list.add(temp);

                        }catch (JSONException e)
                        {

                        }
                    }
                }
                catch (JSONException e)
                {

                }

                CategoryListFragment f = new CategoryListFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list); // list 넘기기
                f.setArguments(bundle);

                ((MainActivity)getActivity()).changeFragment(f);
            }

        }
    };
}