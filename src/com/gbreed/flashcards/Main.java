package com.gbreed.flashcards;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    DataHandler dataHandler;
    Card currentCard;

    @Override
    public void start(Stage primaryStage) throws Exception{

        double width = Screen.getPrimary().getVisualBounds().getWidth();
        double height = Screen.getPrimary().getVisualBounds().getHeight();

        dataHandler = new DataHandler((int) width);

        // Create the new Border Pane
        BorderPane borderPane = new BorderPane();

        // Create the border Sections
        HBox topBox = new HBox();
        HBox midBox = new HBox();
        HBox bottomBox = new HBox();

        // Setup Top Border
        Label questionField = new Label("Question");
        questionField.setFont(new Font("Arial", 36));
        topBox.setPadding(new Insets(height / 20, 0, 40, 0));

        // Setup Middle Border
        TextArea definitionField = new TextArea ("Definition");
        definitionField.setFont(new Font("Arial", 30));
        definitionField.setWrapText(true);
        definitionField.setEditable(false);
        midBox.setPadding(new Insets(0, 10, 20, 10));

        // Setup Bottom Border
        Button showDef = new Button("Show Definition");
        showDef.setPrefSize(100, 20);
        Button good = new Button("Good!");
        good.setPrefSize(100, 20);
        Button ok = new Button("OK");
        ok.setPrefSize(100, 20);
        Button bad = new Button("Bad");
        bad.setPrefSize(100, 20);

        bottomBox.setPadding(new Insets(0, 20, 20, 0));
        bottomBox.setSpacing(20);

        // Handle the button Clicks
        good.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentCard = dataHandler.good();
                questionField.setText(currentCard.getQuestion());
                definitionField.setText("");
                good.setDisable(true);
                bad.setDisable(true);
                ok.setDisable(true);
            }
        });

        bad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentCard = dataHandler.bad();
                questionField.setText(currentCard.getQuestion());
                definitionField.setText("");
                good.setDisable(true);
                bad.setDisable(true);
                ok.setDisable(true);
            }
        });

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentCard = dataHandler.ok();
                questionField.setText(currentCard.getQuestion());
                definitionField.setText("");
                good.setDisable(true);
                bad.setDisable(true);
                ok.setDisable(true);
            }
        });

        showDef.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                definitionField.setText(currentCard.getDefinition());
                good.setDisable(false);
                bad.setDisable(false);
                ok.setDisable(false);
            }
        });

        // Add children to border sections
        midBox.getChildren().add(definitionField);
        topBox.getChildren().add(questionField);
        bottomBox.getChildren().addAll(showDef, good, bad, ok);

        // Center the labels
        topBox.setAlignment(Pos.CENTER);
        midBox.setAlignment(Pos.CENTER);
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);

        // Add border sections to border pane
        borderPane.setTop(topBox);
        borderPane.setCenter(midBox);
        borderPane.setBottom(bottomBox);

        primaryStage.setTitle("Cards");
        primaryStage.setScene(new Scene(borderPane, width / 2, height / 2));
        primaryStage.setResizable(false);
        primaryStage.show();

        currentCard = dataHandler.getRandomLowestCard();
        questionField.setText(currentCard.getQuestion());
        definitionField.setText("");
        good.setDisable(true);
        bad.setDisable(true);
        ok.setDisable(true);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
