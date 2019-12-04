package sample;

import connection.Data_Transfer;
import connection.Server;
import gui.Gui;
import data.Data;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Gui g = new Gui();
    private static Data d = new Data();
    private static Server s = new Server();
    public static Thread info;
    static Thread connect;

    @Override
    public void start(Stage primaryStage) throws Exception{
        g.init();
        g.create(primaryStage);

        info = new Thread(new Data_Transfer(s));
        connect = new Thread(s);
    }

    public static void startGame(){
        s.setStartingGame(true);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void startServer(){
        connect.start();
        info.start();
    }
}
