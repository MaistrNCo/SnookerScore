package com.udacity.maistrenko.snookerscore;

/**
 * Class contains current game states and methods to change them.
 */

class Game {
    boolean firstPlayerTurn = true;
    private int currentBreak = 0;
    private int pointsRemain = 147;
    private int player1points = 0;
    private int player2points = 0;
    private int redBallsLeft = 15;


    void increaseBreakPoints(int points){
        currentBreak += points;
        pointsRemain -= points;
    }
    boolean isFirstPlayerTurn(){
        return firstPlayerTurn;
    }

    void changePlayerTurn(){
        firstPlayerTurn = !firstPlayerTurn;
    }

    String getCurrentBreak(){
        return String.valueOf(currentBreak);
    }

}
