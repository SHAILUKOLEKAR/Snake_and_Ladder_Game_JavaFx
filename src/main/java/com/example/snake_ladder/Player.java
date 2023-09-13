package com.example.snake_ladder;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Player {
   private Circle coin;
    private int currentPosition;
    private String name;
   private static Board gameBoard = new Board();

    public Player(int tileSize, Color coinColor,String playerName){
        coin = new Circle(tileSize/2);
        coin.setFill(coinColor);
        currentPosition = 0;//out of board position
        movePlayer(1);
        name = playerName;
    }

    public void movePlayer(int diceValue)
    {
        if(currentPosition+diceValue<=100)
        {
            currentPosition += diceValue;

            TranslateTransition firstMove = translateAnimation(diceValue),secondMove = null;


            int newPosition = gameBoard.getNewPosition(currentPosition);
            if(newPosition != currentPosition && newPosition!=-1)
            {
                currentPosition = newPosition;
                secondMove = translateAnimation(6);
            }

            if(secondMove==null)
            {
                firstMove.play();
            }else
            {
                SequentialTransition sequentialTransition = new SequentialTransition(firstMove,
                        new PauseTransition(Duration.millis(200)),secondMove);
                sequentialTransition.play();
            }


        }


       /* int x = gameBoard.getXCoordinates(currentPosition);
        int y = gameBoard.getYCoordinates(currentPosition);
        coin.setTranslateX(x);
        coin.setTranslateY(y);*/


    }

    private TranslateTransition translateAnimation(int diceValue)
    {
        TranslateTransition animate = new TranslateTransition(Duration.millis(200 * diceValue),coin);
        animate.setToX(gameBoard.getXCoordinates(currentPosition));
        animate.setToY(gameBoard.getYCoordinates(currentPosition));
        animate.setAutoReverse(false);
        return animate;
    }

    public void startingPositin(){
        currentPosition = 0;
        movePlayer(1);
    }

    boolean isWinner(){
        if(currentPosition == 100)
        {
            return true;
        }
        return false;
    }

    public Circle getCoin() {
        return coin;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public String getName() {
        return name;
    }
}
