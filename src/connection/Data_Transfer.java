package connection;

import data.Data;
import data.Data_Processing;
import gui.Console;
import sample.Main;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Data_Transfer implements Runnable {
    private Server s;
    private static ArrayList<Thread> tubes = new ArrayList<>();

    public Data_Transfer(Server s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            FileWriter fileWriter = new FileWriter("log.txt");
            fileWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Main.info.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (s.getClients().size() > 0) {
                if (receiveData()){
                    processData();
                }
                sendData();

                for (int i = 0; i < s.getClients().size(); i++) {
                    if (System.currentTimeMillis() - s.getClients().get(i).getLastPong() > 20000 && s.getClients().get(i).isConnected() && !s.getClients().get(i).isNoResponse()) {
                        //TODO Disconnect this client
                        Console.addMessage("Client " + s.getClients().get(i).getName() + " is not responding");
                        s.getClients().get(i).setNoResponse(true);
                    } else if (System.currentTimeMillis() - s.getClients().get(i).getLastPong() < 20000 && s.getClients().get(i).isConnected() && s.getClients().get(i).isNoResponse()) {
                        Console.addMessage("Client " + s.getClients().get(i).getName() + " is responding again");
                        s.getClients().get(i).setNoResponse(false);
                    }
                }

                if (!s.isGameRunning() && !s.isStartingGame()) {
                    boolean allReady = true;
                    int counter = 0;

                    for (int i = 0; i < s.getClients().size() - 1; i++) {
                        if (!s.getClients().get(i).isReady() && s.getClients().get(i).isConnected()) {
                            allReady = false;
                        }

                        if (s.getClients().get(i).isConnected()) {
                            counter++;
                        }
                    }
                    if (counter <= 0) {
                        allReady = false;
                    }
                    if (allReady) {
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
        for (int i = 0; i < Data.getListofLists().size(); i++) {
            if (Data.getListofLists().get(i).get(0).equals("character")) {
                Data_Processing.moveCharacter(Data.getListofLists().get(i));
                //TODO check for collision
            }
        }
        Data_Processing.updateProjectiles();

        //fighting


        //cooldown
    }

    private boolean receiveData() {
        boolean received = false;
        for (int i = 0; i < s.getClients().size(); i++) {
            if (s.getClients().get(i).isConnected()) {
                try {
                    int index = -1;
                    long newestTime = 0;
                    for (int j = 0; j < s.getClients().get(i).getTube().getBuffer().size(); j++){
                        String string = null;
                        try {
                            string = s.getClients().get(i).getTube().getBuffer().get(j).substring("//data".length(), s.getClients().get(i).getTube().getBuffer().get(j).indexOf("//buildings"));
                            if (Long.parseLong(string) >= newestTime){
                                newestTime = Long.parseLong(string);
                                index = j;
                            }
                        } catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                    if (index > -1){
                        Data.addData(s.getClients().get(i).getTube().getBuffer().get(index), s.getClients().get(i).getName());
                        received = true;
                    }
                    s.getClients().get(i).getTube().getBuffer().clear();
                    return received;
                } catch (NullPointerException ignore) {
                    ignore.printStackTrace();
                }
            }
        }
        return received;
    }

    private void sendData() {
        for (int i = 0; i < s.getClients().size(); i++) {
            long currentPong = System.currentTimeMillis();
            if (s.getClients().get(i).isPong() && currentPong - s.getClients().get(i).getLastPong() > 1000 && s.getClients().get(i).isConnected()) {
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


        if (!s.isGameRunning() && s.isStartingGame()) {
            //TODO Map generieren
            for (int i = 0; i < s.getClients().size(); i++) {
                if (s.getClients().get(i).isConnected()) {
                    try {
                        s.getClients().get(i).getWriter().write("//GameStarting");
                        //TODO hier die Map schicken
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


        for (int i = 0; i < Data.getListofLists().size(); i++) {
            if (Data.getListofLists().get(i).get(0).equals("character")) {
                listofCharacters.add(Data.getListofLists().get(i));
            } else {
                listofBuildings.add(Data.getListofLists().get(i));
            }
        }

        /*
        for (int i = 0; i < s.getClients().size(); i++) {
            try {
                if (s.getClients().get(i).isConnected()) {
                    s.getClients().get(i).getWriter().write("//buildings" + listofBuildings.size() + "#");
                    for (int j = 0; j < listofBuildings.size(); j++) {
                        s.getClients().get(i).getWriter().write("//" + listofBuildings.get(j).get(1) +
                                "+++" + listofBuildings.get(j).get(2) +
                                "+++" + listofBuildings.get(j).get(3) +
                                "+++" + listofBuildings.get(j).get(4) +
                                "+++" + listofBuildings.get(j).get(5) +
                                "+++" + listofBuildings.get(j).get(6) + "*");
                    }
                    s.getClients().get(i).getWriter().write("//characters" + listofCharacters.size() + "#");
                    for (int j = 0; j < listofCharacters.size(); j++) {
                        s.getClients().get(i).getWriter().write("+++" + listofCharacters.get(j).get(1) +
                                "+++" + listofCharacters.get(j).get(2) +
                                "+++" + listofCharacters.get(j).get(3) +
                                "+++" + listofCharacters.get(j).get(4) +
                                "+++" + listofCharacters.get(j).get(5) +
                                "+++" + listofCharacters.get(j).get(6) +
                                "+++" + listofCharacters.get(j).get(7) +
                                "+++" + listofCharacters.get(j).get(8) +
                                "+++" + listofCharacters.get(j).get(9) +
                                "+++" + listofCharacters.get(j).get(10) + "*");
                    }
                    s.getClients().get(i).getWriter().write("//projectiles" + Data.getProjectiles().size() + "#");
                    for (int j = 0; j < Data.getProjectiles().size(); j++) {
                        s.getClients().get(i).getWriter().write("+++" + Data.getProjectiles().get(j).get(1) +
                                "+++" + Data.getProjectiles().get(j).get(2) +
                                "+++" + Data.getProjectiles().get(j).get(3) +
                                "+++" + Data.getProjectiles().get(j).get(4) +
                                "+++" + Data.getProjectiles().get(j).get(5) + "*");
                    }
                    s.getClients().get(i).getWriter().write("//end");
                    s.getClients().get(i).getWriter().newLine();
                    s.getClients().get(i).getWriter().flush();
                }
            } catch (IOException e) {
                if (e.toString().startsWith("java.net.SocketException: Connection reset")) {
                    s.disconnect(i);
                } else {
                    e.printStackTrace();
                }
            }
        }

         */

        //TestCode
        StringBuilder line = new StringBuilder();
        line.append("//data");
        line.append(System.currentTimeMillis());
        line.append("//buildings" + listofBuildings.size() + "#");
        for (int j = 0; j < listofBuildings.size(); j++) {
            line.append("//" + listofBuildings.get(j).get(1) +
                    "+++" + listofBuildings.get(j).get(2) +
                    "+++" + listofBuildings.get(j).get(3) +
                    "+++" + listofBuildings.get(j).get(4) +
                    "+++" + listofBuildings.get(j).get(5) +
                    "+++" + listofBuildings.get(j).get(6) + "*");
        }
        line.append("//characters" + listofCharacters.size() + "#");
        for (int j = 0; j < listofCharacters.size(); j++) {
            line.append("+++" + listofCharacters.get(j).get(1) +
                    "+++" + listofCharacters.get(j).get(2) +
                    "+++" + listofCharacters.get(j).get(3) +
                    "+++" + listofCharacters.get(j).get(4) +
                    "+++" + listofCharacters.get(j).get(5) +
                    "+++" + listofCharacters.get(j).get(6) +
                    "+++" + listofCharacters.get(j).get(7) +
                    "+++" + listofCharacters.get(j).get(8) +
                    "+++" + listofCharacters.get(j).get(9) +
                    "+++" + listofCharacters.get(j).get(10) + "*");
        }
        line.append("//projectiles" + Data.getProjectiles().size() + "#");
        for (int j = 0; j < Data.getProjectiles().size(); j++) {
            line.append("+++" + Data.getProjectiles().get(j).get(0) +
                    "+++" + Data.getProjectiles().get(j).get(1) +
                    "+++" + Data.getProjectiles().get(j).get(2) +
                    "+++" + Data.getProjectiles().get(j).get(3) +
                    "+++" + Data.getProjectiles().get(j).get(4) +
                    "+++" + Data.getProjectiles().get(j).get(5) +
                    "+++" + Data.getProjectiles().get(j).get(6) +"*");
        }
        line.append("//end\n");
        writeFile(line.toString());
        //TestCode

        for (int i = 0; i < s.getClients().size(); i++) {
            try {
                if (s.getClients().get(i).isConnected()) {
                    s.getClients().get(i).getWriter().write(line.toString());
                }
            } catch (IOException e) {
                if (e.toString().startsWith("java.net.SocketException: Connection reset")) {
                    s.disconnect(i);
                } else {
                    e.printStackTrace();
                }
            }
        }
    }

        private void writeFile (String output){
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            String outputWithNewLine = output + System.getProperty("line.separator");

            try {
                fileWriter = new FileWriter("log.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(outputWithNewLine);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert bufferedWriter != null;
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        public static void startTubeThread ( int i, Server s){
            tubes.add(new Thread(s.getClients().get(i).getTube()));
            tubes.get(tubes.size() - 1).start();
        }
    }