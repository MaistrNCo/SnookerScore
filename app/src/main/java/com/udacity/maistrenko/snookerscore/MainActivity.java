package com.udacity.maistrenko.snookerscore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Game game;

    MainActivity() {
        game = new Game();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPlayerTurn();
    }

    public void changePlayer(View view) {
        game.changePlayerTurn();
        setPlayerTurn();
        refreshTextViewWithValue(R.id.brake_points, "Brake score: " + game.getCurrentBreak());
    }

    private void setPlayerTurn() {
        ImageView marker1 = findViewById(R.id.current_player1_marker);
        ImageView marker2 = findViewById(R.id.current_player2_marker);
        if (game.isFirstPlayerTurn()) {
            marker1.setVisibility(View.VISIBLE);
            marker2.setVisibility(View.INVISIBLE);
        } else {
            marker1.setVisibility(View.INVISIBLE);
            marker2.setVisibility(View.VISIBLE);
        }
    }

    public void addPoint(View view) {
        int points = 0;
        switch (view.getId()) {
            case R.id.add_yellow:
                points = 2;
                break;
            case R.id.add_green:
                points = 3;
                break;
            case R.id.add_brown:
                points = 4;
                break;
            case R.id.add_blue:
                points = 5;
                break;
            case R.id.add_pink:
                points = 6;
                break;
            case R.id.add_black:
                points = 7;
                break;
            case R.id.add_red:
                points = 1;
                break;

            default:
                throw new RuntimeException("Unknow button ID");
        }
        game.increaseBreakPoints(points);
        if (game.isFirstPlayerTurn()){
            refreshTextViewWithValue(R.id.player1_frame_points, game.getPlayer1points());
        } else {
            refreshTextViewWithValue(R.id.player2_frame_points, game.getPlayer2points());
        }
        refreshTextViewWithValue(R.id.brake_points, "Brake score: " + game.getCurrentBreak());
        refreshTextViewWithValue(R.id.remain_points, "Remaining score: " + game.getFramePointsRamain());

    }

    private void refreshTextViewWithValue(int brake_points, String s) {
        TextView tv = findViewById(brake_points);
        tv.setText(s);
    }

}
