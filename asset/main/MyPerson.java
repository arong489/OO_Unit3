package asset.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.oocourse.spec3.main.Message;
import com.oocourse.spec3.main.NoticeMessage;
import com.oocourse.spec3.main.Person;

public class MyPerson implements Person {

    private int id;
    private String name;
    private int age;
    private final HashMap<Person, Integer> acquaintance = new HashMap<>();
    private int money = 0;
    private int socialValue = 0;
    private final List<Message> messages = new ArrayList<>();
    //attached single way list
    private HashSet<Person> singleacquaintance = new HashSet<>();

    public MyPerson(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person p2) {
        return this.name.compareTo(p2.getName());
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getMoney() {
        return money;
    }

    public HashSet<Person> getSingleacquaintance() {
        return singleacquaintance;
    }

    @Override
    public boolean isLinked(Person person) {
        if (this.id == person.getId()) {
            return true;
        }
        return acquaintance.containsKey(person);
    }

    @Override
    public int queryValue(Person person) {
        Integer tempValue = acquaintance.get(person);
        if (tempValue != null) {
            return tempValue.intValue();
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Person)) {
            return false;
        }
        MyPerson other = (MyPerson) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public void addSocialValue(int num) {
        this.socialValue += num;
    }

    @Override
    public int getSocialValue() {
        return socialValue;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public List<Message> getReceivedMessages() {
        if (messages.size() < 6) {
            return messages;
        } else {
            return messages.subList(0, 5);
        }
    }

    @Override
    public void addMoney(int num) {
        this.money += num;
    }

    public HashMap<Person, Integer> getAcquaintance() {
        return acquaintance;
    }

    public void addHeaderMessage(Message message) {
        messages.add(0, message);
    }

    // relation modifyer helper
    // check refresh
    public void modifyRelation(Person person, int value) {
        Integer temp = acquaintance.get(person) + value;
        if (temp > 0) {
            acquaintance.put(person, temp);
        } else {
            acquaintance.remove(person);
            if (person.getId() > this.id) {
                singleacquaintance.remove(person);
            }
        }
    }

    // clearNotices helper
    public void clearNotices(int personId) {
        Iterator<Message> iterator = messages.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof NoticeMessage) {
                iterator.remove();
            }
        }
    }

    // after this line, it is union find set
    // addLink
    // add singleway
    public void addLink(Person person, Integer value) {
        // Note: add two kinds of relationship
        acquaintance.put(person, value);

        if (person.getId() > this.id) {
            singleacquaintance.add(person);
        }
    }

    public boolean isCircle(Person person) {
        if (this.id == person.getId() || acquaintance.containsKey(person)) {
            return true;
        }
        // Note: bidirectional BFS
        ArrayList<Person> head = new ArrayList<Person>();
        ArrayList<Person> tail = new ArrayList<Person>();
        head.add(this);
        tail.add(person);

        HashSet<Person> headArrive = new HashSet<>();
        HashSet<Person> tailArrive = new HashSet<>();
        headArrive.add(this);
        tailArrive.add(person);

        while (head.size() != 0 && tail.size() != 0) {
            for (Person temPerson : ((MyPerson) head.get(0)).getAcquaintance().keySet()) {
                if (tailArrive.contains(temPerson)) {
                    return true;
                }
                if (headArrive.add(temPerson)) {
                    head.add(temPerson);
                }
            }
            head.remove(0);
            for (Person temPerson : ((MyPerson) tail.get(0)).getAcquaintance().keySet()) {
                if (headArrive.contains(temPerson)) {
                    return true;
                }
                if (tailArrive.add(temPerson)) {
                    tail.add(temPerson);
                }
            }
            tail.remove(0);
        }
        return false;
    }

    public HashSet<Person> getSingleWay() {
        return this.singleacquaintance;
    }

    public Integer getCoupleId() {
        Integer coupleId = null;
        int value = 0;
        int tempId;
        int tempValue;
        for (Entry<Person, Integer> entry : acquaintance.entrySet()) {
            tempValue = entry.getValue();
            tempId = entry.getKey().getId();
            if (tempValue > value ||
                    (tempValue == value && tempId < coupleId)) {
                coupleId = tempId;
                value = tempValue;
            }
        }
        return coupleId;
    }
}