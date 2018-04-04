package com.udacity.maistrenko.snookerscore;

import java.util.LinkedList;
import java.util.List;

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
    private int redBallsLeft = 5;
    List<Ball> coloredBalls;

    Game() {
        coloredBalls = new LinkedList<>();
        coloredBalls.add(Ball.YELLOW);
        coloredBalls.add(Ball.GREEN);
        coloredBalls.add(Ball.BROWN);
        coloredBalls.add(Ball.BLUE);
        coloredBalls.add(Ball.PINK);
        coloredBalls.add(Ball.BLACK);
    }

    boolean increaseBreakPoints(int points) {

        if (nextBall == Ball.RED) {
            if (points > 1) return false;  //pushed color button instead of red
        } else if (nextBall == Ball.COLOR) {
            if (points == 1) return false;//pushed red button instead of color
        } else {
            Ball pottedBall = getBallColorFromPointsAmount(points);
            if (pottedBall != nextBall) return false;
        }

        currentBreak += points;

        if (firstPlayerTurn) {
            player1points += points;
        } else {
            player2points += points;
        }
        calculateFramePointsRemain();

        findLegalNextBall();

        return true;
    }

    private void findLegalNextBall() {
        //next ball??
        if (nextBall == Ball.RED) {
            redBallsLeft--;
            nextBall = Ball.COLOR; // after red ball always colored
        } else if (nextBall == Ball.COLOR) {
            if (redBallsLeft > 0) {
                nextBall = Ball.RED; //after colored - red
            } else {
                nextBall = coloredBalls.get(0); // or colored by order if ran out of red
            }
        } else { //last colored sequence by order
            if (nextBall != Ball.BLACK) {
                coloredBalls.remove(0);   //remove potted ball from list
                nextBall = coloredBalls.get(0); //now first in list have to be next potted
            } //TODO implement behaviour  in the and of frame
        }
    }

    private Ball getBallColorFromPointsAmount(int points) {
        switch (points) {
            case 2:
                return Ball.YELLOW;
            case 3:
                return Ball.GREEN;
            case 4:
                return Ball.BROWN;
            case 5:
                return Ball.BLUE;
            case 6:
                return Ball.PINK;
            case 7:
                return Ball.BLACK;
        }
        return Ball.RED;
    }

    boolean isFirstPlayerTurn() {
        return firstPlayerTurn;
    }

    void changePlayerTurn() {
        currentBreak = 0;
        if (redBallsLeft > 0) {
            nextBall = Ball.RED;
        } else {
            nextBall = coloredBalls.get(0);
        }
        firstPlayerTurn = !firstPlayerTurn;
    }

    private void calculateFramePointsRemain() {
        if (redBallsLeft > 0) {
            pointsRemain = redBallsLeft * 8 + 27;
        } else if (nextBall == Ball.COLOR) {
            pointsRemain = 36;
        } else if (nextBall == Ball.YELLOW) {
            pointsRemain = 27;
        } else if (nextBall == Ball.GREEN) {
            pointsRemain = 25;
        } else if (nextBall == Ball.BROWN) {
            pointsRemain = 22;
        } else if (nextBall == Ball.BLUE) {
            pointsRemain = 18;
        } else if (nextBall == Ball.PINK) {
            pointsRemain = 13;
        } else {
            pointsRemain = 7;
        }

    }

    String getCurrentBreak() {
        return String.valueOf(currentBreak);
    }

    String getPlayer1points() {
        return String.valueOf(player1points);
    }

    String getPlayer2points() {
        return String.valueOf(player2points);
    }

    String getFramePointsRamain() {
        return String.valueOf(pointsRemain);
    }

    String getNextBall() {
        return nextBall.toString();
    }
}
