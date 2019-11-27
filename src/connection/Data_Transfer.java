package connection;

import data.Data;
import sample.Main;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;

public class Data_Transfer implements Runnable {
    boolean running = true;
    private Server s = null;
    private Data data;
    private static ArrayList<Thread> tubes = new ArrayList<>();

    public Data_Transfer(Server s, Data d){
        this.s = s;
    }

    @Override
    public void run() {
        while (true){
            //TODO die Daten sende, verarbeiten und schicken
            if (s.getClients().size() > 0){
                //System.out.println("size: "+s.getClients().size());
                if (receiveData()){
                    processData();
                    sendData();
                }
            }
        }
    }

    private void processData() {

    }

    private boolean receiveData (){
        boolean received = false;
        for (int i = 0; i < s.getClients().size(); i++){
            if (s.getClients().get(i).isConnected()){
                //System.out.println("name: "+s.getClients().get(i).getName());
                String message;
                try {
                    //message = s.getClients().get(i).getTube().getReader().readLine();
                    //System.out.println("message "+message);
                    //System.out.println("buffer"+i+": "+s.getClients().get(i).getTube().getBuffer().size());
                    for (int j = 0; j < s.getClients().get(i).getTube().getBuffer().size(); j++){
                        //System.out.println(s.getClients().get(i).getTube().getBuffer().get(j));
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
                        s.getClients().get(i).getWriter().write("//"+listofBuildings.get(j).get(0)+
                                "+++"+listofBuildings.get(j).get(1)+
                                "+++"+listofBuildings.get(j).get(2)+
                                "+++"+listofBuildings.get(j).get(3)+
                                "+++"+listofBuildings.get(j).get(4)+"*");
                    }
                    s.getClients().get(i).getWriter().write("//characters"+listofCharacters.size()+"#");
                    for (int j = 0; j < listofCharacters.size(); j++){
                        s.getClients().get(i).getWriter().write("+++"+listofCharacters.get(j).get(0)+
                                "+++"+listofCharacters.get(j).get(1)+
                                "+++"+listofCharacters.get(j).get(2)+
                                "+++"+listofCharacters.get(j).get(3)+
                                "+++"+listofCharacters.get(j).get(4)+
                                "+++"+listofCharacters.get(j).get(5)+
                                "+++"+listofCharacters.get(j).get(6)+
                                "+++"+listofCharacters.get(j).get(7)+"*");
                    }
                    s.getClients().get(i).getWriter().write("//end");
                    s.getClients().get(i).getWriter().newLine();
                    s.getClients().get(i).getWriter().flush();
                }
            } catch (IOException e){
                if (e.toString().equals("java.net.SocketException: Connection reset by peer: socket write error")){
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