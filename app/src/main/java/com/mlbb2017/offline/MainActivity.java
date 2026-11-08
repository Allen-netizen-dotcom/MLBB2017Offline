package com.mlbb2017.offline;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;
import android.view.ViewGroup;

public class MainActivity extends Activity {

    String selectedHero = "Warrior";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showMainMenu();
    }

    void showMainMenu() {
        LinearLayout layout = createLayout();

        TextView title = new TextView(this);
        title.setText("MLBB 2017 OFFLINE");
        title.setTextSize(28);
        title.setTextColor(Color.WHITE);

        Button start = new Button(this);
        start.setText("START GAME");
        start.setOnClickListener(v -> showHeroSelection());

        layout.addView(title);
        layout.addView(start);

        setContentView(layout);
    }

    void showHeroSelection() {
        LinearLayout layout = createLayout();

        TextView title = new TextView(this);
        title.setText("SELECT YOUR HERO");
        title.setTextSize(25);
        title.setTextColor(Color.WHITE);

        layout.addView(title);

        String[] heroes = {
            "Warrior",
            "Mage",
            "Archer",
            "Assassin",
            "Tank",
            "Support"
        };

        for (String hero : heroes) {
            Button button = new Button(this);
            button.setText(hero);

            button.setOnClickListener(v -> {
                selectedHero = hero;
                Toast.makeText(this,
                    hero + " selected!",
                    Toast.LENGTH_SHORT).show();
            });

            layout.addView(button);
        }

        Button battle = new Button(this);
        battle.setText("START 5V5 BATTLE");
        battle.setOnClickListener(v -> showBattle());

        layout.addView(battle);

        Button back = new Button(this);
        back.setText("BACK");
        back.setOnClickListener(v -> showMainMenu());

        layout.addView(back);

        setContentView(layout);
    }

    void showBattle() {
        LinearLayout layout = createLayout();

        TextView battle = new TextView(this);
        battle.setText(
            "OFFLINE BATTLE\n\n" +
            "Your Hero: " + selectedHero +
            "\n\n5v5 AI Battle Prototype"
        );

        battle.setTextColor(Color.WHITE);
        battle.setTextSize(24);

        layout.addView(battle);
        setContentView(layout);
    }

    LinearLayout createLayout() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.rgb(20, 29, 48));
        return layout;
    }
                                  }
