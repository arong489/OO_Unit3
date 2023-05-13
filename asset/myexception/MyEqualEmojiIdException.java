package asset.myexception;

import java.util.HashMap;

import com.oocourse.spec3.exceptions.EqualEmojiIdException;

public class MyEqualEmojiIdException extends EqualEmojiIdException {
    private static int total = 0;
    private static HashMap<Integer, Integer> disperse = new HashMap<>();
    private String exception;

    public MyEqualEmojiIdException(int id) {
        MyEqualEmojiIdException.total++;
        Integer disperseInteger = MyEqualEmojiIdException.disperse.get(id);
        if (disperseInteger == null) {
            disperseInteger = new Integer(1);
        } else {
            disperseInteger++;
        }
        disperse.put(id, disperseInteger);
        this.exception = String.format(
                "eei-%d, %d-%d",
                MyEqualEmojiIdException.total,
                id,
                disperseInteger);
    }

    @Override
    public void print() {
        System.out.println(this.exception);
    }
}