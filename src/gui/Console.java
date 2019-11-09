package gui;

import javafx.beans.value.ObservableValue;
import sample.Main;

import java.util.ArrayList;

public class Console {
    private static ArrayList<String> messages = new ArrayList<>();

    public static void addMessage(String s){
        //mit Zeitstempel?
        messages.add(s);
        Main.g.updateConsole();
    }

    public static String printMessages(){
        StringBuilder printing = new StringBuilder();
        for (int i = 0; i < messages.size(); i++){
            System.out.println("size: "+messages.size()+"   "+i);
            printing.append(messages.get(i)).append("\n");
        }
        //System.out.println(printing.toString().length());
        return printing.toString();
    }
}
