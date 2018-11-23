package com.example.maedin.mohagee.fragment;

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
import android.widget.EditText;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.thread.ServerThread;

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
                
            }
        });

        serverThread.setFgHandler(mHandler);

        return view;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}