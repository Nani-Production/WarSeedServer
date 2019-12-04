package data;

import java.util.ArrayList;

public class Data_Processing {

    public static double[] moveCharacter (ArrayList<String> list){
        double newCoord [] = null;
        if (list.get(7) == null || list.get(8) == null){
            double someX = Double.parseDouble(list.get(7)) -Double.parseDouble(list.get(5)); //ZielpunktX - x
            double someY = Double.parseDouble(list.get(8)) -Double.parseDouble(list.get(6)); //ZielpunktY - y

            //TODO Einheitendatenbank? Damit man die Geschwindigkeit ziehen kann
            double speed = 2; //?

            newCoord = new double[2];
            double v = speed / Math.sqrt(Math.pow(someX, 2) + Math.pow(someY, 2));
            newCoord[0] = v * someX;
            newCoord[1] = v * someY;

            if (newCoord [0] == Double.parseDouble(list.get(5)) && newCoord[1] == Double.parseDouble(list.get(6))){
                list.set(7, null);
                list.set(8, null);
            }
        }
        return newCoord;
    }

    public static void dealDamage (){

    }

    public static boolean isDead(ArrayList<String> list){
        if (Double.parseDouble(list.get(3)) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    //soll in einem Thread aufgerufen werden bsp new Thread(new Runnable({@Override public run(){processCooldown()})}))
    public static boolean processCooldown (long ms){
        Timer timer = new Timer(ms);
        while (true){
            if (timer.process()){
                return true;
            }
        }
    }
}

/*
list.add("building");
            list.add(owner);
            list.add(type);
            list.add(hp);
            list.add(name);
            list.add(x);
            list.add(y);

list.add("character");
        list.add(owner);
        list.add(type);
        list.add(hp);
        list.add(name);
        list.add(x);
        list.add(y);
        list.add(moveX);
        list.add(moveY);
 */