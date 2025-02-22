package asset.main;

import com.oocourse.spec3.main.EmojiMessage;
import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Person;

public class MyEmojiMessage extends MyMessage implements EmojiMessage {
    private int emojiId;

    /*@ ensures type == 0;
      @ ensures group == null;
      @ ensures id == messageId;
      @ ensures person1 == messagePerson1;
      @ ensures person2 == messagePerson2;
      @ ensures emojiId == emojiNumber;
      @*/
    public MyEmojiMessage(
            int messageId, int emojiNumber, Person messagePerson1, Person messagePerson2) {
        super(messageId, emojiNumber, messagePerson1, messagePerson2);
        this.emojiId = emojiNumber;
    }

    /*@ ensures type == 1;
      @ ensures person2 == null;
      @ ensures id == messageId;
      @ ensures person1 == messagePerson1;
      @ ensures group == messageGroup;
      @ ensures emojiId == emojiNumber;
      @*/
    public MyEmojiMessage(
            int messageId, int emojiNumber, Person messagePerson1, Group messageGroup) {
        super(messageId, emojiNumber, messagePerson1, messageGroup);
        this.emojiId = emojiNumber;
    }

    @Override
    public int getEmojiId() {
        return emojiId;
    }
}
