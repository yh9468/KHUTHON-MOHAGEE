package com.example.maedin.mohagee.fragment;

import android.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.Select_Location_Activity;
import com.example.maedin.mohagee.adapter.CustomPathListAdapter;
import com.example.maedin.mohagee.application.App;
import com.example.maedin.mohagee.thread.ServerThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomPathFragment extends Fragment {

    View view;

    CustomPathListAdapter adapter;
    ListView listView;

    private ServerThread serverThread = ServerThread.getInstance();
    private String myResult;

    ArrayList<JSONObject> list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_custom_path, container, false);

        listView = (ListView) view.findViewById(R.id.list_custom_path);

        serverThread.setFgHandler(mHandler);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                dialogDelete(position);

                return true;
            }
        });

        return view;
    }


    private void dialogDelete(int position)
    {
        final LinearLayout layout = (LinearLayout) View.inflate(getContext(),R.layout.dialog_custom_path_delete, null);
        AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
        dlg.setTitle("커스텀 경로 삭제");
        dlg.setCancelable(false);
        dlg.setView(layout);
        Button btnDelete = layout.findViewById(R.id.btn_custom_delete);
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //삭제 요청
            }
        });
        Button btnCancel = layout.findViewById(R.id.btn_custom_cancel);
        btnCancel.setOnClickListener(null);
//        dlg.setPositiveButton("취소", null);
        dlg.show();
    }


    private void getCustomPathList()
    {

        try {
            JSONObject temp = new JSONObject();
            temp.put("loc_ids","1,2,3,4");
            temp.put("user_id","sikhye823");
            list.add(temp);
        }
        catch (JSONException e)
        {
        }

//        Message msg = new Message();
//        msg.what = 6;
//        serverThread.setCustomList(((App)getActivity().getApplication()).getUser().getId(),myResult);
//        serverThread.getFgHandler().sendMessage(msg);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

}
