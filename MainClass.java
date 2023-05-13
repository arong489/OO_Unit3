import com.oocourse.spec3.main.Runner;

import asset.main.MyEmojiMessage;
import asset.main.MyGroup;
import asset.main.MyMessage;
import asset.main.MyNetwork;
import asset.main.MyNoticeMessage;
import asset.main.MyPerson;
import asset.main.MyRedEnvelopeMessage;

public class MainClass {
    public static void main(String[] args) throws Exception {
        Runner runner = new Runner(
                MyPerson.class,
                MyNetwork.class,
                MyGroup.class,
                MyMessage.class,
                MyEmojiMessage.class,
                MyNoticeMessage.class,
                MyRedEnvelopeMessage.class);
        runner.run();
    }
}