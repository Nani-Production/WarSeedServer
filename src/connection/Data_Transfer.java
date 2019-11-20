package connection;

import data.Data;
import sample.Main;

import java.io.*;

public class Data_Transfer implements Runnable {
    boolean running = true;
    private Server s = null;
    private Data data;

    public Data_Transfer(Server s, Data d){
        this.s = s;
    }

    @Override
    public void run() {
        while (running){
            //TODO die Daten sende, verarbeiten und schicken
            if (s.getClients().size() > 0){
                System.out.println(s.getClients().size());
                receiveData();
                sendData();
            }
        }
    }
    private void receiveData (){
        for (int i = 0; i < s.getClients().size(); i++){
            if (s.getClients().get(i).getName() != null){
                String message;
                System.out.println("read messages");
                try {
                    message = s.getClients().get(i).getReader().readLine();
                    System.out.println("message "+message);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException ignore) {}
            }
        }
    }

    private void sendData (){
        for (int i = 0; i < s.getClients().size(); i++){

        }
    }
}