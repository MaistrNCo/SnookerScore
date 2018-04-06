package com.udacity.maistrenko.snookerscore;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;
import java.util.List;

/**
 * Class contains current game states and methods to change them.
 */

class Game implements Parcelable {

    private enum Ball {
        RED, COLOR, BLACK, PINK,
        BLUE, BROWN, GREEN, YELLOW
    }

    boolean firstPlayerTurn = true;
    private Ball nextBall = Ball.RED;
    private int currentBreak = 0;
    private int player1points = 0;
    private int player2points = 0;
    private int player1Frames = 0;
    private int player2Frames = 0;
    private int totalFrames = 0;
    private int redBallsLeft = 15;
    List<Ball> coloredBalls;

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
        int[] intParams = new int[7];
        intParams[0] = currentBreak;
        intParams[1] = player1points;
        intParams[2] = player2points;
        intParams[3] = redBallsLeft;
        intParams[4] = player1Frames;
        intParams[5] = player2Frames;
        intParams[6] = totalFrames;
        dest.writeIntArray(intParams);
        boolean[] flag = {firstPlayerTurn};
        dest.writeBooleanArray(flag);
    }


    Game() {
        createColoredBallsSet();
    }

    private void createColoredBallsSet() {
        coloredBalls = new LinkedList<>();
        coloredBalls.add(Ball.YELLOW);
        coloredBalls.add(Ball.GREEN);
        coloredBalls.add(Ball.BROWN);
        coloredBalls.add(Ball.BLUE);
        coloredBalls.add(Ball.PINK);
        coloredBalls.add(Ball.BLACK);
    }

    Game(Parcel parcel) {
        this();

        int[] intParams = new int[5];
        parcel.readIntArray(intParams);
        currentBreak = intParams[0];
        player1points = intParams[1];
        player2points = intParams[2];
        redBallsLeft = intParams[3];
        player1Frames = intParams[4];
        player2Frames = intParams[5];
        totalFrames = intParams[6];

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
            coloredBalls.remove(0);   //remove potted ball from list
            if (nextBall != Ball.BLACK) {
                nextBall = coloredBalls.get(0); //now first in list have to be next potted
            } else {
                nextBall = null;
            }//TODO implement behaviour  in the and of frame
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

    private int calculateFramePointsRemain() {
        int pointsRemain;
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
        } else if (nextBall == Ball.BLACK) {
            pointsRemain = 7;
        } else {
            pointsRemain = 0;
        }
        return pointsRemain;

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

    String getPlayer1Frames() {
        return String.valueOf(player1Frames);
    }
    String getPlayer2Frames() {
        return String.valueOf(player2Frames);
    }

    String getTotalFrames() {
        return String.valueOf(totalFrames);
    }

    String getFramePointsRemain() {
        return String.valueOf(calculateFramePointsRemain());
    }

    String getNextBall() {
        if (nextBall == null) {
            return "FRAME FINISHED";
        }
        return nextBall.toString();
    }

    void initNewFrame() {
        totalFrames++;
        if (player1points > player2points) {
            player1Frames++;
        } else if (player1points < player2points) {
            player2Frames++;
        }
        player1points = 0;
        player2points = 0;
        currentBreak = 0;
        createColoredBallsSet();
        nextBall = Ball.RED;
        redBallsLeft = 15;
    }

    void initNewMatch() {
        totalFrames = 0;
        player1Frames = 0;
        player2Frames =0;
        player1points = 0;
        player2points = 0;
        currentBreak = 0;
        createColoredBallsSet();
        nextBall = Ball.RED;
        redBallsLeft = 15;

    }
}
