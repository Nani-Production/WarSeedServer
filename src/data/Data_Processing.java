package data;

import java.util.ArrayList;

public class Data_Processing {

    public static double[] moveCharacter(ArrayList<String> list) {
        double newCoord[] = null;
        double speed = Double.parseDouble(UnitDatabank.getUnitInfo(Integer.parseInt(list.get(2)), "speed"));
        double someX, someY, moveX = -1, moveY = -1, nowX = -1, nowY = -1;
        boolean formatsucessfull = true, moving = true;

        //TODO dies und das

        //System.out.println("i'm in");

        try {
            nowX  = Double.parseDouble(list.get(5));
            nowY  = Double.parseDouble(list.get(6));
            if (!list.get(7).equals("null")){
                moveX = Double.parseDouble(list.get(7));
                System.out.println("7 worked");
            } else {
                moving = false;
            }
            if (!list.get(8).equals("null")){
                moveY = Double.parseDouble(list.get(8));
                System.out.println("8 worked");
            } else {
                moving = false;
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
            formatsucessfull = false;
        }

        if (formatsucessfull && moving){
            someX = moveX -nowX; //ZielpunktX - x
            someY = moveY -nowY; //ZielpunktY - y

            newCoord = new double[2];
            double v = speed / Math.sqrt(Math.pow(someX, 2) + Math.pow(someY, 2));
            newCoord[0] = v * someX;
            newCoord[1] = v * someY;


            if (list.get(5) != list.get(7) && list.get(6) != list.get(8)){
                System.out.println(list.get(4)+" is moving");
            }

            if (newCoord [0] == Double.parseDouble(list.get(5)) && newCoord[1] == Double.parseDouble(list.get(6))){
                list.set(7, "null");
                list.set(8, "null");
            } else {
                list.set(5, Double.toString(newCoord[0]));
                list.set(6, Double.toString(newCoord[1]));
            }
            return newCoord;
        }
        return null;
    }

    public static void updateProjectiles() {
        for (int i = 0; i < Data.getProjectiles().size(); i++) {
            if (Data.getProjectiles().get(i).get(6) == "true") {
                Data.getProjectiles().remove(i);
                i--;
            } else {
                boolean formatsucessfull = true;
                double someX, someY, moveX = -1, moveY = -1, nowX = -1, nowY = -1, speed = 1.;
                try {
                    nowX = Double.parseDouble(Data.getProjectiles().get(i).get(2));
                    nowY = Double.parseDouble(Data.getProjectiles().get(i).get(3));
                    moveX = Double.parseDouble(Data.getProjectiles().get(i).get(4));
                    moveY = Double.parseDouble(Data.getProjectiles().get(i).get(5));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    formatsucessfull = false;
                }

                if (formatsucessfull) {
                    someX = moveX - nowX; //ZielpunktX - x
                    someY = moveY - nowY; //ZielpunktY - y

                    double newCoord[] = new double[2];
                    double v = speed / Math.sqrt(Math.pow(someX, 2) + Math.pow(someY, 2));
                    newCoord[0] = v * someX;
                    newCoord[1] = v * someY;

                    if (newCoord[0] == nowX && newCoord[1] == nowY) {
                        nowX = -1;
                        nowY = -1;
                    } else {
                        Data.getProjectiles().get(i).set(2, Double.toString(newCoord[0]));
                        Data.getProjectiles().get(i).set(3, Double.toString(newCoord[1]));
                        boolean xFinished = false, yFinished = false;
                        if (moveX > nowX) {
                            if (newCoord[0] > moveX) {
                                xFinished = true;
                            }
                        } else {
                            if (newCoord[0] < moveX) {
                                xFinished = true;
                            }
                        }
                        if (moveY > nowY) {
                            if (newCoord[1] > moveY) {
                                yFinished = true;
                            }
                        } else {
                            if (newCoord[1] < moveY) {
                                yFinished = true;
                            }
                        }
                        if (xFinished && yFinished) {
                            Data.getProjectiles().get(i).set(6, "true");
                        }
                    }
                }
            }
        }
    }

        public static void dealDamage () {

        }

        public static boolean isDead (ArrayList < String > list) {
            if (Double.parseDouble(list.get(3)) <= 0) {
                return true;
            } else {
                return false;
            }
        }

        //soll in einem Thread aufgerufen werden bsp new Thread(new Runnable({@Override public run(){processCooldown()})}))
        public static boolean processCooldown ( long ms){
            Timer timer = new Timer(ms);
            while (true) {
                if (timer.process()) {
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