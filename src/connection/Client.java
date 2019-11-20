package connection;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private String name;
    private InputStreamReader input;
    private OutputStreamWriter output;
    private PrintWriter writer;
    private BufferedReader reader;

    public Client (Socket socket){ //, String name
        this.socket = socket;
        //this.name = name;
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

    public PrintWriter getWriter() {
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
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

