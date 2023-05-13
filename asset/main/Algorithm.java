package asset.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import com.oocourse.spec3.main.Person;

public class Algorithm {
    public static int mininumCircle(Collection<Person> persons, Person src) {
        // <arrive, [dis from]>
        HashMap<Person, int[]> arrive = new HashMap<>();
        // <rest, [dis, potential from]>
        HashMap<Person, int[]> rest = new HashMap<>();
        ArrayList<MyPerson> helperPersons = new ArrayList<>();
        // initial
        initialMap(persons, arrive, rest, helperPersons, src);
        int ans = modifyDijkstra(arrive, rest);
        fixMap(helperPersons, src);
        return ans;
    }

    private static void initialMap(
            Collection<Person> persons,
            HashMap<Person, int[]> arrive,
            HashMap<Person, int[]> rest,
            ArrayList<MyPerson> helperPersons,
            Person src) {
        int id = 1;
        MyPerson tempPerson;

        for (Person person : persons) {
            if (person == src) {
                continue;
            }
            if (src.isLinked(person)) {
                rest.put(person, new int[] { src.queryValue(person), id });
                tempPerson = new MyPerson(-id, null, 0);
                tempPerson.addLink(person, src.queryValue(person));
                ((MyPerson) person).addLink(tempPerson, src.queryValue(person));
                ((MyPerson) person).modifyRelation(src, -src.queryValue(person));
                arrive.put(tempPerson, new int[] { 0, id });
                helperPersons.add(tempPerson);
                id++;
            } else {
                rest.put(person, new int[] { 0x7fffffff, 0 });
            }
        }
    }

    private static int modifyDijkstra(
            HashMap<Person, int[]> arrive,
            HashMap<Person, int[]> rest) {
        MyPerson ans = null;
        int[] initialArray = new int[] { 0x7fffffff, 0 };
        int[] tempArray;
        int[] minArray = initialArray;
        int myAns = 0x7fffffff;
        int tempAns;
        Person tempPerson;
        while (true) {
            minArray = initialArray;
            ans = null;
            for (Entry<Person, int[]> entry : rest.entrySet()) {
                tempArray = entry.getValue();
                if (tempArray[0] < minArray[0]) {
                    minArray = tempArray;
                    ans = (MyPerson) entry.getKey();
                }
            }
            if (ans == null) {
                break;
            }
            arrive.put(ans, minArray);
            rest.remove(ans);
            for (Entry<Person, Integer> entry : ans.getAcquaintance().entrySet()) {
                tempPerson = entry.getKey();
                tempArray = rest.get(tempPerson);
                if (tempArray == null) {
                    tempArray = arrive.get(tempPerson);
                    tempAns = tempArray[0] + minArray[0] + entry.getValue();
                    if (tempArray[1] != minArray[1] && tempAns < myAns) {
                        myAns = tempAns;
                    }
                } else {
                    tempAns = minArray[0] + entry.getValue();
                    if (tempArray[0] > tempAns) {
                        tempArray[0] = tempAns;
                        tempArray[1] = minArray[1];
                    }
                }
            }
        }
        return myAns;
    }

    private static void fixMap(
            ArrayList<MyPerson> helperPersons,
            Person src) {
        Entry<Person, Integer> entry;
        for (MyPerson myPerson : helperPersons) {
            entry = myPerson.getAcquaintance().entrySet().iterator().next();
            ((MyPerson) entry.getKey()).modifyRelation(myPerson, -entry.getValue());
            ((MyPerson) entry.getKey()).addLink(src, entry.getValue());
        }
    }
}
// ap 1 a 1
// ap 2 b 1
// ap 3 c 1
// ap 4 d 1
// ap 5 e 1
// ar 1 2 1
// ar 1 3 2
// ar 1 4 3
// ar 1 5 4
// ar 2 3 10
// ar 3 4 10
// ar 4 5 10
// ar 5 2 10
// ar 1 4 1
// ar 1 3 10
// ar 1 2 16
// ar 2 3 100
// ar 2 5 15
// ar 3 5 20