package asset.myexception;

import java.util.HashMap;

import com.oocourse.spec3.exceptions.EqualRelationException;

public class MyEqualRelationException extends EqualRelationException {
    private static int total = 0;
    private static HashMap<Integer, Integer> disperse = new HashMap<>();
    private String exception;

    public MyEqualRelationException(int id1, int id2) {
        MyEqualRelationException.total++;
        Integer disperseInteger1;
        Integer disperseInteger2;
        if (id1 == id2) {
            disperseInteger1 = MyEqualRelationException.disperse.get(id1);
            if (disperseInteger1 == null) {
                disperseInteger1 = new Integer(1);
            } else {
                disperseInteger1++;
            }
            disperse.put(id1, disperseInteger1);
            disperseInteger2 = disperseInteger1;
        } else {
            disperseInteger1 = MyEqualRelationException.disperse.get(id1);
            disperseInteger2 = MyEqualRelationException.disperse.get(id2);
            if (disperseInteger1 == null) {
                disperseInteger1 = new Integer(1);
            } else {
                disperseInteger1++;
            }
            disperse.put(id1, disperseInteger1);
            if (disperseInteger2 == null) {
                disperseInteger2 = new Integer(1);
            } else {
                disperseInteger2++;
            }
            disperse.put(id2, disperseInteger2);
        }

        if (id1 < id2) {
            this.exception = String.format(
                "er-%d, %d-%d, %d-%d",
                MyEqualRelationException.total,
                id1,
                disperseInteger1,
                id2,
                disperseInteger2
            );
        } else {
            this.exception = String.format(
                "er-%d, %d-%d, %d-%d",
                MyEqualRelationException.total,
                id2,
                disperseInteger2,
                id1,
                disperseInteger1
            );
        }
    }
    
    @Override
    public void print() {
        System.out.println(String.format(this.exception));
    }
}
