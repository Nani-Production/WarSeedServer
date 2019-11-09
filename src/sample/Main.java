package sample;

import connection.Data_Transfer;
import connection.Server;
import gui.Console;
import gui.Gui;
import information.Data;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Gui g = new Gui();
    Data d = new Data();
    Server s = new Server();
    static Thread info, connect;

    @Override
    public void start(Stage primaryStage) throws Exception{
        g.init();
        g.create(primaryStage);

        info = new Thread(new Data_Transfer(s, d));
        connect = new Thread(s);
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void startServer(){
        connect.start();
        info.start();
    }
}
