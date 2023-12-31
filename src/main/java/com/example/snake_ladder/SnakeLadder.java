package com.example.snake_ladder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class SnakeLadder extends Application {

    public static final int tileSize=40,width=10,height=10;

    public static final int buttonLine = height*tileSize + 50, infoLine = buttonLine-30;

    private static Dice dice = new Dice();
    private Player playerOne,playerTwo;
    private boolean gameStarted = false, playerOneTurn = false,playerTwoTurn=false,collided=false;

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

       // getPlayerNames();

        playerOne = new Player(tileSize, Color.BLACK,"Shailesh");
        playerTwo = new Player(tileSize, Color.WHITE,"Manasi");

        //check the collision and distinguish the players
        /*checkCollision(playerOne.getCoin().getCenterX(),playerOne.getCoin().getCenterY(),
                playerTwo.getCoin().getCenterX(),playerTwo.getCoin().getCenterY());*/

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStarted = true;
                diceLabel.setText("Game Started!");

                //check the collision and distinguish the players
                checkCollision(playerOne.getCurrentPosition(),playerTwo.getCurrentPosition());

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

                    //check the collision and distinguish the players
                    checkCollision(playerOne.getCurrentPosition(),playerTwo.getCurrentPosition());

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

                    //check the collision and distinguish the players
                    checkCollision(playerOne.getCurrentPosition(),playerTwo.getCurrentPosition());

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

    public void getPlayerNames() throws FileNotFoundException {
        // Create a custom graphic (icon) for the dialog
        Image customIcon = new Image(new FileInputStream("E:\\ACCIO Material\\Accio Projects\\Snake Ladder\\src\\main\\img.png")); // Replace with the path to your custom icon
        ImageView iconView = new ImageView(customIcon);
        iconView.setFitWidth(15);
        iconView.setFitHeight(15);

        TextInputDialog player1Dialog = new TextInputDialog();
        player1Dialog.setTitle("Player 1 Name");
        player1Dialog.setHeaderText("Enter Player 1's Name:");
        player1Dialog.setGraphic(iconView); // Set the custom icon
        Optional<String> player1Result = player1Dialog.showAndWait();

        TextInputDialog player2Dialog = new TextInputDialog();
        player2Dialog.setTitle("Player 2 Name");
        player2Dialog.setHeaderText("Enter Player 2's Name:");
        player2Dialog.setGraphic(iconView); // Set the same custom icon
        Optional<String> player2Result = player2Dialog.showAndWait();

        // Check if the user entered a name and store it in variables
        String player1Name = player1Result.orElse("Player 1");
        String player2Name = player2Result.orElse("Player 2");

        // Now, you have the player names in the 'player1Name' and 'player2Name' variables.
        System.out.println("Player 1's Name: " + player1Name);
        System.out.println("Player 2's Name: " + player2Name);
    }

    private void checkCollision(int playerOnePos, int playerTwoPos){
        //System.out.println(collided);
       /* System.out.println(plOneX + "**" + plTwoX);
        System.out.println(plOneY + "**" + plTwoY);*/
        //System.out.println(":::::::");
        if(playerOnePos == playerTwoPos && !collided)
        {
            //System.out.println("spread!!!");
           // playerOne.getCoin().setRotate(moveByPixels);
            playerOne.getCoin().setCenterX(playerOne.getCoin().getCenterX() + 5);
            playerOne.getCoin().setCenterY(playerOne.getCoin().getCenterY() + 2);

           // playerTwo.getCoin().setRotate(0);
            playerTwo.getCoin().setCenterX(playerTwo.getCoin().getCenterX() - 5);
            playerTwo.getCoin().setCenterY(playerTwo.getCoin().getCenterY() - 2);

            collided = true;
        }
        else if(collided)
        {
            //System.out.println("Merged!!!");
            // playerOne.getCoin().setRotate(moveByPixels);
            playerOne.getCoin().setCenterX(playerOne.getCoin().getCenterX() - 5);
            playerOne.getCoin().setCenterY(playerOne.getCoin().getCenterY() - 2);

            // playerTwo.getCoin().setRotate(0);
            playerTwo.getCoin().setCenterX(playerTwo.getCoin().getCenterX() + 5);
            playerTwo.getCoin().setCenterY(playerTwo.getCoin().getCenterY() + 2);

            collided = false;
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
      //  Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake & Ladder");
        stage.setScene(scene);

        Image icon = new Image(new FileInputStream("E:\\ACCIO Material\\Accio Projects\\Snake_and_Ladder_Game_JavaFx\\src\\main\\resources\\snakes-and-ladders.png"));
        stage.getIcons().add(icon);

        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}