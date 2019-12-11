package data;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {
    private final int TEX_GRASS = 0, TEX_FOREST = 1, TEX_WATER_BRIGHT = 2, TEX_WATER_DARK = 3;
    private int [][] textures;
    private int mapWidth, mapHeight;
    private ArrayList<Ressource> ressources = new ArrayList<>();

    public void generateMap(){
        mapWidth = 20;
        mapHeight = 11;
        textures = new int[mapWidth][mapHeight];

        for (int i = 0; i < mapHeight; i++){
            for (int j = 0; j < mapWidth; j++){
                textures[i][j] = (int) (Math.random()*3);
            }
        }
    }

    private void generateRessources(){

    }

    private void putSpawns(){

    }

    public double getMapWidth() {
        return mapWidth*200;
    }

    public double getMapHeight() {
        return mapHeight*200;
    }
}