package information;

import java.util.ArrayList;

public class Data {
    private ArrayList <ArrayList<String[]>> list = new ArrayList<ArrayList<String[]>>(); //name, x, y
    //private ArrayList <ArrayList<String>> listofLists = new ArrayList <ArrayList<String>>(); //x, y, name ??;

    public ArrayList <ArrayList<String[]>> getList(){
        return list;
    }

    public void setList (ArrayList <ArrayList<String[]>> list){
        this.list = list;
    }
}

