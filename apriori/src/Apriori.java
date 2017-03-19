import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;


public class Apriori {
	/***
	 * The TRANSACTIONS 2-dimensional array holds the full data set for the lab
	 */
    private final int[][] transactions;

    static final int[][] TRANSACTIONS = new int[][]{{1, 2, 3, 4, 5}, {1, 3, 5}, {2, 3, 5}, {1, 5}, {1, 3, 4}, {2, 3, 5}, {2, 3, 5},
                {3, 4, 5}, {4, 5}, {2}, {2, 3}, {2, 3, 4}, {3, 4, 5}};

    static final int[][] BOOK_TRANSACTIONS = new int[][]{{1, 2, 5}, {2, 4}, {2, 3}, {1, 2, 4}, {1, 3}, {2, 3}, {1, 3},
                {1, 2, 3, 5}, {1, 2, 3}};

    public Apriori(int[][] transactions) {
        this.transactions = transactions;
    }

    public static void main( String[] args ) {
        // TODO: Select a reasonable support threshold via trial-and-error. Can either be percentage or absolute value.
        final int supportThreshold = 42;
        Apriori apriori = new Apriori(BOOK_TRANSACTIONS);
        List<ItemSet> list = apriori.getItemsWithAbsoluteThreshold(4);
    }

    public List<ItemSet> getItemsWithAbsoluteThreshold(int supportThreshold) {
        int k = 0;
        HashMap<ItemSet, Integer> frequentItemSets = generateFrequentItemSetsLevel1(transactions, supportThreshold );
        for (k = 1; !frequentItemSets.isEmpty(); k++) {
            System.out.print( "Finding frequent itemsets of length " + (k + 1) + "…" );
            frequentItemSets = generateFrequentItemSets( supportThreshold, transactions, frequentItemSets );
            // TODO: add to list

            System.out.println( " found " + frequentItemSets.size() );
        }
        // TODO: create association rules from the frequent itemsets

        // TODO: return something useful
        return null;
    }

    private static HashMap<ItemSet, Integer> generateFrequentItemSets( int supportThreshold, int[][] transactions,
                    HashMap<ItemSet, Integer> lowerLevelItemSets ) {
        // first generate candidate itemsets from the lower level itemsets
        List<ItemSet> candidates = getCandidatesFrom(lowerLevelItemSets);
        HashMap<ItemSet, Integer> prunedFrequentItemSets = pruneStep(lowerLevelItemSets, candidates, transactions,
                supportThreshold);
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

    private static HashMap<ItemSet, Integer> pruneStep(HashMap<ItemSet, Integer> priorKnowledge,
                                                       List<ItemSet> candidates,
                                                       int[][] transactions,
                                                       int supportThreshold) {
        Map<ItemSet, Integer> conc = new ConcurrentHashMap<>();
        candidates.stream()
                //.parallel()
                .forEach(is ->
                {
                    int count;
                    if (subsetTest(priorKnowledge, is) && (count = countSupport(is.set, transactions)) > supportThreshold)
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

    private static int countSupport( int[] itemSet, int[][] transactions ) {
        // Assumes that items in ItemSets and transactions are both unique
        int count = 0;
        for (int[] transaction : transactions) {
            boolean exist = true;
            for (int item : itemSet) {
                if (Arrays.binarySearch(transaction, item) < 0) {
                    exist = false;
                    break;
                }
            }
            if (exist)
                count++;
        }
        return count;
    }

}
