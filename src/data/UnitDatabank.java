package data;

public class UnitDatabank {
    public static final int SPEEDER = 0, DAMAGEDEALER = 1, TANK = 2, SETTLER = 3, NEXUS = 4, VILLAGE = 5;

    public static String getUnitInfo(int type, String property){
        String value = null;
        switch (type){
            case SPEEDER:
                if (property.equalsIgnoreCase("speed")){
                    value = "15";
                } else if (property.equalsIgnoreCase("range")){
                    value = "100";
                } else if (property.equalsIgnoreCase("attack")){
                    value = "5";
                } else if (property.equalsIgnoreCase("cooldown")){
                    value = "200";
                } else if (property.equalsIgnoreCase("maxHealth")){
                    value = "100";
                }
                break;

            case DAMAGEDEALER:
                if (property.equalsIgnoreCase("speed")){
                    value = "10";
                } else if (property.equalsIgnoreCase("range")){
                    value = "150";
                } else if (property.equalsIgnoreCase("attack")){
                    value = "7";
                } else if (property.equalsIgnoreCase("cooldown")){
                    value = "150";
                } else if (property.equalsIgnoreCase("maxHealth")){
                    value = "120";
                }
                break;
            case TANK:
                if (property.equalsIgnoreCase("speed")){
                    value = "5";
                } else if (property.equalsIgnoreCase("range")){
                    value = "100";
                } else if (property.equalsIgnoreCase("attack")){
                    value = "4";
                } else if (property.equalsIgnoreCase("cooldown")){
                    value = "350";
                } else if (property.equalsIgnoreCase("maxHealth")){
                    value = "300";
                }
                break;
            case SETTLER:
                if (property.equalsIgnoreCase("speed")){
                    value = "15";
                } else if (property.equalsIgnoreCase("range")){
                    value = "50";
                } else if (property.equalsIgnoreCase("attack")){
                    value = "2";
                } else if (property.equalsIgnoreCase("cooldown")){
                    value = "250";
                } else if (property.equalsIgnoreCase("maxHealth")){
                    value = "50";
                }
                break;
            case NEXUS:
                if (property.equalsIgnoreCase("maxHealth")){
                value = "1000";
                }
                break;
            case VILLAGE:
            if (property.equalsIgnoreCase("maxHealth")){
                value = "100";
            }
                break;
        }
        return value;
    }
}