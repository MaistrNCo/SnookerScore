package com.udacity.maistrenko.snookerscore;

/**
 * Class contains current game states and methods to change them.
 */

class Game {
    private enum Ball {
        RED, COLOR, BLACK, PINK,
        BLUE, BROWN, GREEN, YELLOW
    }
    boolean firstPlayerTurn = true;
    private Ball nextBall = Ball.RED;
    private int currentBreak = 0;
    private int pointsRemain = 147;
    private int player1points = 0;
    private int player2points = 0;
    private int redBallsLeft = 15;


    void increaseBreakPoints(int points){

        currentBreak += points;
        pointsRemain -= points;

        if (firstPlayerTurn) {
            player1points += points;
        }else{
            player2points += points;
        }
        calculateFramePointsRemain();
    }
    boolean isFirstPlayerTurn(){
        return firstPlayerTurn;
    }

    void changePlayerTurn(){

        currentBreak = 0;
        nextBall = Ball.RED;
        firstPlayerTurn = !firstPlayerTurn;
    }

    String getCurrentBreak(){
        return String.valueOf(currentBreak);
    }

    String getPlayer1points(){
        return String.valueOf(player1points);
    }
    String getPlayer2points(){
        return String.valueOf(player2points);
    }
    String getFramePointsRamain(){
        return String.valueOf(pointsRemain);
    }


    private void calculateFramePointsRemain(){
        pointsRemain = 147;
    }
}
