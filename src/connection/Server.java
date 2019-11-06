package connection;

import information.Data;

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
                socket = ss.accept();
                System.out.println("Client connected");
                input = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(input);
                String name = reader.readLine();
                clients.add(new Client(socket, name));
                System.out.println(clients.get(0).getName());
                data.getList().add(new ArrayList<String[]>());

                socket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }

            System.out.println("Socket closed");
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
