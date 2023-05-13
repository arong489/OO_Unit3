package asset.myexception;

import java.util.HashMap;

import com.oocourse.spec3.exceptions.PathNotFoundException;

public class MyPathNotFoundException extends PathNotFoundException {
    private static int total = 0;
    private static HashMap<Integer, Integer> disperse = new HashMap<>();
    private String exception;

    public MyPathNotFoundException(int id) {
        MyPathNotFoundException.total++;
        Integer disperseInteger = MyPathNotFoundException.disperse.get(id);
        if (disperseInteger == null) {
            disperseInteger = new Integer(1);
        } else {
            disperseInteger++;
        }
        disperse.put(id, disperseInteger);
        this.exception = String.format(
                "pnf-%d, %d-%d",
                MyPathNotFoundException.total,
                id,
                disperseInteger);
    }

    @Override
    public void print() {
        System.out.println(this.exception);
    }
}