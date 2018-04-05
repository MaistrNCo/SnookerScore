package com.udacity.maistrenko.snookerscore;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;
import java.util.List;

/**
 * Class contains current game states and methods to change them.
 */

class Game implements Parcelable {

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        int[] intParams = new int[5];
        intParams[0] = currentBreak;
        intParams[1] = player1points;
        intParams[2] = player2points;
        intParams[3] = redBallsLeft;
        dest.writeIntArray(intParams);
        boolean[] flag = {firstPlayerTurn};
        dest.writeBooleanArray(flag);
    }


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

    Game(Parcel parcel) {
        coloredBalls = new LinkedList<>();
        coloredBalls.add(Ball.YELLOW);
        coloredBalls.add(Ball.GREEN);
        coloredBalls.add(Ball.BROWN);
        coloredBalls.add(Ball.BLUE);
        coloredBalls.add(Ball.PINK);
        coloredBalls.add(Ball.BLACK);

        int[] intParams = new int[5];
        parcel.readIntArray(intParams);
        currentBreak = intParams[0];
        player1points = intParams[1];
        player2points = intParams[2];
        redBallsLeft = intParams[3];

        currentBreak = parcel.readInt();
        player1points = parcel.readInt();
        player2points = parcel.readInt();
        redBallsLeft = parcel.readInt();

        boolean[] flag = new boolean[1];
        parcel.readBooleanArray(flag);
        firstPlayerTurn = flag[0];
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
