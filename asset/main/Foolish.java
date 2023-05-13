package asset.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Foolish {
    public static int modifyRelationOkTest(int id1, int id2, int value,
            HashMap<Integer, HashMap<Integer, Integer>> beforeData,
            HashMap<Integer, HashMap<Integer, Integer>> afterData) {
        //Note: check exceptional_behavior
        if (!beforeData.containsKey(id1) ||
                !beforeData.containsKey(id2) ||
                !beforeData.get(id1).containsKey(id2)) {
            return checkException(id1, id2, value, beforeData, afterData);
        }
        int ans = checkBoth(id1, id2, value, beforeData, afterData);
        if (ans != 0) {
            return ans;
        }
        //Note: select require
        if (beforeData.get(id1).get(id2) + value > 0) {
            ans = checkTypePlus(id1, id2, value, beforeData, afterData);
            if (ans != 0) {
                return ans;
            }
        } else {
            ans = checkMinus(id1, id2, value, beforeData, afterData);
            if (ans != 0) {
                return ans;
            }
        }
        return 0;
    }

    private static int checkException(int id1, int id2, int value,
            HashMap<Integer, HashMap<Integer, Integer>> beforeData,
            HashMap<Integer, HashMap<Integer, Integer>> afterData) {
        Iterator<Entry<Integer, HashMap<Integer, Integer>>> beforeIterator;
        Iterator<Entry<Integer, HashMap<Integer, Integer>>> afterIterator;
        Entry<Integer, HashMap<Integer, Integer>> beforeEntry;
        Entry<Integer, HashMap<Integer, Integer>> afterEntry;
        Iterator<Entry<Integer, Integer>> beforeInner;
        Iterator<Entry<Integer, Integer>> afterInner;
        //Note: check exceptional_behavior
        beforeIterator = beforeData.entrySet().iterator();
        afterIterator = afterData.entrySet().iterator();
        while (beforeIterator.hasNext() && afterIterator.hasNext()) {
            beforeEntry = beforeIterator.next();
            afterEntry = afterIterator.next();
            if (beforeEntry.getKey() != afterEntry.getKey()) {
                return -1;
            }
            beforeInner = beforeEntry.getValue().entrySet().iterator();
            afterInner = afterEntry.getValue().entrySet().iterator();
            while (beforeInner.hasNext() && afterInner.hasNext()) {
                Entry<Integer, Integer> beforeAdj = beforeInner.next();
                Entry<Integer, Integer> afterAdj = afterInner.next();
                if (beforeAdj.getKey() != afterAdj.getKey() ||
                        beforeAdj.getValue() != afterAdj.getValue()) {
                    return -1;
                }
            }
            if (beforeInner.hasNext() || afterInner.hasNext()) {
                return -1;
            }
        }
        if (beforeIterator.hasNext() || afterIterator.hasNext()) {
            return -1;
        }
        return 0;
    }

    private static int checkBoth(int id1, int id2, int value,
            HashMap<Integer, HashMap<Integer, Integer>> beforeData,
            HashMap<Integer, HashMap<Integer, Integer>> afterData) {
        //Note: check 1
        if (beforeData.size() != afterData.size()) {
            return 1;
        }
        //Note: check 2
        Iterator<Integer> beforeId = beforeData.keySet().iterator();
        Iterator<Integer> afterId = afterData.keySet().iterator();
        while (beforeId.hasNext() && afterId.hasNext()) {
            if (beforeId.next() != afterId.next()) {
                return 2;
            }
        }
        Iterator<Entry<Integer, HashMap<Integer, Integer>>> beforeIterator;
        Iterator<Entry<Integer, HashMap<Integer, Integer>>> afterIterator;
        Entry<Integer, HashMap<Integer, Integer>> beforeEntry;
        Entry<Integer, HashMap<Integer, Integer>> afterEntry;
        Iterator<Entry<Integer, Integer>> beforeInner;
        Iterator<Entry<Integer, Integer>> afterInner;
        //Note: check 3
        beforeIterator = beforeData.entrySet().iterator();
        afterIterator = afterData.entrySet().iterator();
        while (beforeIterator.hasNext() && afterIterator.hasNext()) {
            beforeEntry = beforeIterator.next();
            afterEntry = afterIterator.next();
            if (beforeEntry.getKey() != id1 && beforeEntry.getKey() != id2) {
                if (beforeEntry.getValue().size() != afterEntry.getValue().size()) {
                    return 3;
                }
                beforeInner = beforeEntry.getValue().entrySet().iterator();
                afterInner = afterEntry.getValue().entrySet().iterator();
                while (beforeInner.hasNext() && afterInner.hasNext()) {
                    Entry<Integer, Integer> beforeAdj = beforeInner.next();
                    Entry<Integer, Integer> afterAdj = afterInner.next();
                    if (beforeAdj.getKey() != afterAdj.getKey() ||
                            beforeAdj.getValue() != afterAdj.getValue()) {
                        return 3;
                    }
                }
            }
        }
        return 0;
    }

    private static int checkTypePlus(int id1, int id2, int value,
            HashMap<Integer, HashMap<Integer, Integer>> beforeData,
            HashMap<Integer, HashMap<Integer, Integer>> afterData) {
        //Note: check 4
        if (afterData.get(id1).get(id2) == null || afterData.get(id2).get(id1) == null) {
            return 4;
        }
        //Note: check 5
        if (afterData.get(id1).get(id2) != beforeData.get(id1).get(id2) + value) {
            return 5;
        }
        //Note: check 6
        if (afterData.get(id2).get(id1) != beforeData.get(id2).get(id1) + value) {
            return 6;
        }
        //Note: check 7
        if (afterData.get(id1).size() != beforeData.get(id1).size()) {
            return 7;
        }
        //Note: check 8
        if (afterData.get(id2).size() != beforeData.get(id2).size()) {
            return 8;
        }
        //Note: check 9
        Iterator<Integer> beforeAcq = beforeData.get(id1).keySet().iterator();
        Iterator<Integer> afterAcq = afterData.get(id1).keySet().iterator();
        while (beforeAcq.hasNext() && afterAcq.hasNext()) {
            if (beforeAcq.next() != afterAcq.next()) {
                return 9;
            }
        }
        //Note: check 10
        beforeAcq = beforeData.get(id2).keySet().iterator();
        afterAcq = afterData.get(id2).keySet().iterator();
        while (beforeAcq.hasNext() && afterAcq.hasNext()) {
            if (beforeAcq.next() != afterAcq.next()) {
                return 10;
            }
        }
        Iterator<Entry<Integer, Integer>> beforeInner;
        Iterator<Entry<Integer, Integer>> afterInner;
        //Note: check 11
        beforeInner = beforeData.get(id1).entrySet().iterator();
        afterInner = afterData.get(id1).entrySet().iterator();
        while (beforeInner.hasNext() && afterInner.hasNext()) {
            Entry<Integer, Integer> beforeAdj = beforeInner.next();
            Entry<Integer, Integer> afterAdj = afterInner.next();
            if (beforeAdj.getKey() != id2 &&
                    beforeAdj.getValue() != afterAdj.getValue()) {
                return 11;
            }
        }
        //Note: check 12
        beforeInner = beforeData.get(id2).entrySet().iterator();
        afterInner = afterData.get(id2).entrySet().iterator();
        while (beforeInner.hasNext() && afterInner.hasNext()) {
            Entry<Integer, Integer> beforeAdj = beforeInner.next();
            Entry<Integer, Integer> afterAdj = afterInner.next();
            if (beforeAdj.getKey() != id1 &&
                    beforeAdj.getValue() != afterAdj.getValue()) {
                return 12;
            }
        }
        // check 13
        // check 14
        return 0;
    }

    private static int checkMinus(int id1, int id2, int value,
            HashMap<Integer, HashMap<Integer, Integer>> beforeData,
            HashMap<Integer, HashMap<Integer, Integer>> afterData) {
        //Note: check 15
        if (afterData.get(id1).get(id2) != null || afterData.get(id2).get(id1) != null) {
            return 15;
        }
        //Note: check 16
        if (beforeData.get(id1).size() != afterData.get(id1).size() + 1) {
            return 16;
        }
        //Note: check 17
        if (beforeData.get(id2).size() != afterData.get(id2).size() + 1) {
            return 17;
        }
        //Note: check 18
        //Note: check 19
        Iterator<Entry<Integer, Integer>> beforeInner;
        Iterator<Entry<Integer, Integer>> afterInner;
        //Note: check 20
        beforeInner = beforeData.get(id1).entrySet().iterator();
        afterInner = afterData.get(id1).entrySet().iterator();
        while (beforeInner.hasNext() && afterInner.hasNext()) {
            Entry<Integer, Integer> beforeAdj = beforeInner.next();
            Entry<Integer, Integer> afterAdj = afterInner.next();
            if (beforeAdj.getKey() == id2) {
                beforeAdj = beforeInner.next();
            }
            if (beforeAdj.getKey() != afterAdj.getKey() ||
                    beforeAdj.getValue() != afterAdj.getValue()) {
                return 20;
            }
        }
        //Note: check 21
        beforeInner = beforeData.get(id2).entrySet().iterator();
        afterInner = afterData.get(id2).entrySet().iterator();
        while (beforeInner.hasNext() && afterInner.hasNext()) {
            Entry<Integer, Integer> beforeAdj = beforeInner.next();
            Entry<Integer, Integer> afterAdj = afterInner.next();
            if (beforeAdj.getKey() == id1) {
                beforeAdj = beforeInner.next();
            }
            if (beforeAdj.getKey() != afterAdj.getKey() ||
                    beforeAdj.getValue() != afterAdj.getValue()) {
                return 21;
            }
        }
        return 0;
    }

    public static int deleteColdEmojiOkTest(
            int limit, ArrayList<HashMap<Integer, Integer>> beforeData,
            ArrayList<HashMap<Integer, Integer>> afterData, int result) {
        HashMap<Integer, Integer> bfeEmojiMap = beforeData.get(0);
        HashMap<Integer, Integer> aftEmojiMap = afterData.get(0);
        int checker = checkEmoji(limit, bfeEmojiMap, aftEmojiMap);
        if (checker != 0) {
            return checker;
        }
        HashMap<Integer, Integer> bfeMesgeMap = beforeData.get(1);
        HashMap<Integer, Integer> aftMesgeMap = afterData.get(1);
        checker = checkMessage(bfeMesgeMap, aftMesgeMap, aftEmojiMap);
        if (checker != 0) {
            return checker;
        }
        return result == aftEmojiMap.size() ? 0 : 8;
    }

    private static int checkEmoji(
            int limit, HashMap<Integer, Integer> bfeEmojiMap,
            HashMap<Integer, Integer> aftEmojiMap) {
        Iterator<Entry<Integer, Integer>> bfeEmojiIterator = bfeEmojiMap.entrySet().iterator();
        Entry<Integer, Integer> bfeEmoji;
        // Note: check 1
        while (bfeEmojiIterator.hasNext()) {
            bfeEmoji = bfeEmojiIterator.next();
            if (bfeEmoji.getValue() >= limit && !aftEmojiMap.containsKey(bfeEmoji.getKey())) {
                return 1;
            }
        }
        // Note: check 2
        Iterator<Entry<Integer, Integer>> aftEmojiIterator = aftEmojiMap.entrySet().iterator();
        Entry<Integer, Integer> aftEmoji;
        Integer bfeEmojiHeat;
        while (aftEmojiIterator.hasNext()) {
            aftEmoji = aftEmojiIterator.next();
            bfeEmojiHeat = bfeEmojiMap.get(aftEmoji.getKey());
            if (bfeEmojiHeat == null || bfeEmojiHeat != aftEmoji.getValue()) {
                return 2;
            }
        }
        // Note: check 3
        int aftEmojiLength = 0;
        for (Integer heat : bfeEmojiMap.values()) {
            if (heat >= limit) {
                aftEmojiLength++;
            }
        }
        if (aftEmojiLength != aftEmojiMap.size()) {
            return 3;
        }
        // Note: check 4
        return 0;
    }

    private static int checkMessage(
            HashMap<Integer, Integer> bfeMesgeMap,
            HashMap<Integer, Integer> aftMesgeMap,
            HashMap<Integer, Integer> aftEmojiMap) {

        // Note: check 5
        Iterator<Entry<Integer, Integer>> bfeMesgeIterator = bfeMesgeMap.entrySet().iterator();
        Entry<Integer, Integer> bfeMesge;
        Integer aftEmojiId;
        while (bfeMesgeIterator.hasNext()) {
            bfeMesge = bfeMesgeIterator.next();
            if (bfeMesge.getValue() != null && aftEmojiMap.containsKey(bfeMesge.getValue())) {
                aftEmojiId = aftMesgeMap.get(bfeMesge.getKey());
                if (aftEmojiId == null || aftEmojiId.compareTo(bfeMesge.getValue()) != 0) {
                    return 5;
                }
            }
        }
        // Note: check 6
        bfeMesgeIterator = bfeMesgeMap.entrySet().iterator();
        while (bfeMesgeIterator.hasNext()) {
            bfeMesge = bfeMesgeIterator.next();
            if (bfeMesge.getValue() == null &&
                    (!aftMesgeMap.containsKey(bfeMesge.getKey()) ||
                            aftMesgeMap.get(bfeMesge.getKey()) != null)) {
                return 6;
            }
        }
        // Note: check 7
        int messageLength = 0;
        bfeMesgeIterator = bfeMesgeMap.entrySet().iterator();
        while (bfeMesgeIterator.hasNext()) {
            bfeMesge = bfeMesgeIterator.next();
            if (bfeMesge.getValue() == null || aftEmojiMap.containsKey(bfeMesge.getValue())) {
                messageLength++;
            }
        }
        if (messageLength != aftMesgeMap.size()) {
            return 7;
        }
        return 0;
    }
}
