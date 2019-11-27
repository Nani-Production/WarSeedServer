package data;

import java.util.ArrayList;

public class Data_Processing {

    public double[] moveCharacter (ArrayList<String> list){
        double someX = Double.parseDouble(list.get(6)) -Double.parseDouble(list.get(4));
        double someY = Double.parseDouble(list.get(7)) -Double.parseDouble(list.get(5));

        //TODO Einheitendatenbank? Damit man die Geschwindigkeit ziehen kann
        double speed = 2; //?

        double newCoord [] = new double[2];
        double v = speed / Math.sqrt(Math.pow(someX, 2) + Math.pow(someY, 2));
        newCoord[0] = v * someX;
        newCoord[1] = v * someY;

        return newCoord;
    }

    public void dealDamage (){

    }

    public boolean isDead(ArrayList<String> list){
        if (Double.parseDouble(list.get(2)) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    //soll in einem Thread aufgerufen werden bsp new Thread(new Runnable({@Override public run(){processCooldown()})}))
    public boolean processCooldown (long ms){
        Timer timer = new Timer(ms);
        while (true){
            if (timer.process()){
                return true;
            }
        }
    }
}