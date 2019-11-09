package connection;

import information.Data;

import java.io.*;

public class Data_Transfer implements Runnable {
    boolean running = true;
    private Server s = null;
    private Data data;
    private InputStreamReader input;
    private OutputStreamWriter output;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Data_Transfer(Server s, Data d){
        this.s = s;
    }

    @Override
    public void run() {
        while (running){
            String message [] = new String [s.getClients().size()];
            //Input
            for (int i = 0; i < s.getClients().size(); i++){

                input = new InputStreamReader(s.getClients().get(i).getInputStream());
                reader = new BufferedReader(input);
                try {
                    message[i] = reader.readLine();

                    System.out.println(message[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Output
            for (int i = 0; i < s.getClients().size(); i++){
                output = new OutputStreamWriter(s.getClients().get(i).getOutputStream());
                writer = new BufferedWriter(output);
                for (int j = 0; j < message.length; j++){
                    /*
                    try {
                        writer.write(message[j]);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    */
                }
            }
        }
    }
}
