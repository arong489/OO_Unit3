package asset.main;

import java.util.HashMap;
import java.util.Stack;
import java.util.Map.Entry;

import com.oocourse.spec3.main.Person;

public class Algorithm {
    public static int mininumCircle(HashMap<Integer, Person> people, Person src) {
        // <arrive, [dis from]>
        HashMap<Person, int[]> arrive = new HashMap<>();
        // <rest, [dis, potential from]>
        HashMap<Person, int[]> rest = new HashMap<>();
        HashMap<Person, Person> path = new HashMap<>();
        // initial
        initialMap(people, arrive, rest, path, src);
        return modifyDijkstra(arrive, rest, path);
    }

    private static void initialMap(
            HashMap<Integer, Person> people, HashMap<Person, int[]> arrive,
            HashMap<Person, int[]> rest, HashMap<Person, Person> path,
            Person src) {
        int id = 1;
        arrive.put(src, new int[] { 0, 0x80000000 });
        for (Person person : people.values()) {
            if (person == src) {
                continue;
            }
            if (src.isLinked(person)) {
                rest.put(person, new int[] { src.queryValue(person), id });
                path.put(person, src);
                id++;
            } else {
                rest.put(person, new int[] { 0x7fffffff, 0 });
            }
        }
    }

    private static int modifyDijkstra(HashMap<Person, int[]> arrive,
            HashMap<Person, int[]> rest, HashMap<Person, Person> path) {
        MyPerson ans = null;
        int[] initialArray = new int[] { 0x7fffffff, 0 };
        int[] tempArray;
        int[] minArray = initialArray;
        int myAns = 0x7fffffff;
        int tempAns;
        Person tempPerson;
        Person ansPerson1 = null;
        Person ansPerson2 = null;
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
                if (tempPerson.getId() == path.get(ans).getId()) {
                    continue;
                }
                tempArray = rest.get(tempPerson);
                if (tempArray == null) {
                    tempArray = arrive.get(tempPerson);
                    tempAns = tempArray[0] + minArray[0] + entry.getValue();
                    if (tempArray[1] != minArray[1] && tempAns < myAns) {
                        myAns = tempAns;
                        ansPerson1 = ans;
                        ansPerson2 = tempPerson;
                    }
                } else {
                    tempAns = minArray[0] + entry.getValue();
                    if (tempArray[0] > tempAns) {
                        tempArray[0] = tempAns;
                        tempArray[1] = minArray[1];
                        path.put(tempPerson, ans);
                    }
                }
            }
        }
        if (myAns != 0x7fffffff && ansPerson1 != null && ansPerson2 != null) {
            printPath(path, ansPerson1, ansPerson2);
        }
        return myAns;
    }

    private static void printPath(
            HashMap<Person, Person> path,
            Person ansPerson1,
            Person ansPerson2) {
        Stack<Person> tempPath = new Stack<>();
        Person tempPerson = ansPerson1;
        System.out.print("Path :\t");
        while (tempPerson != null) {
            tempPath.push(tempPerson);
            tempPerson = path.get(tempPerson);
        }
        while (!tempPath.empty()) {
            System.out.print(tempPath.pop().getId() + " -> ");
        }
        System.out.print(ansPerson2.getId());
        tempPerson = path.get(ansPerson2);
        while (tempPerson != null) {
            System.out.print(" -> " + tempPerson.getId());
            tempPerson = path.get(tempPerson);
        }
        System.out.println("");
    }
}
// ap -1 a 1
// ap -2 b 1
// ap -3 c 1
// ap -4 d 1
// ap -5 e 1
// ar -1 -2 1
// ar -1 -3 2
// ar -1 -4 3
// ar -1 -5 4
// ar -2 -3 10
// ar -3 -4 10
// ar -4 -5 10
// ar -5 -2 10
// ar 1 4 1
// ar 1 3 10
// ar 1 2 16
// ar 2 3 100
// ar 2 5 15
// ar 3 5 20