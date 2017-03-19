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
        AssociationRule ass = new AssociationRule(new ItemSet(new int[]{1, 4}), new ItemSet(new int[]{2}), 0.3);
        System.out.println(ass);
        Apriori apriori = new Apriori(BOOK_TRANSACTIONS);
        List<AssociationRule> list = apriori.getItemsWithAbsoluteThreshold(4);
    }

    public List<AssociationRule> getItemsWithAbsoluteThreshold(int supportThreshold) {
        int k = 0;
        HashMap<ItemSet, Integer> frequentItemSets = generateFrequentItemSetsLevel1(transactions, supportThreshold );
        cache.putAll(frequentItemSets);
        for (k = 1; !frequentItemSets.isEmpty(); k++) {
            System.out.print( "Finding frequent itemsets of length " + (k + 1) + "…" );
            frequentItemSets = generateFrequentItemSets(frequentItemSets, supportThreshold);
            System.out.println( " found " + frequentItemSets.size() );
        }
        List<AssociationRule> assocRules = calculateAssociationRules(frequentItemSets.keySet());
        return assocRules;
    }

    private HashMap<ItemSet, Integer> generateFrequentItemSets(HashMap<ItemSet, Integer> lowerLevelItemSets,
                                                                       int supportThreshold)
                    {
        // first generate candidate itemsets from the lower level itemsets
        List<ItemSet> candidates = getCandidatesFrom(lowerLevelItemSets);
        HashMap<ItemSet, Integer> prunedFrequentItemSets = pruneStep(lowerLevelItemSets, candidates, supportThreshold);
         // now check the support for all candidates and add only those that have enough support to the set

        return prunedFrequentItemSets;
    }

    private static List<ItemSet> getCandidatesFrom(HashMap<ItemSet, Integer> lowerLevelItemSets) {
        List<ItemSet> candidates = new ArrayList<>();
        ItemSet[] itemSets = lowerLevelItemSets.keySet().toArray(new ItemSet[lowerLevelItemSets.keySet().size()]);
        for (int i = 0; i < itemSets.length; i++) {
            for (int j = i; j < itemSets.length; j++) {
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
        for (int i = 0; i < firstArr.length; i++)
            join[i] = firstArr[i];
        join[firstArr.length] = second.set[firstArr.length - 1];

        return new ItemSet(join);
    }

    private HashMap<ItemSet, Integer> pruneStep(HashMap<ItemSet, Integer> priorKnowledge,
                                                       List<ItemSet> candidates,
                                                       int supportThreshold) {
        Map<ItemSet, Integer> conc = new ConcurrentHashMap<>();
        candidates.stream()
                //.parallel()
                .forEach(is ->
                {
                    int count;
                    if (subsetTest(priorKnowledge, is) && (count = countSupport(is.set)) >
                            supportThreshold)
                        conc.put(is, count);
                });
        return new HashMap<>(conc);
    }
    /***
     * The method calculates the candidate's subsets with size k - 1 , where k is the candidate's itemset size.
     * @param priorKnowledge
     * @param candidate
     * @return true if all @candidate's subsets having size k - 1 are also frequent, false otherwise.
     */
    private static  boolean subsetTest(HashMap<ItemSet, Integer> priorKnowledge, ItemSet candidate) {
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
        return res;
    }

    private static HashMap<ItemSet, Integer> generateFrequentItemSetsLevel1( int[][] transactions, int
            supportThreshold) {
        int[] array = new int[6];

        for (int[] arr : transactions)
            for (int i : arr)
                array[i]++;

        HashMap<ItemSet, Integer> table = new HashMap<>();
        for (int i = 0; i < array.length; i++)
            if (array[i] > supportThreshold)
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
        for (ItemSet itemSet : frequentItems) {
            associationRules.addAll(getConfidenceFor(itemSet));
        }
        return associationRules;
    }

    private List<AssociationRule> getConfidenceFor(ItemSet frequentItem) {
        List<AssociationRule> list = new ArrayList<>();
        ItemSet dependent;
        double prob, countSupport = cache.get(frequentItem);
        for (ItemSet subset: getSubsets(frequentItem)) {
            prob = cache.get(countSupport)/cache.get(subset);
            if (prob > minConf) {
                dependent = difference(frequentItem, subset);
                list.add(new AssociationRule(subset, dependent, prob));
            }
        }
        return list;
    }

    private Iterable<ItemSet> getSubsets(ItemSet itemSet) {
        List<ItemSet> subsets = new ArrayList<>();
        //TODO: Generate Subsets
        return subsets;
    }

    private static ItemSet difference(ItemSet itemSet, ItemSet itemSubset) {
        int[] subsetSet = itemSubset.set, set = itemSet.set;
        if (set.length <= subsetSet.length)
            throw new IllegalArgumentException(itemSet + " must be a proper superset of "+ itemSubset);
        int[] diff = new int[set.length - subsetSet.length];
        for (int i = 0; i < set.length; i++) {
            if (Arrays.binarySearch(subsetSet, set[i]) < 0) {
                diff[i] = set[i];
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
            return given.toString() + " => " + dependent.toString() + " with confidence: " + String.valueOf(confidence);
        }
    }
}
