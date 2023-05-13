package asset.myexception;

import java.util.HashMap;

import com.oocourse.spec3.exceptions.EqualMessageIdException;

public class MyEqualMessageIdException extends EqualMessageIdException {
    private static int total = 0;
    private static HashMap<Integer, Integer> disperse = new HashMap<>();
    private String exception;

    public MyEqualMessageIdException(int id) {
        MyEqualMessageIdException.total++;
        Integer disperseInteger = MyEqualMessageIdException.disperse.get(id);
        if (disperseInteger == null) {
            disperseInteger = new Integer(1);
        } else {
            disperseInteger++;
        }
        disperse.put(id, disperseInteger);
        this.exception = String.format(
            "emi-%d, %d-%d",
            MyEqualMessageIdException.total,
            id,
            disperseInteger
        );
    }

    @Override
    public void print() {
        System.out.println(this.exception);
    }
}
