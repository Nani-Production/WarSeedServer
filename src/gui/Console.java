package gui;

import javafx.beans.value.ObservableValue;
import sample.Main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Console {
    private static ArrayList<String> messages = new ArrayList<>();
    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    public static void addMessage(String s){
        //TODO mit Zeitstempel?
        messages.add("["+format.format(Calendar.getInstance().getTime())+"] "+s);
        Main.g.updateConsole();
    }

    public static String printMessages(){
        StringBuilder printing = new StringBuilder();
        for (int i = 0; i < messages.size(); i++){
            printing.append(messages.get(i)).append("\n");
        }
        //System.out.println(printing.toString().length());
        return printing.toString();
    }
}
