package com.example.maedin.mohagee.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.maedin.mohagee.R;

public class MypageFragmet extends Fragment  implements View.OnClickListener {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mypage, container, false);

        ImageButton cemerabutton = view.findViewById(R.id.camera_button);
        cemerabutton.setImageResource(R.drawable.camera);
        cemerabutton.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.camera_button:

                break;


        }

    }

}
