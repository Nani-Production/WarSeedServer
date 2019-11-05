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
    public static double width = 1280, height = 720;
    public static GraphicsContext gc_main;
    private Canvas canvas_main;
    private Scene scene;
    private StackPane root;
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

        TextArea area = new TextArea("with style kek");
        TextField ip = new TextField(addr.getHostAddress());
        Button start = new Button("Start Server");

        start.setTranslateX(-200);
        start.setMaxSize(100, 50);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.startServer();
                start.setDisable(true);
            }
        });
        start.setVisible(true);

        ip.setTranslateX(-500);
        ip.setTranslateY(-300);
        ip.setMinSize(100, 50);
        ip.setMaxSize(110, 50);
        ip.setVisible(true);
        ip.setEditable(false);
        ip.setDisable(true);
        System.out.println("layout  "+ip.getLayoutX()+"  "+ip.getLayoutY()+"\nTranslate   "+ip.getTranslateX()+"   "+ip.getTranslateY());

        area.setMinSize(100, 100);
        area.setMaxSize(100, 100);
        area.setVisible(true);
        System.out.println("layout  "+area.getLayoutX()+"  "+area.getLayoutY()+"\nTranslate   "+area.getTranslateX()+"   "+area.getTranslateY());

        canvas_main = new Canvas(width, height);
        root = new StackPane();
        gc_main = canvas_main.getGraphicsContext2D();


        root.getChildren().add(canvas_main);
        root.getChildren().addAll(ip, area, start);
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
}
