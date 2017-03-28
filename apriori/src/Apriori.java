import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class Apriori {
	/***
	 * The TRANSACTIONS 2-dimensional array holds the full data set for the lab
	 */
    private final int[][] transactions;
    private final Map<ItemSet, Integer> cache;
    private final double minConf = 0.25;
    static final int[][] TRANSACTIONS = new int[][]{{1, 2, 3, 4, 5}, {1, 3, 5}, {2, 3, 5}, {1, 5}, {1, 3, 4}, {2, 3, 5}, {2, 3, 5},
                {3, 4, 5}, {4, 5}, {2}, {2, 3}, {2, 3, 4}, {3, 4, 5}};

    static final int[][] BOOK_TRANSACTIONS = new int[][]{{1, 2, 5}, {2, 4}, {2, 3}, {1, 2, 4}, {1, 3}, {2, 3}, {1, 3},
                {1, 2, 3, 5}, {1, 2, 3}};

    public Apriori(int[][] transactions) {
        this.transactions = transactions;
        cache = new ConcurrentHashMap<>();
    }

    public static void main( String[] args ) {
        final int supportThreshold = 42;
        Apriori apriori = new Apriori(BOOK_TRANSACTIONS);
        List<AssociationRule> list = apriori.getItemsWithAbsoluteThreshold(2);
        for (AssociationRule rule : list)
            System.out.println(rule.toString());
    }

    public List<AssociationRule> getItemsWithAbsoluteThreshold(int supportThreshold) {
        int k = 0;
        Map<ItemSet, Integer> frequentItemSets = generateFrequentItemSetsLevel1(transactions, supportThreshold ),
        tmp = null;
        cache.putAll(frequentItemSets);
        for (k = 1; !frequentItemSets.isEmpty(); k++) {
            System.out.print( "Finding frequent itemsets of length " + (k + 1) + "…" );
            tmp = frequentItemSets;
            frequentItemSets = generateFrequentItemSets(Collections.unmodifiableMap(frequentItemSets),
                    supportThreshold);
            System.out.println( " found " + frequentItemSets.size() );
        }
        List<AssociationRule> assocRules = calculateAssociationRules(tmp.keySet());
        return assocRules;
    }

    private Map<ItemSet, Integer> generateFrequentItemSets(Map<ItemSet, Integer> lowerLevelItemSets,
                                                                       int supportThreshold)
                    {
        // first generate candidate itemsets from the lower level itemsets
        List<ItemSet> candidates = getCandidatesFrom(lowerLevelItemSets);
        Map<ItemSet, Integer> prunedFrequentItemSets = pruneStep(lowerLevelItemSets, candidates, supportThreshold);
         // now check the support for all candidates and add only those that have enough support to the set

        return prunedFrequentItemSets;
    }

    private static List<ItemSet> getCandidatesFrom(Map<ItemSet, Integer> lowerLevelItemSets) {
        List<ItemSet> candidates = new ArrayList<>();
        ItemSet[] itemSets = lowerLevelItemSets.keySet().toArray(new ItemSet[lowerLevelItemSets.keySet().size()]);
        for (int i = 0; i < itemSets.length - 1; i++) {
            for (int j = i + 1; j < itemSets.length; j++) {
                if (itemSets[i].isJoinableWith(itemSets[j])) {
                    candidates.add(joinStep(itemSets[i], itemSets[j]));
                }
            }
        }

        return candidates;
    }

    private static ItemSet joinStep(ItemSet first, ItemSet second) {
        // Assume that are joinable using the isJoinable method in the ItemSet class.
        int[] firstArr = first.set,
                join = new int[firstArr.length + 1];
        for (int i = 0; i < firstArr.length - 1; i++)
            join[i] = firstArr[i];
        int f = firstArr[firstArr.length - 1], s = second.set[firstArr.length - 1];
        if (s < f) {
            int tmp = f;
            f = s;
            s = tmp;
        }
        join[join.length - 2] = f;
        join[join.length - 1] = s;
        return new ItemSet(join);
    }

    private Map<ItemSet, Integer> pruneStep(Map<ItemSet, Integer> priorKnowledge,
                                                       List<ItemSet> candidates,
                                                       int supportThreshold) {
        Map<ItemSet, Integer> conc = new HashMap<>();
        candidates.stream()
                //.parallel()
                .forEach(is ->
                {
                    int count;
                    if (subsetTest(priorKnowledge, is) && (count = countSupport(is.set)) >=
                            supportThreshold)
                        conc.put(is, count);
                });
        return conc;
    }
    /***
     * The method calculates the candidate's subsets with size k - 1 , where k is the candidate's itemset size.
     * @param priorKnowledge
     * @param candidate
     * @return true if all @candidate's subsets having size k - 1 are also frequent, false otherwise.
     */
    private static  boolean subsetTest(Map<ItemSet, Integer> priorKnowledge, ItemSet candidate) {
        int[] set = candidate.set;
        ItemSet tmpItemset = new ItemSet(null);

        for (int i = 0; i < set.length; i++) {
                tmpItemset.set = joinArr(Arrays.copyOfRange(set, 0, i), Arrays.copyOfRange(set, i + 1, set.length));
                if (!priorKnowledge.containsKey(tmpItemset))
                    return false;
        }
        return true;
    }
    private static int[] joinArr(int[] arr1, int[] arr2) {
        int[] res = new int[arr1.length + arr2.length];
        for (int i = 0; i < arr1.length; i++)
            res[i] = arr1[i];
        for (int j = 0; j < arr2.length; j++)
            res[arr1.length + j] = arr2[j];
        Arrays.sort(res);
        return res;
    }

    private static Map<ItemSet, Integer> generateFrequentItemSetsLevel1( int[][] transactions, int
            supportThreshold) {
        int[] array = new int[6];

        for (int[] arr : transactions)
            for (int i : arr)
                array[i]++;

        Map<ItemSet, Integer> table = new HashMap<>();
        for (int i = 0; i < array.length; i++)
            if (array[i] >= supportThreshold)
                table.put(new ItemSet(new int[]{i}), new Integer(array[i]));

        return table;
    }

    private int countSupport(int[] itemSetArr) {
        Integer in;
        ItemSet itemSet = new ItemSet(itemSetArr);
        if ((in = cache.get(itemSet)) != null)
            return in;
        int count = 0;
        for (int[] transaction : transactions) {
            boolean exist = true;
            for (int item : itemSetArr) {
                if (Arrays.binarySearch(transaction, item) < 0) {
                    exist = false;
                    break;
                }
            }
            if (exist)
                count++;
        }
        cache.put(itemSet, count);
        return count;
    }

    private List<AssociationRule> calculateAssociationRules(Collection<ItemSet> frequentItems) {
        List<AssociationRule> associationRules =  new ArrayList<>(frequentItems.size());
        for (ItemSet frequentItem: frequentItems) {
            associationRules.addAll(getConfidenceFor(frequentItem));
        }
        return associationRules;
    }

    private List<AssociationRule> getConfidenceFor(ItemSet frequentItem) {
        List<AssociationRule> list = new ArrayList<>();
        ItemSet dependent;
        double prob, countSupport = cache.get(frequentItem);
        for (ItemSet subset: getSubsets(frequentItem)) {
            prob = countSupport/cache.get(subset);
            if (prob > minConf) {
                dependent = difference(frequentItem, subset);
                list.add(new AssociationRule(subset, dependent, prob));
            }
        }
        return list;
    }

    private Iterable<ItemSet> getSubsets(ItemSet itemSet) {
        return itemSet.getSubsets();
    }

    private static ItemSet difference(ItemSet itemSet, ItemSet itemSubset) {
        int[] subsetSet = itemSubset.set, set = itemSet.set;
        if (set.length <= subsetSet.length)
            throw new IllegalArgumentException(itemSet + " must be a proper superset of "+ itemSubset);
        int[] diff = new int[set.length - subsetSet.length];
        int count =0;
        for (int i = 0; i < set.length; i++) {
            if (Arrays.binarySearch(subsetSet, set[i]) < 0) {
                diff[count++] = set[i];
            }
        }
        return new ItemSet(diff);
    }


    private static class AssociationRule {
        private final ItemSet given, dependent;
        final double confidence;

        AssociationRule(ItemSet given, ItemSet dependent, double confidence) {
            this.confidence = confidence;
            this.dependent = dependent;
            this.given = given;
        }

        public String toString() {
            return given.toString() + "=> " + dependent.toString() + "with confidence: " + String.valueOf
                    (confidence);
        }
    }
}
