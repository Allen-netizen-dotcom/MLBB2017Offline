package com.mlbb2017.offline;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.rgb(20, 29, 48));

        TextView title = new TextView(this);
        title.setText("MOBILE LEGENDS");
        title.setTextSize(30);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);

        TextView subtitle = new TextView(this);
        subtitle.setText("2017 OFFLINE EDITION");
        subtitle.setTextColor(Color.rgb(255, 190, 70));
        subtitle.setTextSize(18);

        Button start = new Button(this);
        start.setText("START GAME");

        start.setOnClickListener(v ->
            Toast.makeText(this,
                "Welcome to Offline Edition!",
                Toast.LENGTH_SHORT).show()
        );

        Button heroes = new Button(this);
        heroes.setText("HERO SELECTION");

        layout.addView(title);
        layout.addView(subtitle);
        layout.addView(start);
        layout.addView(heroes);

        setContentView(layout);
    }
}
