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
    private boolean connected = false, ready = false, pong = true, noResponse = false;
    private long lastPong = System.currentTimeMillis();

    public Client (Socket socket, Server s){
        this.socket = socket;
        this.s = s;
        //Tube tube = new Tube(s, this);
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
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

    public boolean isPong() {
        return pong;
    }

    public void setPong(boolean pong) {
        this.pong = pong;
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

    public long getLastPong() {
        return lastPong;
    }

    public void setLastPong(long lastPong) {
        this.lastPong = lastPong;
    }

    public boolean isNoResponse() {
        return noResponse;
    }

    public void setNoResponse(boolean noResponse) {
        this.noResponse = noResponse;
    }
}

