package asset.main;

import java.util.HashSet;

import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Person;

public class MyGroup implements Group {
    private int id;
    private final HashSet<Person> people = new HashSet<>();

    public MyGroup(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public HashSet<Person> getPeople() {
        return people;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Group)) {
            return false;
        }
        return ((Group) obj).getId() == id;
    }

    @Override
    public void addPerson(Person person) {
        people.add(person);
    }

    @Override
    public void delPerson(Person person) {
        people.remove(person);
    }

    @Override
    public int getAgeMean() {
        if (people.size() == 0) {
            return 0;
        }
        int sum = 0;
        for (Person person : people) {
            sum += person.getAge();
        }
        return sum / people.size();
    }

    @Override
    public int getAgeVar() {
        if (people.size() == 0) {
            return 0;
        }
        int sum = 0;
        int average = getAgeMean();
        for (Person person : people) {
            sum += (person.getAge() - average) * (person.getAge() - average);
        }
        return sum / people.size();
    }

    @Override
    public int getSize() {
        return people.size();
    }

    @Override
    public int getValueSum() {
        int sum = 0;
        for (Person person : people) {
            for (Person person2 : people) {
                if (person != person2 && person.isLinked(person2)) {
                    sum += person.queryValue(person2);
                }
            }
        }
        return sum;
    }

    @Override
    public boolean hasPerson(Person person) {
        return people.contains(person);
    }

    public void addMoney(int money) {
        people.forEach((person) -> {
            person.addMoney(money);
        });
    }

    public void addSocialValue(int socialValue) {
        people.forEach((person) -> {
            person.addSocialValue(socialValue);
        });
    }
}
