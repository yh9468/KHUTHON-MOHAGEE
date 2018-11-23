package com.example.maedin.mohagee.fragment;

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
import android.widget.ListView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.adapter.CustomPathListAdapter;
import com.example.maedin.mohagee.application.App;
import com.example.maedin.mohagee.thread.ServerThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomPathFragment extends Fragment {

    View view;

    ArrayList<JSONObject> list;
    CustomPathListAdapter adapter;
    ListView listView;

    private ServerThread serverThread = ServerThread.getInstance();
    private String myResult;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_custom_path, container, false);

        listView = (ListView) view.findViewById(R.id.list_custom_path);

        serverThread.setFgHandler(mHandler);
        getCustomPathList();


        return view;
    }



    private void getCustomPathList()
    {
        Message msg = new Message();
        msg.what = 13;
        serverThread.setCustomList(((App)getActivity().getApplication()).getUser().getId(),myResult);
        serverThread.getFgHandler().sendMessage(msg);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 13)
            {
                Log.d("Custom",msg.obj.toString());

                try {
                    JSONObject object = new JSONObject(msg.obj.toString());
                    JSONArray array = new JSONArray(object.getString("customRoutes"));

                    list = new ArrayList<>();
                    for(int i=0; i < array.length(); i++) {

                        JSONObject jObject = array.getJSONObject(i);  // JSONObject 추출
                        list.add(jObject);
                    }
                    adapter = new CustomPathListAdapter(list, getContext());
                    listView.setAdapter(adapter);
                }
                catch (JSONException e)
                {
                }
            }
        }
    };

}
