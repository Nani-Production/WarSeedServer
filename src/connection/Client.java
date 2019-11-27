package connection;

import java.io.*;
import java.net.Socket;

public class Client {
    private Server s;
    private Socket socket;
    private String name;
    private OutputStreamWriter output;
    private BufferedWriter writer;
    private Tube tube = new Tube(s, this);
    private boolean connected = false;

    public Client (Socket socket, Server s){
        this.socket = socket;
        this.s = s;
    }

    public Tube getTube() {
        return tube;
    }

    public void setTube(Tube tube) {
        this.tube = tube;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public OutputStreamWriter getOutput() {
        return output;
    }

    public void setOutput(OutputStreamWriter output) {
        this.output = output;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public InputStream getInputStream() {
        try {
            return socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public OutputStream getOutputStream() {
        try {
            return socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

