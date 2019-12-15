package connection;

import gui.Console;
import data.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

    //TODO TLS Handshake nachschauen für TCP/IP
public class Server implements Runnable{
    private final int port = 7777;
    private boolean running = true, gameRunning = false, startingGame = false;
    private ServerSocket ss;
    private ArrayList<Client> clients = new ArrayList<>();

    @Override
    public void run() {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Console.addMessage("Server is started");

        while (running && !gameRunning) {
            System.out.println("Searching for connection...");
            try {
                //Connecting
                int index = 0;
                clients.add(new Client(new Socket(), this));
                clients.get(clients.size()-1).setSocket(ss.accept());
                index = clients.size()-1;
                clients.get(index).setOutput(new OutputStreamWriter(clients.get(index).getOutputStream()));
                clients.get(index).setWriter(new BufferedWriter(clients.get(index).getOutput()));

                clients.get(index).getTube().setInput(new InputStreamReader(clients.get(index).getInputStream()));
                clients.get(index).getTube().setReader(new BufferedReader(clients.get(index).getTube().getInput()));

                String name = clients.get(index).getTube().getReader().readLine();
                clients.get(index).setName(name);
                System.out.println("name im Server: "+clients.get(index).getName());
                if (clients.get(index).getSocket() != null){
                    Console.addMessage(name+" has joined");
                    Data_Transfer.startTubeThread(index, this);
                    clients.get(index).setConnected(true);
                }
                //TODO was machen bei gleichen Namen?
            } catch(IOException e) {
                e.printStackTrace();
            }
        } // end of while
        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Client> getClients (){
        return clients;
    }

    public void disconnect (int i){
        System.out.println("User "+clients.get(i).getName()+" disconnected");
        clients.get(i).setConnected(false);
        clients.remove(i);
    }

    public void disconnect (Client c){
        int index = clients.indexOf(c);
        System.out.println("User "+clients.get(index).getName()+" disconnected");
        clients.get(index).setConnected(false);
        clients.remove(c);
    }

        public boolean isGameRunning() {
            return gameRunning;
        }

        public void setGameRunning(boolean gameRunning) {
            this.gameRunning = gameRunning;
        }

        public boolean isStartingGame() {
            return startingGame;
        }

        public void setStartingGame(boolean startingGame) {
            this.startingGame = startingGame;
        }
    }