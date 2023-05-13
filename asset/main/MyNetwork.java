package asset.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Map.Entry;

import com.oocourse.spec3.exceptions.AcquaintanceNotFoundException;
import com.oocourse.spec3.exceptions.EmojiIdNotFoundException;
import com.oocourse.spec3.exceptions.EqualEmojiIdException;
import com.oocourse.spec3.exceptions.EqualGroupIdException;
import com.oocourse.spec3.exceptions.EqualMessageIdException;
import com.oocourse.spec3.exceptions.EqualPersonIdException;
import com.oocourse.spec3.exceptions.EqualRelationException;
import com.oocourse.spec3.exceptions.GroupIdNotFoundException;
import com.oocourse.spec3.exceptions.MessageIdNotFoundException;
import com.oocourse.spec3.exceptions.PathNotFoundException;
import com.oocourse.spec3.exceptions.PersonIdNotFoundException;
import com.oocourse.spec3.exceptions.RelationNotFoundException;
import com.oocourse.spec3.main.EmojiMessage;
import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Message;
import com.oocourse.spec3.main.Network;
import com.oocourse.spec3.main.Person;
import com.oocourse.spec3.main.RedEnvelopeMessage;

import asset.myexception.MyAcquaintanceNotFoundException;
import asset.myexception.MyEmojiIdNotFoundException;
import asset.myexception.MyEqualEmojiIdException;
import asset.myexception.MyEqualGroupIdException;
import asset.myexception.MyEqualMessageIdException;
import asset.myexception.MyEqualPersonIdException;
import asset.myexception.MyEqualRelationException;
import asset.myexception.MyGroupIdNotFoundException;
import asset.myexception.MyMessageIdNotFoundException;
import asset.myexception.MyPathNotFoundException;
import asset.myexception.MyPersonIdNotFoundException;
import asset.myexception.MyRelationNotFoundException;

public class MyNetwork implements Network {
    private final HashMap<Integer, Person> people = new HashMap<>();
    private final HashMap<Integer, Group> groups = new HashMap<>();
    private final HashMap<Integer, Message> messages = new HashMap<>();
    private final HashMap<Integer, Integer> emojis = new HashMap<>();

    public MyNetwork() {
    }

    @Override
    public void addPerson(Person person) throws EqualPersonIdException {
        if (people.containsKey(person.getId())) {
            throw new MyEqualPersonIdException(person.getId());
        }
        people.put(person.getId(), person);
    }

    @Override
    public void addRelation(int id1, int id2, int value)
            throws PersonIdNotFoundException, EqualRelationException {
        MyPerson person1 = (MyPerson) people.get(id1);
        if (person1 == null) {
            throw new MyPersonIdNotFoundException(id1);
        }
        MyPerson person2 = (MyPerson) people.get(id2);
        if (person2 == null) {
            throw new MyPersonIdNotFoundException(id2);
        }
        if (person1.isLinked(person2)) {
            throw new MyEqualRelationException(id1, id2);
        }
        person1.addLink(person2, value);
        person2.addLink(person1, value);
    }

    @Override
    public boolean contains(int id) {
        return people.containsKey(id);
    }

    @Override
    public Person getPerson(int id) {
        return people.get(id);
    }

    @Override
    public boolean isCircle(int id1, int id2) throws PersonIdNotFoundException {
        MyPerson person1 = (MyPerson) people.get(id1);
        if (person1 == null) {
            throw new MyPersonIdNotFoundException(id1);
        }
        MyPerson person2 = (MyPerson) people.get(id2);
        if (person2 == null) {
            throw new MyPersonIdNotFoundException(id2);
        }
        return person1.isCircle(person2);
    }

    @Override
    public int queryBlockSum() {
        HashMap<Person, Person> leader = new HashMap<>();
        for (Person person : people.values()) {
            if (!leader.containsKey(person)) {
                leader.put(person, person);
            }
            for (Person person2 : ((MyPerson) person).getAcquaintance().keySet()) {
                if (leader.containsKey(person2)) {
                    leader.put(getleader(leader, person2), getleader(leader, person));
                } else {
                    leader.put(person2, getleader(leader, person));
                }
            }
        }
        int qbs = 0;
        for (Entry<Person, Person> entry : leader.entrySet()) {
            if (entry.getKey() == entry.getValue()) {
                qbs++;
            }
        }
        return qbs;
    }

    private Person getleader(HashMap<Person, Person> leader, Person person) {
        Person temPerson = person;
        Stack<Person> persons = new Stack<>();
        while (leader.get(temPerson) != temPerson) {
            persons.add(temPerson);
            temPerson = leader.get(temPerson);
        }
        Person element;
        while (!persons.empty()) {
            element = persons.pop();
            leader.put(element, temPerson);
        }
        return temPerson;
    }

    @Override
    public int queryTripleSum() {
        int qtnum = 0;
        Iterator<Person> iterator = people.values().iterator();
        while (iterator.hasNext()) {
            HashSet<Person> next = ((MyPerson) iterator.next()).getSingleWay();
            for (Person person : next) {
                Iterator<Person> inner = ((MyPerson) person).getSingleWay().iterator();
                while (inner.hasNext()) {
                    if (next.contains(inner.next())) {
                        qtnum++;
                    }
                }
            }
        }
        return qtnum;
    }

    @Override
    public int queryValue(int id1, int id2)
            throws PersonIdNotFoundException, RelationNotFoundException {
        MyPerson person1 = (MyPerson) people.get(id1);
        if (person1 == null) {
            throw new MyPersonIdNotFoundException(id1);
        }
        MyPerson person2 = (MyPerson) people.get(id2);
        if (person2 == null) {
            throw new MyPersonIdNotFoundException(id2);
        }
        if (!person1.isLinked(person2)) {
            throw new MyRelationNotFoundException(id1, id2);
        }
        return person1.queryValue(person2);
    }

    @Override
    public void addGroup(Group group) throws EqualGroupIdException {
        if (groups.containsKey(group.getId())) {
            throw new MyEqualGroupIdException(group.getId());
        }
        groups.put(group.getId(), group);
    }

    @Override
    public void addMessage(Message message)
            throws EqualMessageIdException, EqualPersonIdException, EmojiIdNotFoundException {
        if (messages.containsKey(message.getId())) {
            throw new MyEqualMessageIdException(message.getId());
        }
        if (message instanceof EmojiMessage &&
                !emojis.containsKey(((MyEmojiMessage) message).getEmojiId())) {
            throw new MyEmojiIdNotFoundException(((MyEmojiMessage) message).getEmojiId());
        }
        if (message.getPerson1() == message.getPerson2()) {
            throw new MyEqualPersonIdException(message.getPerson1().getId());
        }
        messages.put(message.getId(), message);
    }

    @Override
    public void addToGroup(int id1, int id2)
            throws GroupIdNotFoundException, PersonIdNotFoundException, EqualPersonIdException {
        Group group = groups.get(id2);
        if (group == null) {
            throw new MyGroupIdNotFoundException(id2);
        }
        Person person = people.get(id1);
        if (person == null) {
            throw new MyPersonIdNotFoundException(id1);
        }
        if (group.hasPerson(person)) {
            throw new MyEqualPersonIdException(id1);
        }
        if (group.getSize() <= 1111) {
            group.addPerson(person);
        }
    }

    @Override
    public boolean containsMessage(int id) {
        return messages.containsKey(id);
    }

    @Override
    public void delFromGroup(int id1, int id2)
            throws GroupIdNotFoundException, PersonIdNotFoundException, EqualPersonIdException {
        Group group = groups.get(id2);
        if (group == null) {
            throw new MyGroupIdNotFoundException(id2);
        }
        Person person = people.get(id1);
        if (person == null) {
            throw new MyPersonIdNotFoundException(id1);
        }
        if (!group.hasPerson(person)) {
            throw new MyEqualPersonIdException(id1);
        }
        group.delPerson(person);
    }

    @Override
    public Group getGroup(int id) {
        return groups.get(id);
    }

    @Override
    public Message getMessage(int id) {
        return messages.get(id);
    }

    @Override
    public void modifyRelation(int id1, int id2, int value)
            throws PersonIdNotFoundException, EqualPersonIdException, RelationNotFoundException {
        MyPerson person1 = (MyPerson) people.get(id1);
        if (person1 == null) {
            throw new MyPersonIdNotFoundException(id1);
        }
        MyPerson person2 = (MyPerson) people.get(id2);
        if (person2 == null) {
            throw new MyPersonIdNotFoundException(id2);
        }
        if (id1 == id2) {
            throw new MyEqualPersonIdException(id1);
        }
        if (!person1.isLinked(person2)) {
            throw new MyRelationNotFoundException(id1, id2);
        }
        person1.modifyRelation(person2, value);
        person2.modifyRelation(person1, value);
    }

    @Override
    public int queryBestAcquaintance(int id)
            throws PersonIdNotFoundException, AcquaintanceNotFoundException {
        Person person = people.get(id);
        if (person == null) {
            throw new MyPersonIdNotFoundException(id);
        }
        if (((MyPerson) person).getAcquaintance().size() == 0) {
            throw new MyAcquaintanceNotFoundException(id);
        }
        return ((MyPerson) person).getCoupleId();
    }

    @Override
    public int queryCoupleSum() {
        HashMap<Integer, Integer> coupleIds = new HashMap<>();
        people.forEach((id, person) -> {
            coupleIds.put(id, ((MyPerson) person).getCoupleId());
        });
        int id;
        int coupleId;
        int ans = 0;
        for (Entry<Integer, Integer> relation : coupleIds.entrySet()) {
            id = relation.getKey();
            coupleId = relation.getValue();
            if (coupleId > id && coupleId != 0) {
                if (coupleIds.get(coupleId) == id) {
                    ans++;
                }
            }
        }
        return ans;
    }

    @Override
    public int queryGroupAgeVar(int id) throws GroupIdNotFoundException {
        Group group = groups.get(id);
        if (group == null) {
            throw new MyGroupIdNotFoundException(id);
        }
        return group.getAgeVar();
    }

    @Override
    public int queryGroupValueSum(int id) throws GroupIdNotFoundException {
        Group group = groups.get(id);
        if (group == null) {
            throw new MyGroupIdNotFoundException(id);
        }
        return group.getValueSum();
    }

    @Override
    public List<Message> queryReceivedMessages(int id) throws PersonIdNotFoundException {
        Person person = people.get(id);
        if (person == null) {
            throw new MyPersonIdNotFoundException(id);
        }
        return person.getReceivedMessages();
    }

    @Override
    public int querySocialValue(int id) throws PersonIdNotFoundException {
        Person person = people.get(id);
        if (person == null) {
            throw new MyPersonIdNotFoundException(id);
        }
        return person.getSocialValue();
    }

    @Override
    public void sendMessage(int id)
            throws RelationNotFoundException,
            MessageIdNotFoundException,
            PersonIdNotFoundException {
        Message message = messages.get(id);
        if (message == null) {
            throw new MyMessageIdNotFoundException(id);
        }
        if (message.getType() == 0 && !message.getPerson1().isLinked(message.getPerson2())) {
            throw new MyRelationNotFoundException(
                    message.getPerson1().getId(),
                    message.getPerson2().getId());
        }
        if (message.getType() == 1 && !message.getGroup().hasPerson(message.getPerson1())) {
            throw new MyPersonIdNotFoundException(message.getPerson1().getId());
        }
        Person person1 = message.getPerson1();
        Person person2 = message.getPerson2();
        if (message.getType() == 0 && person1 != person2) {
            person1.addSocialValue(message.getSocialValue());
            person2.addSocialValue(message.getSocialValue());
            messages.remove(message.getId());
            ((MyPerson) person2).addHeaderMessage(message);
            if (message instanceof RedEnvelopeMessage) {
                person1.addMoney(-((RedEnvelopeMessage) message).getMoney());
                person2.addMoney(((RedEnvelopeMessage) message).getMoney());
            }
            if (message instanceof EmojiMessage) {
                int emojiId = ((EmojiMessage) message).getEmojiId();
                emojis.put(emojiId, emojis.get(emojiId) + 1);
            }
        }
        if (message.getType() == 1) {
            Group group = message.getGroup();
            ((MyGroup) group).addSocialValue(message.getSocialValue());
            messages.remove(message.getId());
            if (message instanceof RedEnvelopeMessage) {
                int aveMoney = ((RedEnvelopeMessage) message).getMoney() / group.getSize();
                person1.addMoney(-aveMoney * group.getSize());
                ((MyGroup) group).addMoney(aveMoney);
            }
            if (message instanceof EmojiMessage) {
                int emojiId = ((EmojiMessage) message).getEmojiId();
                emojis.put(emojiId, emojis.get(emojiId) + 1);
            }
        }
    }

    @Override
    public void clearNotices(int personId) throws PersonIdNotFoundException {
        Person person = people.get(personId);
        if (person == null) {
            throw new MyPersonIdNotFoundException(personId);
        }
        ((MyPerson) person).clearNotices(personId);
    }

    @Override
    public boolean containsEmojiId(int id) {
        return emojis.containsKey(id);
    }

    @Override
    public int deleteColdEmoji(int limit) {
        Iterator<Entry<Integer, Integer>> emojiIterator = emojis.entrySet().iterator();
        while (emojiIterator.hasNext()) {
            if (emojiIterator.next().getValue() < limit) {
                emojiIterator.remove();
            }
        }
        Iterator<Entry<Integer, Message>> messageIterator = messages.entrySet().iterator();
        Message message;
        Integer emojiId;
        while (messageIterator.hasNext()) {
            message = messageIterator.next().getValue();
            if (message instanceof EmojiMessage) {
                emojiId = ((EmojiMessage) message).getEmojiId();
                if (!emojis.containsKey(emojiId)) {
                    messageIterator.remove();
                }
            }
        }
        return emojis.size();
    }

    @Override
    public int deleteColdEmojiOKTest(int limit, ArrayList<HashMap<Integer, Integer>> beforeData,
            ArrayList<HashMap<Integer, Integer>> afterData, int result) {
        return Foolish.deleteColdEmojiOkTest(limit, beforeData, afterData, result);
    }

    @Override
    public int queryLeastMoments(int id) throws PersonIdNotFoundException, PathNotFoundException {
        Person person = people.get(id);
        if (person == null) {
            throw new MyPersonIdNotFoundException(id);
        }
        int ans = Algorithm.mininumCircle(people, person);
        if (ans == 0x7fffffff) {
            throw new MyPathNotFoundException(id);
        }
        return ans;
    }

    @Override
    public int queryMoney(int id) throws PersonIdNotFoundException {
        Person person = people.get(id);
        if (person == null) {
            throw new MyPersonIdNotFoundException(id);
        }
        return person.getMoney();
    }

    @Override
    public int queryPopularity(int id) throws EmojiIdNotFoundException {
        Integer emojiHeat = emojis.get(id);
        if (emojiHeat == null) {
            throw new MyEmojiIdNotFoundException(id);
        }
        return emojiHeat;
    }

    @Override
    public void storeEmojiId(int id) throws EqualEmojiIdException {
        if (emojis.containsKey(id)) {
            throw new MyEqualEmojiIdException(id);
        }
        emojis.put(id, 0);
    }
}