package com.mlbb2017.offline;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

public class BattleView extends View {

    Paint p = new Paint(3);
    float px = 400, py = 400;
    float targetX = 400, targetY = 400;
    int playerHP = 100;
    long lastAttack = 0;

    ArrayList<Bot> bots = new ArrayList<>();

// MINION SYSTEM
ArrayList<Minion> minions = new ArrayList<>();

int blueTowerHP = 500;
int redTowerHP = 500;
int blueBaseHP = 1000;
int redBaseHP = 1000;

long lastSpawn = 0;
boolean gameOver = false;

class Minion {
    float x, y;
    boolean enemy;
    int hp = 100;
    long lastHit = 0;

    Minion(float x, float y, boolean enemy) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
    }
}

    class Bot {
        float x, y;
        int hp = 100;
        boolean enemy;

        Bot(float x, float y, boolean enemy) {
            this.x = x;
            this.y = y;
            this.enemy = enemy;
        }
    }

    public BattleView(Context context) {
        super(context);

        // Four allies
        for (int i = 0; i < 4; i++) {
            bots.add(new Bot(300 + i * 80, 250, false));
        }

        // Five enemies
        for (int i = 0; i < 5; i++) {
            bots.add(new Bot(850 + i * 65, 550, true));
        }
    }

    void color(Canvas c, int color) {
        p.setColor(color);
        p.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float sx = getWidth() / 1200f;
        float sy = getHeight() / 700f;

        canvas.save();
        canvas.scale(sx, sy);

        // Battlefield
        color(canvas, Color.rgb(55, 105, 65));
        canvas.drawColor(Color.rgb(55, 105, 65));

        // River
        color(canvas, Color.rgb(35, 100, 135));
        canvas.drawRect(0, 300, 1200, 390, p);

        // Battlefield paths
        color(canvas, Color.rgb(130, 115, 85));
        p.setStrokeWidth(55);
        canvas.drawLine(100, 600, 1100, 100, p);
        canvas.drawLine(100, 100, 1100, 600, p);

        // Bases
        color(canvas, Color.BLUE);
        canvas.drawCircle(90, 600, 55, p);

        color(canvas, Color.RED);
        canvas.drawCircle(1110, 100, 55, p);

        // Player
        color(canvas, Color.CYAN);
        canvas.drawCircle(px, py, 24, p);

        // Player HP
        drawHP(canvas, px, py - 38, playerHP, Color.GREEN);

        // Bots
        for (Bot b : bots) {
            if (b.hp <= 0) continue;

            color(canvas, b.enemy ? Color.RED : Color.BLUE);
            canvas.drawCircle(b.x, b.y, 22, p);

            drawHP(canvas, b.x, b.y - 34, b.hp,
                Color.GREEN);
        }

        // Attack control
        color(canvas, Color.argb(180, 200, 70, 30));
        canvas.drawCircle(1080, 600, 55, p);

        p.setColor(Color.WHITE);
        p.setTextSize(25);
        canvas.drawText("ATTACK", 1035, 608, p);

        p.setTextSize(24);
        canvas.drawText("HP: " + playerHP, 25, 40, p);
        canvas.drawText("OFFLINE 5V5", 500, 40, p);

        canvas.restore();

        updateGame();
        postInvalidateDelayed(40);
    }

    void drawHP(Canvas c, float x, float y, int hp, int color) {
        p.setColor(Color.DKGRAY);
        c.drawRect(x - 22, y, x + 22, y + 6, p);

        p.setColor(color);
        c.drawRect(x - 22, y,
            x - 22 + (44 * hp / 100f), y + 6, p);
    }

    void updateGame() {

        // Player movement
        float dx = targetX - px;
        float dy = targetY - py;
        float distance = (float)Math.sqrt(dx * dx + dy * dy);

        if (distance > 5) {
            px += dx / distance * 4;
            py += dy / distance * 4;
        }

        // Simple AI
        for (Bot b : bots) {
            if (b.hp <= 0) continue;

            if (b.enemy) {
                float ex = px - b.x;
                float ey = py - b.y;
                float d = (float)Math.sqrt(ex * ex + ey * ey);

                if (d > 65) {
                    b.x += ex / d * 1.2f;
                    b.y += ey / d * 1.2f;
                }
            } else {
                b.x += Math.sin(System.currentTimeMillis()
                    / 1000.0 + b.y) * 0.3f;
            }
        }

        px = Math.max(25, Math.min(1175, px));
        py = Math.max(70, Math.min(660, py));

        // MINION SPAWNING
if (System.currentTimeMillis() - lastSpawn > 5000) {

    for (int i = 0; i < 3; i++) {
        minions.add(new Minion(120, 570 + i * 15, false));
        minions.add(new Minion(1080, 130 + i * 15, true));
    }

    lastSpawn = System.currentTimeMillis();
}
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN ||
            event.getAction() == MotionEvent.ACTION_MOVE) {

            float x = event.getX() * 1200f / getWidth();
            float y = event.getY() * 700f / getHeight();

            if (x > 1000 && y > 500) {
                if (System.currentTimeMillis() - lastAttack > 500) {
                    attack();
                    lastAttack = System.currentTimeMillis();
                }
            } else {
                targetX = x;
                targetY = y;
            }

            return true;
        }

        return true;
    }

    void attack() {
        for (Bot b : bots) {
            if (!b.enemy || b.hp <= 0) continue;

            float dx = b.x - px;
            float dy = b.y - py;
            float d = (float)Math.sqrt(dx * dx + dy * dy);

            if (d < 120) {
                b.hp = Math.max(0, b.hp - 25);
                break;
            }
        }
    }
  }
