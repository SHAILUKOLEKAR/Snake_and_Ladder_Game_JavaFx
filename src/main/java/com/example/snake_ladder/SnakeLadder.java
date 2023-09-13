package com.example.snake_ladder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SnakeLadder extends Application {

    public static final int tileSize=40,width=10,height=10;

    public static final int buttonLine = height*tileSize + 50, infoLine = buttonLine-30;

    private static Dice dice = new Dice();
    private Player playerOne,playerTwo;
    private boolean gameStarted = false, playerOneTurn = false,playerTwoTurn=false;

    private Pane createContent() throws FileNotFoundException {
        Pane root = new Pane();
        root.setPrefSize(width*tileSize,height*tileSize+80);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width ; j++) {
                Tile tile = new Tile(tileSize);
                tile.setTranslateX(j*tileSize);
                tile.setTranslateY(i*tileSize);
                root.getChildren().add(tile);
            }
        }

        Image img = new Image(new FileInputStream("E:\\ACCIO Material\\Accio Projects\\Snake Ladder\\src\\main\\img.png"));
        ImageView board = new ImageView();
        board.setImage(img);
        board.setFitHeight(height*tileSize);
        board.setFitWidth(width*tileSize);

        //Buttons
        Button playerOneButton = new Button("Player One");
        Button playerTwoButton = new Button("PLayer Two");
        Button startButton = new Button("Start");

        playerOneButton.setTranslateY(buttonLine);
        playerOneButton.setTranslateX(20);
        playerOneButton.setDisable(true);

        playerTwoButton.setTranslateY(buttonLine);
        playerTwoButton.setTranslateX(290);
        playerTwoButton.setDisable(true);

        startButton.setTranslateY(buttonLine);
        startButton.setTranslateX(180);

        //INfo display
        Label playerOneLabel = new Label("");
        Label playerTwoLabel = new Label("");
        Label diceLabel = new Label("Start Game!");

        playerOneLabel.setTranslateY(infoLine);
        playerOneLabel.setTranslateX(20);

        playerTwoLabel.setTranslateY(infoLine);
        playerTwoLabel.setTranslateX(290);

        diceLabel.setTranslateY(infoLine);
        diceLabel.setTranslateX(180);

        playerOne = new Player(tileSize, Color.BLACK,"Shailesh");
        playerTwo = new Player(tileSize-10, Color.WHITE,"Manasi");

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStarted = true;
                diceLabel.setText("Game Started!");

                startButton.setDisable(true);

                playerOneTurn = true;
                playerOneLabel.setText("Your turn "+playerOne.getName());
                playerOneButton.setDisable(false);
                playerOne.startingPositin();

                playerTwoTurn = false;
                playerTwoLabel.setText("");
                playerTwoButton.setDisable(true);
                playerTwo.startingPositin();
            }
        });

        playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(gameStarted && playerOneTurn)
                {
                    int diceValue = dice.getRolledDiceValue();
                    diceLabel.setText("Dice Value : "+ diceValue);
                    playerOne.movePlayer(diceValue);

                    //winning conditions
                    if(playerOne.isWinner())
                    {
                        diceLabel.setText("Winner is "+playerOne.getName());

                        playerOneTurn = false;
                        playerOneButton.setDisable(true);
                        playerOneLabel.setText("");

                        playerTwoTurn = false;
                        playerTwoButton.setDisable(true);
                        playerTwoLabel.setText("");

                        startButton.setDisable(false);
                        startButton.setText("Restart!");
                        gameStarted = false;

                    }
                    else {
                        playerOneTurn = false;
                        playerOneButton.setDisable(true);
                        playerOneLabel.setText("");

                        playerTwoTurn = true;
                        playerTwoButton.setDisable(false);
                        playerTwoLabel.setText("Your Turn "+ playerTwo.getName());
                    }



                }
            }
        });

        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStarted && playerTwoTurn)
                {
                    int diceValue = dice.getRolledDiceValue();
                    diceLabel.setText("Dice Value : "+ diceValue);
                    playerTwo.movePlayer(diceValue);

                    //winning conditions
                    if(playerTwo.isWinner())
                    {
                        diceLabel.setText("Winner is "+playerTwo.getName());

                        playerOneTurn = false;
                        playerOneButton.setDisable(true);
                        playerOneLabel.setText("");

                        playerTwoTurn = false;
                        playerTwoButton.setDisable(true);
                        playerTwoLabel.setText("");

                        startButton.setDisable(false);
                        startButton.setText("Restart!");
                        gameStarted = false;

                    }
                    else {

                        playerOneTurn = true;
                        playerOneButton.setDisable(false);
                        playerOneLabel.setText("Your Turn " + playerOne.getName());

                        playerTwoTurn = false;
                        playerTwoButton.setDisable(true);
                        playerTwoLabel.setText("");
                    }

                }
            }
        });


        root.getChildren().addAll(board,playerOneButton,playerTwoButton,startButton,playerOneLabel,
                playerTwoLabel,diceLabel,playerOne.getCoin(),playerTwo.getCoin());

        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
      //  Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake & Ladder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}