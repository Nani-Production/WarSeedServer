package connection;

import information.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    private final int port = 5000;
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
                data.getList().add(new ArrayList<String[]>());

                //Input
                //input = new InputStreamReader(client.getInputStream());
                //reader = new BufferedReader(input);
                //Output
                //output = new OutputStreamWriter(client.getOutputStream());
                //writer = new BufferedWriter(output);



                //receive data
                //String message = reader.readLine();
                //receive position
                //send data
                //send positon

                socket.close();
            } catch(IOException e) {
                System.out.println(e);
            }

            System.out.println("disconnected");
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
