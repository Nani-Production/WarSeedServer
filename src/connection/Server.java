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
}