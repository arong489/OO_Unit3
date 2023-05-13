package asset.myexception;

import java.util.HashMap;

import com.oocourse.spec3.exceptions.MessageIdNotFoundException;

public class MyMessageIdNotFoundException extends MessageIdNotFoundException {
    private static int total = 0;
    private static HashMap<Integer, Integer> disperse = new HashMap<>();
    private String exception;

    public MyMessageIdNotFoundException(int id) {
        MyMessageIdNotFoundException.total++;
        Integer disperseInteger = MyMessageIdNotFoundException.disperse.get(id);
        if (disperseInteger == null) {
            disperseInteger = new Integer(1);
        } else {
            disperseInteger++;
        }
        disperse.put(id, disperseInteger);
        this.exception = String.format(
            "minf-%d, %d-%d",
            MyMessageIdNotFoundException.total,
            id,
            disperseInteger
        );
    }

    @Override
    public void print() {
        System.out.println(this.exception);
    }
}
