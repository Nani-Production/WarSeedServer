package sample;

import connection.Data_Transfer;
import connection.Server;
import data.Data_Processing;
import gui.Console;
import gui.Gui;
import data.Data;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Gui g = new Gui();
    private static Data d = new Data();
    private static Server s = new Server();
    public static Thread info, process;
    static Thread connect;

    @Override
    public void start(Stage primaryStage) throws Exception{
        g.init();
        g.create(primaryStage);

        info = new Thread(new Data_Transfer(s));
        process = new Thread(new Data_Processing());
        connect = new Thread(s);
    }

    public static void startGame(){
        s.setStartingGame(true);
        Console.addMessage("game started");
        //process.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void startServer(){
        connect.start();
        info.start();
    }

    public static String countReadyClients(){
        int counter1 = 0, counter2 = 0;
        for (int i = 0; i < s.getClients().size(); i++){
            if (s.getClients().get(i).isConnected() && s.getClients().get(i).isReady() && !s.getClients().get(i).isNoResponse()){
                counter1++;
            }
            if (s.getClients().get(i).isConnected()){
                counter2++;
            }
        }
        if (counter1 == counter2){
            return "["+counter1+"/"+counter2+"]";
        } else {
            return "all";
        }
    }
}
