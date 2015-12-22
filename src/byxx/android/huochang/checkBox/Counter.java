package byxx.android.huochang.checkBox;

/**
 * Created by wgary on 15/9/30.
 */
public class Counter {
    private static Counter counter = null;
    private int count = 0;

    public static Counter getCounter() {
        if(counter == null){
            counter = new Counter();
        }
        return counter;
    }

    public boolean getCount() {
        count++;
        if(count < 3){
            return true;
        }else{
            count = 0;
            return false;
        }

    }
}
