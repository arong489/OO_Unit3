package asset.myexception;

import java.util.HashMap;

import com.oocourse.spec3.exceptions.EmojiIdNotFoundException;

public class MyEmojiIdNotFoundException extends EmojiIdNotFoundException {
    private static int total = 0;
    private static HashMap<Integer, Integer> disperse = new HashMap<>();
    private String exception;

    public MyEmojiIdNotFoundException(int id) {
        MyEmojiIdNotFoundException.total++;
        Integer disperseInteger = MyEmojiIdNotFoundException.disperse.get(id);
        if (disperseInteger == null) {
            disperseInteger = new Integer(1);
        } else {
            disperseInteger++;
        }
        disperse.put(id, disperseInteger);
        this.exception = String.format(
                "einf-%d, %d-%d",
                MyEmojiIdNotFoundException.total,
                id,
                disperseInteger);
    }

    @Override
    public void print() {
        System.out.println(this.exception);
    }
}