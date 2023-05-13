package asset.myexception;

import java.util.HashMap;

import com.oocourse.spec3.exceptions.PersonIdNotFoundException;

public class MyPersonIdNotFoundException extends PersonIdNotFoundException {
    private static int total = 0;
    private static HashMap<Integer, Integer> disperse = new HashMap<>();
    private String exception;

    public MyPersonIdNotFoundException(int id) {
        MyPersonIdNotFoundException.total++;
        Integer disperseInteger = MyPersonIdNotFoundException.disperse.get(id);
        if (disperseInteger == null) {
            disperseInteger = new Integer(1);
        } else {
            disperseInteger++;
        }
        disperse.put(id, disperseInteger);
        this.exception = String.format(
            "pinf-%d, %d-%d",
            MyPersonIdNotFoundException.total,
            id,
            disperseInteger
        );
    }

    @Override
    public void print() {
        System.out.println(this.exception);
    }
}
