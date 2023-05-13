package asset.myexception;

import java.util.HashMap;

import com.oocourse.spec3.exceptions.GroupIdNotFoundException;

public class MyGroupIdNotFoundException extends GroupIdNotFoundException {
    private static int total = 0;
    private static HashMap<Integer, Integer> disperse = new HashMap<>();
    private String exception;

    public MyGroupIdNotFoundException(int id) {
        MyGroupIdNotFoundException.total++;
        Integer disperseInteger = MyGroupIdNotFoundException.disperse.get(id);
        if (disperseInteger == null) {
            disperseInteger = new Integer(1);
        } else {
            disperseInteger++;
        }
        disperse.put(id, disperseInteger);
        this.exception = String.format(
            "ginf-%d, %d-%d",
            MyGroupIdNotFoundException.total,
            id,
            disperseInteger
        );
    }

    @Override
    public void print() {
        System.out.println(this.exception);
    }
}