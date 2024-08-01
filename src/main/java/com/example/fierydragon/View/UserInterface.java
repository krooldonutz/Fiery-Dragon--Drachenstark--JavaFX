package com.example.fierydragon.View;

import com.example.fierydragon.Controller.GameStateController;
import com.example.fierydragon.Grounds.Board;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.File;

/**
 * The UserInterface class is responsible for setting up and displaying the main
 * user interface for the Fiery Dragons game. This includes the main menu, player
 * selection screen, rules screen, and starting the game.
 */
public class UserInterface extends Application {
    private static final int total_cells = 16;
    private GameStateController controller;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fiery Dragons Game");
        initializeMainScreen(primaryStage);
    }

    /**
     * Initializes the main screen with title, start, rules, and exit buttons.
     *
     * @param primaryStage The primary stage for this application.
     */
    private void initializeMainScreen(Stage primaryStage) {
        VBox startScreen = setupVBox(10, Pos.CENTER, new Insets(10), "Background.png");

        Label titleLabel = createLabel("Fiery Dragons Game", "Chiller", FontWeight.BOLD, 70, Color.web("#00FF00"), new DropShadow(10, Color.BLACK));
        Button startButton = createStyledButton("Start Game", "green", "black");
        Button rulesButton = createStyledButton("Rules", "green", "black");
        Button statsButton = createStyledButton("Stats Screen", "green", "black"); // New stats screen button
        Button exitButton = createStyledButton("Exit", "green", "black");

        startButton.setOnAction(e -> showPlayerSelectionScreen(primaryStage));
        rulesButton.setOnAction(e -> showRules(primaryStage));
        statsButton.setOnAction(e -> showStatsScreen(primaryStage)); // Handle stats screen button click
        exitButton.setOnAction(e -> primaryStage.close());

        startScreen.getChildren().addAll(titleLabel, startButton, rulesButton, statsButton, exitButton); // Add statsButton here
        Scene startScene = new Scene(startScreen, 1000, 570);
        primaryStage.setScene(startScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Displays the player selection screen where users can choose the number of players.
     *
     * @param primaryStage The primary stage for this application.
     */
    private void showPlayerSelectionScreen(Stage primaryStage) {
        VBox menuBox = setupVBox(20, Pos.TOP_CENTER, new Insets(15), "Background.png");
        Label playerSelectionLabel = createLabel("Select How Many Players", "Chiller", FontWeight.BOLD, 70, Color.web("#00FF00"), new DropShadow(10, Color.BLACK));

        Button btn2 = createStyledButton("2 Players", "green", "black");
        Button btn3 = createStyledButton("3 Players", "green", "black");
        Button btn4 = createStyledButton("4 Players", "green", "black");
        Button loadGameButton = createStyledButton("Load Game", "green", "black"); // Add this line
        Button backBtn = createStyledButton("<- Back", "green", "black");

        btn2.setOnAction(e -> startGame(primaryStage, 2));
        btn3.setOnAction(e -> startGame(primaryStage, 3));
        btn4.setOnAction(e -> startGame(primaryStage, 4));
        loadGameButton.setOnAction(e -> loadGameFromFile(primaryStage)); // Handle load game button click

        menuBox.getChildren().addAll(playerSelectionLabel, btn2, btn3, btn4, loadGameButton, backBtn); // Add loadGameButton here
        primaryStage.setScene(new Scene(menuBox, 1000, 570));
    }

    /**
     * Displays the rules screen which shows the game rules.
     *
     * @param primaryStage The primary stage for this application.
     */
    private void showRules(Stage primaryStage) {
        VBox ruleBox = setupVBox(10, Pos.TOP_CENTER, new Insets(10), "Background.png");
        Label ruleTitleLabel = createLabel("Rules", "Chiller", FontWeight.BOLD, 70, Color.web("#00FF00"), new DropShadow(10, Color.BLACK));
        Label rulesLabel = createLabel("Everyone knows that baby dragons are courageous. \n" +
                "But they need also a good memory. And they prove it with a \n" +
                "race around the bubbling volcano. \n" +
                "Each player starts his baby dragon in another cave. \n" +
                "The active player tries to uncover a chit with the kind of \n" +
                "symbol shown on the field he's standing on. If he fails, his turn \n" +
                "is over. If he draws a pirate dragon, he even must move back. \n" +
                "If he succeeds, he moves forward up 1, 2, or 3 spaces (depending\n" +
                " on the chit), and continues with his turn.\n" +
                "The first player that completes a round around the board and \n" +
                "reaches his cave with an exact move wins the game.", "Chiller", FontWeight.NORMAL, 20, Color.BLACK, null);

        Button backBtn = createStyledButton("<- Back", "green", "black");
        backBtn.setOnAction(e -> initializeMainScreen(primaryStage));

        ruleBox.getChildren().addAll(ruleTitleLabel, rulesLabel, backBtn);
        primaryStage.setScene(new Scene(ruleBox, 1000, 570));
    }

    private void loadGameFromFile(Stage primaryStage) {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            try {
                // Load game state
                Board board = new Board(2, total_cells);
                BoardView boardView = new BoardView(board);
                Pane boardPane = boardView.getBoardPane();
                Pane overlayPane = new Pane();
                GameStateController loadedController = new GameStateController(board, boardView, overlayPane);
                loadedController.loadGameState(selectedFile.getPath()); // Pass the file path to loadGameState
                StackPane root = new StackPane(boardPane, overlayPane);
                Scene scene = new Scene(root, boardView.getBoardWidth(), boardView.getBoardHeight());
                primaryStage.setScene(scene);
                primaryStage.setTitle("Fiery Dragons");
                primaryStage.show();
                primaryStage.setResizable(false);
            } catch (Exception e) {
                // Handle the case where the file is not a valid JSON file or loadGameState throws an exception
                System.out.println("Invalid file: " + e.getMessage());
                showPlayerSelectionScreen(primaryStage);
            }
        } else {
            // Handle the case where no file was selected
            System.out.println("No file selected");
        }
    }

    /**
     * Starts the game by initializing the game board, setting up the scene, and showing it.
     *
     * @param primaryStage The primary stage for this application.
     * @param numPlayers   The number of players in the game.
     */
    private void startGame(Stage primaryStage, int numPlayers) {
        VBox gameLayout = new VBox();
        primaryStage.setScene(new Scene(gameLayout, 1000, 570));

        Board board = new Board(numPlayers, total_cells);
        BoardView boardView = new BoardView(board);
        Pane boardPane = boardView.getBoardPane();
        Pane overlayPane = new Pane();
        GameStateController controller = new GameStateController(board, boardView, overlayPane);
        StackPane root = new StackPane(boardPane, overlayPane);
        Scene scene = new Scene(root, boardView.getBoardWidth(), boardView.getBoardHeight());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fiery Dragons");
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    /**
     * Sets up a VBox with specified properties and a background image.
     *
     * @param spacing         The amount of vertical space between each child in the VBox.
     * @param alignment       The alignment of the VBox.
     * @param padding         The padding around the VBox.
     * @param backgroundImage The background image for the VBox.
     * @return A VBox with the specified properties.
     */
    private VBox setupVBox(int spacing, Pos alignment, Insets padding, String backgroundImage) {
        VBox vBox = new VBox(spacing);
        vBox.setAlignment(alignment);
        vBox.setPadding(padding);
        vBox.setBackground(new Background(new BackgroundImage(
                new Image(getClass().getClassLoader().getResource(backgroundImage).toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
        return vBox;
    }

    /**
     * Creates a styled label with specified properties.
     *
     * @param text       The text of the label.
     * @param fontFamily The font family of the text.
     * @param weight     The font weight of the text.
     * @param size       The font size of the text.
     * @param textColor  The color of the text.
     * @param shadow     The shadow effect for the text.
     * @return A Label with the specified properties.
     */
    private Label createLabel(String text, String fontFamily, FontWeight weight, double size, Color textColor, DropShadow shadow) {
        Label label = new Label(text);
        label.setFont(Font.font(fontFamily, weight, size));
        label.setTextFill(textColor);
        if (shadow != null) label.setEffect(shadow);
        return label;
    }

    /**
     * Creates a styled button with specified properties.
     *
     * @param text         The text of the button.
     * @param hoverColor   The color of the text when the button is hovered.
     * @param defaultColor The default color of the text.
     * @return A Button with the specified properties.
     */
    private Button createStyledButton(String text, String hoverColor, String defaultColor) {
        Button button = new Button(text);
        applyBaseStyle(button);
        applyHoverEffect(button, hoverColor, defaultColor);
        return button;
    }

    /**
     * Applies base style to a button.
     *
     * @param button The button to style.
     */
    private void applyBaseStyle(Button button) {
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                "-fx-text-fill: black; -fx-font-family: Chiller; -fx-font-size: 40;");
    }

    /**
     * Applies hover effect to a button.
     *
     * @param button       The button to style.
     * @param hoverColor   The color of the text when the button is hovered.
     * @param defaultColor The default color of the text.
     */
    private void applyHoverEffect(Button button, String hoverColor, String defaultColor) {
        String baseStyle = "-fx-background-color: transparent; -fx-border-color: transparent; " +
                "-fx-font-family: Chiller; -fx-font-size: 40;";
        button.setOnMouseEntered(e -> button.setStyle(baseStyle + "-fx-text-fill: " + hoverColor + ";"));
        button.setOnMouseExited(e -> button.setStyle(baseStyle + "-fx-text-fill: " + defaultColor + ";"));
    }

    /**
     * Displays the stats screen.
     *
     * @param primaryStage The primary stage for this application.
     */
    private void showStatsScreen(Stage primaryStage) {
        // Create a FileChooser to select the save file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            StatsScreenView statsScreenView = new StatsScreenView(primaryStage);
            statsScreenView.showStatsScreen(selectedFile.getPath()); // Pass the file path to the stats screen view
        } else {
            // Handle the case where no file was selected
            System.out.println("No file selected");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}