import java.util.*;
import java.util.stream.Stream;


public class Apriori {
	/***
	 * The TRANSACTIONS 2-dimensional array holds the full data set for the lab
	 */
    static final int[][] TRANSACTIONS = new int[][] { { 1, 2, 3, 4, 5 }, { 1, 3, 5 }, { 2, 3, 5 }, { 1, 5 }, { 1, 3, 4 }, { 2, 3, 5 }, { 2, 3, 5 },
                    { 3, 4, 5 }, { 4, 5 }, { 2 }, { 2, 3 }, { 2, 3, 4 }, { 3, 4, 5 } };
                    
    static final int[][] BOOK_TRANSACTIONS = new int[][] { { 1, 2, 5 }, {2, 4}, { 2, 3 }, { 1, 2, 4 }, { 1, 3 }, { 2, 3 }, { 1, 3 },
                    { 1, 2, 3, 5 }, { 1, 2, 3 }};

    public static void main( String[] args ) {
        // TODO: Select a reasonable support threshold via trial-and-error. Can either be percentage or absolute value.
        final int supportThreshold = 42;
        apriori( TRANSACTIONS, supportThreshold );
    }

    public static List<ItemSet> apriori( int[][] transactions, int supportThreshold ) {
        int k = 0;
        Hashtable<ItemSet, Integer> frequentItemSets = generateFrequentItemSetsLevel1( transactions, supportThreshold );
        for (k = 1; frequentItemSets.size() > 0; k++) {
            System.out.print( "Finding frequent itemsets of length " + (k + 1) + "…" );
            frequentItemSets = generateFrequentItemSets( supportThreshold, transactions, frequentItemSets );
            // TODO: add to list

            System.out.println( " found " + frequentItemSets.size() );
        }
        // TODO: create association rules from the frequent itemsets

        // TODO: return something useful
        return null;
    }

    private static Hashtable<ItemSet, Integer> generateFrequentItemSets( int supportThreshold, int[][] transactions,
                    Hashtable<ItemSet, Integer> lowerLevelItemSets ) {
        // first generate candidate itemsets from the lower level itemsets
        List<ItemSet> candidates = getCandidatesFrom(lowerLevelItemSets);

        Hashtable<ItemSet, Integer> prunedFrequentItemSets = pruneCandidate(candidates, transactions, supportThreshold);
         // now check the support for all candidates and add only those that have enough support to the set

        return prunedFrequentItemSets.isEmpty() ? lowerLevelItemSets: prunedFrequentItemSets;
    }

    private static List<ItemSet> getCandidatesFrom(Hashtable<ItemSet, Integer> lowerLevelItemSets) {
        List<ItemSet> candidates = new ArrayList<>();
        ItemSet[] itemSets = lowerLevelItemSets.keySet().toArray(new ItemSet[lowerLevelItemSets.keySet().size()]);
        for (int i = 0; i < itemSets.length; i++) {
            for (int j = i; j < itemSets.length; j++) {
                if (itemSets[i].isJoinableWith(itemSets[j])) {
                    candidates.add(joinSets(itemSets[i], itemSets[j]));
                }
            }
        }

        return candidates;
    }

    private static ItemSet joinSets( ItemSet first, ItemSet second ) {
        // Assume that are joinable using the isJoinable method in the ItemSet class.
        int[] firstArr = first.set,
                join = new int[firstArr.length + 1];
        for (int i = 0; i < firstArr.length; i++)
            join[i] = firstArr[i];
        join[firstArr.length] = second.set[firstArr.length - 1];

        return new ItemSet(join);
    }

    private static Hashtable<ItemSet, Integer> pruneCandidate(List<ItemSet> candidates,
                                                              int[][] transactions, int
                                                                      supportThreshold) {
        Hashtable<ItemSet, Integer> pruned = new Hashtable<>();
        Stream<ItemSet> str = candidates.stream().filter(is -> {
            int count = countSupport(is.set, transactions);
            boolean isSatisfiable = count > supportThreshold;
            if (isSatisfiable)
                pruned.put(is, count);
            return isSatisfiable;
        });
        return pruned;
    }
    private static Hashtable<ItemSet, Integer> generateFrequentItemSetsLevel1( int[][] transactions, int supportThreshold) {
        int[] array = new int[6];

        for (int[] arr : transactions)
            for (int i : arr)
                array[i]++;

        Hashtable<ItemSet, Integer> table = new Hashtable<>();
        for (int i = 0; i < array.length; i++)
            if (array[i] > supportThreshold)
                table.put(new ItemSet(new int[]{i}), new Integer(array[i]));

        return table;
    }

    private static int countSupport( int[] itemSet, int[][] transactions ) {
        // Assumes that items in ItemSets and transactions are both unique

        // TODO: return something useful
        return 0;
    }

}
