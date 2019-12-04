package connection;

import gui.Console;

import java.io.*;
import java.util.ArrayList;

public class Tube implements Runnable { //messages of clients wait in the tube until the server reads the messages
    private InputStreamReader input;
    private BufferedReader reader;
    private ArrayList<String> buffer = new ArrayList<>();
    private Server s;
    private Client c;

    public Tube(Server s, Client c) {
        this.s = s;
        this.c = c;
    }

    @Override
    public void run() {
        while (true){
            String line = null;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                if (e.toString().startsWith("java.net.SocketException: Connection reset")){
                    s.disconnect(c);
                } else {
                    e.printStackTrace();
                }
            } catch (NullPointerException ignore) {
                System.out.println(ignore.toString()+"  Tube Nullpointer");
            }
            if (line != null){
                if (line.startsWith("//buildings")){ //Datensatz vom Client
                    buffer.add(line);
                } else if (line.startsWith("//command")) { //Command for Server

                } else if (line.startsWith("//message")){ //Messsage for the chat

                } else if (line.startsWith("//Ready//")) {
                    c.setReady(true);
                    Console.addMessage(c.getName()+" is ready");
                } else if (line.startsWith("//notReady//")) {
                    c.setReady(false);
                    Console.addMessage(c.getName()+" is not ready");
                } else if (line.startsWith("pong")) {
                    c.setPong(true);
                }else {
                        System.out.println("Error line of client has no use");
                }
            }
        }
    }

    public InputStreamReader getInput() {
        return input;
    }

    public void setInput(InputStreamReader input) {
        this.input = input;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public ArrayList<String> getBuffer() {
        return buffer;
    }

    public void setBuffer(ArrayList<String> buffer) {
        this.buffer = buffer;
    }
}