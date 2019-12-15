package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Main;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Gui {
    public static double width = 500, height = 600; //h400
    public static GraphicsContext gc_main;
    private Canvas canvas_main;
    private Scene scene;
    private StackPane root;
    private TextArea area;
    private TextField ip;
    private Button startServer, startGame;
    InetAddress addr = null;
    //public Font myFont = Font.loadFont(getClass().getResourceAsStream("/resources/Guardians.ttf"), 24);


    public void init () {
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void create (Stage stage) {
        area = new TextArea("");
        area.setMinSize(100, 100);
        area.setMaxSize(400, 300);
        area.setTranslateY(120);
        area.setEditable(false);
        area.setVisible(true);

        ip = new TextField(addr.getHostAddress());
        ip.setTranslateY(50-(height/2));
        ip.setMinSize(100, 50);
        ip.setMaxSize(110, 50);
        ip.setVisible(true);
        ip.setEditable(false);
        ip.setDisable(true);

        startServer = new Button("Start Server");
        startServer.setTranslateY(-75);
        startServer.setMaxSize(100, 50);
        startServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.startServer();
                startServer.setDisable(true);
            }
        });
        startServer.setVisible(true);

        startGame = new Button("Start Game");
        startGame.setTranslateY(-150);
        startGame.setMaxSize(100, 50);
        startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.startGame();
                startGame.setDisable(true);
            }
        });
        startGame.setDisable(true);
        startGame.setVisible(true);

        canvas_main = new Canvas(width, height);
        root = new StackPane();
        gc_main = canvas_main.getGraphicsContext2D();

        root.getChildren().add(canvas_main);
        root.getChildren().addAll(ip, area, startServer, startGame);
        scene = new Scene(root, width, height);

        stage.setTitle("Gameserver");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public void updateConsole(){
        area.setText(Console.printMessages());
        area.setScrollTop(Double.MAX_VALUE);
    }

    public Button getStartGame() {
        return startGame;
    }
}
