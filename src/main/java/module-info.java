module com.example.snake_ladder {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.snake_ladder to javafx.fxml;
    exports com.example.snake_ladder;
}