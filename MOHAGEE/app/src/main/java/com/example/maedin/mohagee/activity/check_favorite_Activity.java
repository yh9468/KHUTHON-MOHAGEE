package com.example.maedin.mohagee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.maedin.mohagee.R;

import java.util.ArrayList;
import java.util.Vector;

public class check_favorite_Activity extends AppCompatActivity implements View.OnClickListener {
    Button exhibition, theater, cinema;
    Button korea_resturant, china_resturant, japan_resturant, snack_resturant, west_resturant;
    Button billiard, bowling, pc_room, room_escape, singing_room;
    Button park,shopping;
    Button cafe;
    Button Result;

    ArrayList<String> arrstr;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_favorite);
        arrstr = new ArrayList<>();
        korea_resturant = (Button)findViewById(R.id.koreafood_button);
        korea_resturant.setOnClickListener(this);

        Result = (Button)findViewById(R.id.result);
        Result.setOnClickListener(this);

        china_resturant = (Button)findViewById(R.id.chinafood_button);
        china_resturant.setOnClickListener(this);

        japan_resturant = (Button)findViewById(R.id.japanfood_button);
        japan_resturant.setOnClickListener(this);

        snack_resturant = (Button)findViewById(R.id.snack_button);
        snack_resturant.setOnClickListener(this);

        west_resturant = (Button)findViewById(R.id.westfood_button);
        west_resturant.setOnClickListener(this);

        cafe = (Button)findViewById(R.id.cafe);
        cafe.setOnClickListener(this);

        billiard = (Button)findViewById(R.id.billiard_button);
        billiard.setOnClickListener(this);

        bowling = (Button)findViewById(R.id.bowling_button);
        bowling.setOnClickListener(this);

        pc_room = (Button)findViewById(R.id.pc_room_button);
        pc_room.setOnClickListener(this);

        room_escape = (Button)findViewById(R.id.escape_room_button);
        room_escape.setOnClickListener(this);

        singing_room = (Button)findViewById(R.id.singing_room);
        singing_room.setOnClickListener(this);

        park = (Button)findViewById(R.id.park_button);
        park.setOnClickListener(this);

        shopping = (Button)findViewById(R.id.shopping_button);
        shopping.setOnClickListener(this);

        exhibition = (Button)findViewById(R.id.Exhibition_button);
        exhibition.setOnClickListener(this);

        theater = (Button)findViewById(R.id.theater_button);
        theater.setOnClickListener(this);

        cinema = (Button)findViewById(R.id.movie_button);
        cinema.setOnClickListener(this);


    }

    @Override
    public void onClick(View view)
    {
        Button b;
        b = view.findViewById(view.getId());
        if(b.getText() != "#저장하기") {
            if (b.isSelected()) {
                b.setSelected(false);
                for (int i = 0; i < arrstr.size(); i++) {
                    if (arrstr.get(i).equals((String) b.getText())) {
                        arrstr.remove(i);
                        break;
                    }
                }
            } else {
                b.setSelected(true);
                arrstr.add((String) b.getText());
            }
        }
        else
        {
            Intent data = new Intent();
            data.putExtra("List", arrstr);
            setResult(8941, data);
            this.finish();
        }
    }


}
