package data;

public class Timer {

    private long oldTime, newTime, cooldown;

    public Timer(long cooldown){
        this.cooldown = cooldown;
        oldTime = System.currentTimeMillis();
        newTime = -1;
    }

    public boolean process(){ //returns true wenn Timer abgelaufen ist
        newTime = System.currentTimeMillis();
        long dif = newTime - oldTime;
        if (dif > cooldown){
            return true;
        } else {
            return false;
        }
    }
}
