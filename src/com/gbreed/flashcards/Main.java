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

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Main extends Application {
    DataHandler dataHandler;
    Card currentCard;
    Boolean showingDef = false;

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
        HBox bottomTextBox = new HBox();

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
        showDef.setPrefSize(120, 20);
        Button good = new Button("Good!");
        good.setPrefSize(100, 20);
        Button ok = new Button("OK");
        ok.setPrefSize(100, 20);
        Button bad = new Button("Bad");
        bad.setPrefSize(100, 20);
        Button skip = new Button("Skip");
        skip.setPrefSize(100, 20);

        // Setup Bottom Text Border
        Label goodText = new Label("Good: 0");
        Label badText = new Label("Bad: 0");
        Label okText = new Label("Ok: 0");
        goodText.setPrefSize(100, 20);
        badText.setPrefSize(100, 20);
        okText.setPrefSize(100, 20);

        bottomBox.setPadding(new Insets(0, 0, 20, 20));
        bottomBox.setSpacing(20);

        skip.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentCard = dataHandler.getRandomLowestCard();
                updateFields(questionField, definitionField, good, bad, ok, okText, badText, goodText);
            }
        });

        // Handle the button Clicks
        good.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showingDef = false;
                currentCard = dataHandler.good();
                updateFields(questionField, definitionField, good, bad, ok, okText, badText, goodText);
            }
        });

        bad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showingDef = false;
                currentCard = dataHandler.bad();
                updateFields(questionField, definitionField, good, bad, ok, okText, badText, goodText);
            }
        });

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showingDef = false;
                currentCard = dataHandler.ok();
                updateFields(questionField, definitionField, good, bad, ok, okText, badText, goodText);
            }
        });

        showDef.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (showingDef)
                {
                    definitionField.setText("");
                    showingDef = false;
                    good.setDisable(true);
                    bad.setDisable(true);
                    ok.setDisable(true);
                } else {
                    definitionField.setText(currentCard.getDefinition());
                    showingDef = true;
                    good.setDisable(false);
                    bad.setDisable(false);
                    ok.setDisable(false);
                }
            }
        });

        // Add children to border sections
        midBox.getChildren().add(definitionField);
        topBox.getChildren().add(questionField);
        bottomBox.getChildren().addAll(skip, showDef, good, bad, ok);
        bottomTextBox.getChildren().addAll(goodText, badText, okText);

        // Center the labels
        topBox.setAlignment(Pos.CENTER);
        midBox.setAlignment(Pos.CENTER);

        HBox someBot = new HBox();
        someBot.getChildren().addAll(bottomBox, bottomTextBox);
        someBot.setSpacing(((width / 2) - 840) * 2);

        // Add border sections to border pane
        borderPane.setTop(topBox);
        borderPane.setCenter(midBox);
        borderPane.setBottom(someBot);

        primaryStage.setTitle("Cards");
        primaryStage.setScene(new Scene(borderPane, width / 2, height / 2));
        primaryStage.setResizable(false);
        primaryStage.show();

        currentCard = dataHandler.getRandomLowestCard();
        updateFields(questionField, definitionField, good, bad, ok, okText, badText, goodText);
    }

    private void updateFields(Label questionField, TextArea definitionField, Button good, Button bad, Button ok, Label okText, Label badText, Label goodText) {
        questionField.setText(currentCard.getQuestion());
        definitionField.setText("");
        good.setDisable(true);
        bad.setDisable(true);
        ok.setDisable(true);

        okText.setText("Ok: " + dataHandler.getOkCards());
        badText.setText("Bad: " + dataHandler.getBadCards());
        goodText.setText("Good: " + dataHandler.getGoodCards());

        switch(dataHandler.getCurrentScore()) {
            case 0:
                definitionField.setStyle("-fx-border-color: red;");
                break;
            case 1:
                definitionField.setStyle("-fx-border-color: yellow;");
                break;
            case 2:
                definitionField.setStyle("-fx-border-color: green;");
                break;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
