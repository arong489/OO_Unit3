package asset.myexception;

import java.util.HashMap;

import com.oocourse.spec3.exceptions.RelationNotFoundException;

public class MyRelationNotFoundException extends RelationNotFoundException {
    private static int total = 0;
    private static HashMap<Integer, Integer> disperse = new HashMap<>();
    private String exception;

    public MyRelationNotFoundException(int id1, int id2) {
        MyRelationNotFoundException.total++;
        Integer disperseInteger1 = MyRelationNotFoundException.disperse.get(id1);
        Integer disperseInteger2 = MyRelationNotFoundException.disperse.get(id2);
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
        
        if (id1 < id2) {
            this.exception = String.format(
                "rnf-%d, %d-%d, %d-%d",
                MyRelationNotFoundException.total,
                id1,
                disperseInteger1,
                id2,
                disperseInteger2
            );
        } else {
            this.exception = String.format(
                "rnf-%d, %d-%d, %d-%d",
                MyRelationNotFoundException.total,
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
