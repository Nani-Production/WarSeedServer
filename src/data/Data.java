package data;

import java.util.ArrayList;

public class Data {
    private static ArrayList <ArrayList<String>> listofLists = new ArrayList <ArrayList<String>>(); //x, y, name ??;

    public static ArrayList<ArrayList<String>> getListofLists() {
        return listofLists;
    }

    public static void setListofLists(ArrayList<ArrayList<String>> listofLists) {
        Data.listofLists = listofLists;
    }

    //TODO Timer funktionalität, den Angriff zwischen Einheiten verarbeiten
    public static void addData(String line){
        //Buildings
        int index = 0;
        index = line.indexOf("#");
        index += 3;

        int startIndex = 0;
        int i = 0;
        while ( startIndex < line.indexOf("//characters")) {
            if ((startIndex = line.indexOf("*", startIndex+1)) != -1){
                i++;
            }
        }
        i--;
        String substring [] = new String [i];

        startIndex = 0;
        for (int j = 0; j < i; j++){
            startIndex = line.indexOf("*", index+1);
            substring [j] = line.substring(index, startIndex);
            index = startIndex+3;
        }

        for (int k = 0; k < substring.length; k++){
            int index1 = 0, index2 = 0;
            String owner, type, hp, name, x, y;
            owner = substring[k].substring(0, (index1 = substring[k].indexOf("+++")));
            index2 = substring[k].indexOf("+++", index1+1);
            index1 += 3;
            type = substring[k].substring(index1, index2);
            index1 = index2+3;
            index2 = substring[k].indexOf("+++", index1+1);
            hp = substring[k].substring(index1, index2);
            index1 = index2+3;
            index2 = substring[k].indexOf("+++", index1+1);
            name = substring[k].substring(index1, index2);
            index1 = index2+3;
            index2 = substring[k].indexOf("+++", index1+1);
            x = substring[k].substring(index1, index2);
            y = substring[k].substring(index2+3);

            if (name == null){
                name = generateName(type);
            }

            ArrayList<String> list = new ArrayList<>();
            list.add("building");
            list.add(owner);
            list.add(type);
            list.add(hp);
            list.add(name);
            list.add(x);
            list.add(y);

            if (!doubling(list)){
                if (!actualising(list)){
                    listofLists.add(list);
                }
            }
        }

        //Character
        index = line.indexOf("#", index+1);
        index += 4;
        startIndex = index+1;

        i = 0;
        while (startIndex < line.indexOf("//end") && startIndex > index) {
            if ((startIndex = line.indexOf("*", startIndex+1)) != -1){
                i++;
            }
        }
        substring = new String [i];

        for (int j = 0; j < i; j++){
            startIndex = line.indexOf("*", index+1);
            substring [j] = line.substring(index, startIndex);
            index = startIndex+4;
        }

        for (int k = 0; k < substring.length; k++){
            int index1 = 0, index2 = 0;
            String owner, type, hp, x, y, name, moveX, moveY, angle, canAttack;
            owner = substring[k].substring(0, (index1 = substring[k].indexOf("+++")));
            index2 = substring[k].indexOf("+++", index1+1);
            index1 += 3;
            type = substring[k].substring(index1, index2);
            index1 = index2+3;
            index2 = substring[k].indexOf("+++", index1+1);
            hp = substring[k].substring(index1, index2);
            index1 = index2+3;
            index2 = substring[k].indexOf("+++", index1+1);
            name = substring[k].substring(index1, index2);
            index1 = index2+3;
            index2 = substring[k].indexOf("+++", index1+1);
            x = substring[k].substring(index1, index2);
            index1 = index2+3;
            index2 = substring[k].indexOf("+++", index1+1);
            y = substring[k].substring(index1, index2);
            index1 = index2+3;
            index2 = substring[k].indexOf("+++", index1+1);
            moveX = substring[k].substring(index1, index2);
            index1 = index2+3;
            index2 = substring[k].indexOf("+++", index1+1);
            moveY = substring[k].substring(index1, index2);
            index1 = index2+3;
            index2 = substring[k].indexOf("+++", index1+1);
            angle = substring[k].substring(index1, index2);
            canAttack = substring[k].substring(index2+3);

            if (name == null){
                name = generateName(type);
            }

            ArrayList<String> list = new ArrayList<>();
            list.add("character");
            list.add(owner);
            list.add(type);
            list.add(hp);
            list.add(name);
            list.add(x);
            list.add(y);
            list.add(moveX);
            list.add(moveY);
            list.add(angle);
            list.add(canAttack);

            if (!doubling(list)){
                if (!actualising(list)){
                    listofLists.add(list);
                }
            }
        }

        /*
        final int[] cooldown = {5};
        ArrayList <Thread> cooldowns = new ArrayList<>();
        cooldowns.add(new Thread(new Runnable() {
            @Override
            public void run() {
                if (cooldown[0] != -1) {
                    if (Data_Processing.processCooldown(cooldown[0])){
                        cooldown[0] = -1;
                    }
                }
            }
        }));
        cooldowns.get(cooldowns.size()).start();
        */
    }

    private static boolean doubling (ArrayList<String> list) {
        boolean doubling = false;
        for (int i = 0; i < listofLists.size(); i++){
            if (listofLists.get(i).equals(list)){
                doubling = true;
            }
        }
        return doubling;
    }

    private static boolean actualising (ArrayList<String> list){
        boolean actualising = false;
        for (int i = 0; i < listofLists.size(); i++){
                if (listofLists.get(i).get(1).equals(list.get(1)) && listofLists.get(i).get(2).equals(list.get(2)) && !listofLists.get(i).equals(list) && listofLists.get(i).size() == list.size()){
                    if (listofLists.get(i).get(0).equals("character") && listofLists.get(i).get(4).equals(list.get(4))){
                        listofLists.get(i).set(3, list.get(3));
                        listofLists.get(i).set(5, list.get(5));
                        listofLists.get(i).set(6, list.get(6));
                        listofLists.get(i).set(7, list.get(7));
                        listofLists.get(i).set(8, list.get(8));
                        actualising = true;
                    } else if (listofLists.get(i).get(0).equals("building") && listofLists.get(i).get(4).equals(list.get(4))){
                        listofLists.get(i).set(3, list.get(3));
                        listofLists.get(i).set(5, list.get(5));
                        listofLists.get(i).set(6, list.get(6));
                        actualising = true;
                    }
                }
            }
        return actualising;
    }

    private static String generateName(String type){
        int counter = 0;
        for (int i = 0; i < listofLists.size(); i++){
            if (listofLists.get(i).get(3).startsWith(type)){
                counter++;
            }
        }
        counter++;
        return type+counter;
    }
}

