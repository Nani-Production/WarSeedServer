package connection;

import data.Data;
import data.Data_Processing;
import gui.Console;
import sample.Main;

import java.io.*;
import java.util.ArrayList;

public class Data_Transfer implements Runnable {
    private Server s;
    private static ArrayList<Thread> tubes = new ArrayList<>();

    public Data_Transfer(Server s){
        this.s = s;
    }

    @Override
    public void run() {
        while (true){
            if (s.getClients().size() > 0){
                if (receiveData()){
                    processData();
                }
                sendData();

                for (int i = 0; i < s.getClients().size(); i++){
                    if (System.currentTimeMillis() - s.getClients().get(i).getLastPong() > 10000 && s.getClients().get(i).isConnected()){
                        //Disconnect to this client
                        Console.addMessage("Client "+s.getClients().get(i).getName()+" is not responding");
                    }
                }

                if (!s.isGameRunning() && !s.isStartingGame()){
                    boolean allReady = true;
                    int counter = 0;

                    for (int i = 0; i < s.getClients().size()-1; i++){
                        if (!s.getClients().get(i).isReady() && s.getClients().get(i).isConnected()){
                            allReady = false;
                        }
                        if (s.getClients().get(i).isConnected()){
                            counter++;
                        }
                    }
                    if (counter < 1){
                        allReady = false;
                    }
                    if (allReady){
                        Main.g.getStartGame().setDisable(false);
                    } else {
                        Main.g.getStartGame().setDisable(true);
                    }
                }
            }
        }
    }

    private void processData() {
        //moving
        double [] coord = new double[2];
        for (int i = 0; i < Data.getListofLists().size(); i++){
            if (Data.getListofLists().get(i).get(0).equals("//character")){
                coord = Data_Processing.moveCharacter(Data.getListofLists().get(i));
                //TODO checking for collision
                Data.getListofLists().get(i).set(5, Double.toString(coord[0]));
                Data.getListofLists().get(i).set(6, Double.toString(coord[1]));
            }
        }

        //fighting


        //cooldown
    }

    private boolean receiveData (){
        boolean received = false;
        for (int i = 0; i < s.getClients().size(); i++){
            if (s.getClients().get(i).isConnected()){
                try {
                    for (int j = 0; j < s.getClients().get(i).getTube().getBuffer().size(); j++){
                        Data.addData(s.getClients().get(i).getTube().getBuffer().get(j));
                        received = true;
                    }
                    return received;
                } catch (NullPointerException ignore) {}
            }
        }
        return received;
    }

    private void sendData (){
        for (int i = 0; i < s.getClients().size(); i++){
            long currentPong = System.currentTimeMillis();
            if (s.getClients().get(i).isPong() && currentPong - s.getClients().get(i).getLastPong() > 1000 && s.getClients().get(i).isConnected()){
                try {
                    s.getClients().get(i).getWriter().write("ping");
                    s.getClients().get(i).getWriter().newLine();
                    s.getClients().get(i).getWriter().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                s.getClients().get(i).setPong(false);
                s.getClients().get(i).setLastPong(System.currentTimeMillis());
            }
        }


        if (!s.isGameRunning() && s.isStartingGame()){
            for (int i = 0; i < s.getClients().size(); i++){
                if (s.getClients().get(i).isConnected()){
                    try {
                        s.getClients().get(i).getWriter().write("//GameStarting//");
                        s.getClients().get(i).getWriter().newLine();
                        s.getClients().get(i).getWriter().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            s.setStartingGame(false);
            s.setGameRunning(true);
        }

        ArrayList<ArrayList<String>> listofBuildings = new ArrayList<>();
        ArrayList<ArrayList<String>> listofCharacters = new ArrayList<>();


        for (int i = 0; i < Data.getListofLists().size(); i++){
            if (Data.getListofLists().get(i).size() > 7){
                listofCharacters.add(Data.getListofLists().get(i));
            } else {
                listofBuildings.add(Data.getListofLists().get(i));
            }
        }

        for (int i = 0; i < s.getClients().size(); i++){
            try{
                if (s.getClients().get(i).isConnected()){
                    s.getClients().get(i).getWriter().write("//buildings"+listofBuildings.size()+"#");
                    for (int j = 0; j < listofBuildings.size(); j++){
                        s.getClients().get(i).getWriter().write("//"+listofBuildings.get(j).get(1)+
                                "+++"+listofBuildings.get(j).get(2)+
                                "+++"+listofBuildings.get(j).get(3)+
                                "+++"+listofBuildings.get(j).get(4)+
                                "+++"+listofBuildings.get(j).get(5)+
                                "+++"+listofBuildings.get(j).get(6)+"*");
                    }
                    s.getClients().get(i).getWriter().write("//characters"+listofCharacters.size()+"#");
                    for (int j = 0; j < listofCharacters.size(); j++){
                        s.getClients().get(i).getWriter().write("+++"+listofCharacters.get(j).get(1)+
                                "+++"+listofCharacters.get(j).get(2)+
                                "+++"+listofCharacters.get(j).get(3)+
                                "+++"+listofCharacters.get(j).get(4)+
                                "+++"+listofCharacters.get(j).get(5)+
                                "+++"+listofCharacters.get(j).get(6)+
                                "+++"+listofCharacters.get(j).get(7)+
                                "+++"+listofCharacters.get(j).get(8)+
                                "+++"+listofCharacters.get(i).get(9)+"*");
                    }
                    s.getClients().get(i).getWriter().write("//end");
                    s.getClients().get(i).getWriter().newLine();
                    s.getClients().get(i).getWriter().flush();
                }
            } catch (IOException e){
                if (e.toString().startsWith("java.net.SocketException: Connection reset")){
                    s.disconnect(i);
                } else {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void startTubeThread (int i, Server s) {
        tubes.add(new Thread(s.getClients().get(i).getTube()));
        tubes.get(tubes.size()-1).start();
    }
}