package connection;

import gui.Console;
import data.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    private final int port = 7777;
    private boolean running = true;
    private ServerSocket ss;
    private Socket socket = null;
    private InputStreamReader input;
    private BufferedReader reader;
    private Data data;
    private ArrayList<Client> clients = new ArrayList<>();

    @Override
    public void run() {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        data = new Data();

        while (running) {
            System.out.println("Searching for connection...");
            try {
                //Connecting
                int index = 0;
                clients.add(new Client(new Socket()));
                clients.get(clients.size()-1).setSocket(ss.accept());
                index = clients.size()-1;
                clients.get(index).setOutput(new OutputStreamWriter(clients.get(index).getOutputStream()));
                clients.get(index).setWriter(new PrintWriter(clients.get(index).getOutput()));

                clients.get(index).setInput(new InputStreamReader(clients.get(index).getInputStream()));
                clients.get(index).setReader(new BufferedReader(clients.get(index).getInput()));

                String name = clients.get(index).getReader().readLine();
                clients.get(index).setName(name);
                if (clients.get(index).getSocket() != null){
                    Console.addMessage(name+" has joined");
                }
                //TODO was machen bei gleichen Namen?
                data.getList().add(new ArrayList<String[]>());
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
}