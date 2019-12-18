package data;

import java.util.ArrayList;

public class Data {
    private static ArrayList<ArrayList<String>> listofLists = new ArrayList<ArrayList<String>>(); //x, y, name ??;
    private static ArrayList<ArrayList<String>> projectiles = new ArrayList<>();
    private static int projectileCount = 0;

    public static ArrayList<ArrayList<String>> getListofLists() {
        return listofLists;
    }

    public static void setListofLists(ArrayList<ArrayList<String>> listofLists) {
        Data.listofLists = listofLists;
    }

    //TODO Timer funktionalität, den Angriff zwischen Einheiten verarbeiten
    public static void addData(String line, String clientName) {
        //Buildings
        int index = 0;
        index = line.indexOf("#");
        index += 3;

        int startIndex = 0;
        int i = 0;
        while (startIndex < line.indexOf("//characters")) {
            if ((startIndex = line.indexOf("*", startIndex + 1)) != -1) {
                i++;
            }
        }
        i--;
        String substring[] = new String[i];

        startIndex = 0;
        for (int j = 0; j < i; j++) {
            startIndex = line.indexOf("*", index + 1);
            substring[j] = line.substring(index, startIndex);
            index = startIndex + 3;
        }

        for (int k = 0; k < substring.length; k++) {
            int index1 = 0, index2 = 0;
            String owner, type, hp, name, x, y;
            owner = substring[k].substring(0, (index1 = substring[k].indexOf("+++")));
            index2 = substring[k].indexOf("+++", index1 + 1);
            index1 += 3;
            type = substring[k].substring(index1, index2);
            index1 = index2 + 3;
            index2 = substring[k].indexOf("+++", index1 + 1);
            hp = substring[k].substring(index1, index2);
            index1 = index2 + 3;
            index2 = substring[k].indexOf("+++", index1 + 1);
            name = substring[k].substring(index1, index2);
            index1 = index2 + 3;
            index2 = substring[k].indexOf("+++", index1 + 1);
            x = substring[k].substring(index1, index2);
            y = substring[k].substring(index2 + 3);

            if (name == null) {
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

            if (!doubling(list)) {
                if (!actualising(list)) {
                    listofLists.add(list);
                }
            }
        }

        //Character
        index = line.indexOf("#", index + 1);
        index += 4;
        startIndex = index + 1;

        i = 0;
        while (startIndex < line.indexOf("//projectiles") && startIndex > index) {
            if ((startIndex = line.indexOf("*", startIndex + 1)) != -1) {
                i++;
            }
        }
        substring = new String[i];

        for (int j = 0; j < i; j++) {
            startIndex = line.indexOf("*", index + 1);
            substring[j] = line.substring(index, startIndex);
            index = startIndex + 4;
        }

        for (int k = 0; k < substring.length; k++) {
            int index1 = 0, index2 = 0;
            String owner, type, hp, x, y, name, moveX, moveY, angle, canAttack;
            owner = substring[k].substring(0, (index1 = substring[k].indexOf("+++")));
            index2 = substring[k].indexOf("+++", index1 + 1);
            index1 += 3;
            type = substring[k].substring(index1, index2);
            index1 = index2 + 3;
            index2 = substring[k].indexOf("+++", index1 + 1);
            hp = substring[k].substring(index1, index2);
            index1 = index2 + 3;
            index2 = substring[k].indexOf("+++", index1 + 1);
            name = substring[k].substring(index1, index2);
            index1 = index2 + 3;
            index2 = substring[k].indexOf("+++", index1 + 1);
            x = substring[k].substring(index1, index2);
            index1 = index2 + 3;
            index2 = substring[k].indexOf("+++", index1 + 1);
            y = substring[k].substring(index1, index2);
            index1 = index2 + 3;
            index2 = substring[k].indexOf("+++", index1 + 1);
            moveX = substring[k].substring(index1, index2);
            index1 = index2 + 3;
            index2 = substring[k].indexOf("+++", index1 + 1);
            moveY = substring[k].substring(index1, index2);
            index1 = index2 + 3;
            index2 = substring[k].indexOf("+++", index1 + 1);
            angle = substring[k].substring(index1, index2);
            canAttack = substring[k].substring(index2 + 3);

            if (name.equals("null")) {
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

            if (!doubling(list)) {
                if (!actualising(list)) {
                    listofLists.add(list);
                }
            }
            //TODO ausgabe wegmachen
            /*
            if (!moveX.equals("null") || !moveY.equals("null")){
                System.out.println(moveX+"  lol   "+moveY);
            }
            */
        }
        /*
        //attacks
        index = line.indexOf("//attacks", 0);
        if (line.indexOf("#", index + 1) != -1) {
            //index += "//attacks".length();
            startIndex = index + 1;

            System.out.println("1 start: "+index+"    end: "+startIndex);

            i = 0;
            while (startIndex < line.indexOf("//end") && startIndex > index) {
                if ((startIndex = line.indexOf("#", startIndex + 1)) != -1) {
                    i++;
                }
            }
            System.out.println("2 start: "+index+"    end: "+startIndex);

            substring = new String[i];
            index += "//attacks".length();
            //TODO lol
            for (int j = 0; j < i; j++) {
                startIndex = line.indexOf("#", index + 1);
                System.out.println("3 start: "+index+"    end: "+startIndex);
                substring[j] = line.substring(index, startIndex);
                index = startIndex + 1;
            }

            for (int k = 0; k < substring.length; k++) {
                System.out.println(substring[k]);
                String attacker, defender;
                int index1;
                attacker = substring[k].    substring(0, (index1 = substring[k].indexOf("+++")));
                index1 += 3;
                defender = substring[k].substring(index1, substring[k].length() - 3);

                double startX = -1, startY = -1, targetX = -1, targetY = -1;
                for (int f = 0; f < Data.getListofLists().size(); f++) {
                    if (Data.getListofLists().get(f).get(4) == attacker && Data.getListofLists().get(f).get(4) == "character") {
                        startX = Double.parseDouble(Data.getListofLists().get(f).get(5)) + 32;
                        startY = Double.parseDouble(Data.getListofLists().get(f).get(6)) + 32;
                    } else if (Data.getListofLists().get(f).get(4) == defender && Data.getListofLists().get(f).get(4) == "character") {
                        targetX = Double.parseDouble(Data.getListofLists().get(f).get(5)) + 32;
                        targetY = Double.parseDouble(Data.getListofLists().get(f).get(6)) + 32;
                    }
                }
                if (startX != -1 && startY != -1 && targetX != -1 && targetY != -1) {
                    System.out.println("lol it kinda worked/ projectile will be created");
                    ArrayList<String> list = new ArrayList<>();
                    list.add(clientName);
                    list.add("p" + projectileCount); //name
                    projectileCount++;
                    list.add(Double.toString(startX));
                    list.add(Double.toString(startY));
                    list.add(Double.toString(targetX));
                    list.add(Double.toString(targetY));
                    list.add("false");
                    projectiles.add(list);
                }
            }
        }
        */
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

    private static boolean doubling(ArrayList<String> list) {
        boolean doubling = false;
        for (int i = 0; i < listofLists.size(); i++) {
            if (listofLists.get(i).equals(list)) {
                doubling = true;
            }
        }
        return doubling;
    }

    private static boolean actualising(ArrayList<String> list) {
        boolean actualising = false;
        for (int i = 0; i < listofLists.size(); i++) {
            if (listofLists.get(i).get(1).equals(list.get(1)) && listofLists.get(i).get(2).equals(list.get(2)) && !listofLists.get(i).equals(list) && listofLists.get(i).size() == list.size()) {
                if (listofLists.get(i).get(0).equals("character") && listofLists.get(i).get(4).equals(list.get(4))) {
                    listofLists.get(i).set(3, list.get(3));
                    listofLists.get(i).set(5, list.get(5));
                    listofLists.get(i).set(6, list.get(6));
                    listofLists.get(i).set(7, list.get(7));
                    listofLists.get(i).set(8, list.get(8));
                    actualising = true;
                } else if (listofLists.get(i).get(0).equals("building") && listofLists.get(i).get(4).equals(list.get(4))) {
                    listofLists.get(i).set(3, list.get(3));
                    listofLists.get(i).set(5, list.get(5));
                    listofLists.get(i).set(6, list.get(6));
                    actualising = true;
                }
            }
        }
        return actualising;
    }

    private static String generateName(String type) {
        int counter = 0;
        for (int i = 0; i < listofLists.size(); i++) {
            if (listofLists.get(i).get(3).startsWith(type)) {
                counter++;
            }
        }
        counter++;
        return type + counter;
    }

    public static ArrayList<ArrayList<String>> getProjectiles() {
        return projectiles;
    }

    public static void setProjectiles(ArrayList<ArrayList<String>> projectiles) {
        Data.projectiles = projectiles;
    }
}

