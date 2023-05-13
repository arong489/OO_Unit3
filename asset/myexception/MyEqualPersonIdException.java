package asset.myexception;

import java.util.HashMap;

import com.oocourse.spec3.exceptions.EqualPersonIdException;

public class MyEqualPersonIdException extends EqualPersonIdException {
    private static int total = 0;
    private static HashMap<Integer, Integer> disperse = new HashMap<>();
    private String exception;

    public MyEqualPersonIdException(int id) {
        MyEqualPersonIdException.total++;
        Integer disperseInteger = MyEqualPersonIdException.disperse.get(id);
        if (disperseInteger == null) {
            disperseInteger = new Integer(1);
        } else {
            disperseInteger++;
        }
        disperse.put(id, disperseInteger);
        this.exception = String.format(
            "epi-%d, %d-%d",
            MyEqualPersonIdException.total,
            id,
            disperseInteger
        );
    }

    @Override
    public void print() {
        System.out.println(this.exception);
    }
}
