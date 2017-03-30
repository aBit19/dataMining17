
import java.util.*;
import java.util.stream.Collectors;

/***
 * The ItemSet class is used to store information concerning a single transaction.
 * Should not need any changes.
 *
 */
public class ItemSet  implements Comparable<ItemSet>{
	
	/***
	 * The PRIMES array is internally in the ItemSet-class' hashCode method
	 */
	private static final int[] PRIMES = { 2, 3, 5, 7, 11, 13, 17, 23, 27, 31, 37 };
    int[] set;
    SubsetGenerator subsetGenerator;

    /***
     * Creates a new instance of the ItemSet class.
     * @param set Transaction content
     */
    public ItemSet( int[] set ) {
        this.set = set;
        subsetGenerator = new SubsetGenerator(set);
    }

    public Set<ItemSet> getSubsets() {
        return subsetGenerator.getSubsets(set);
    }

    @Override
    /**
     * hashCode functioned used internally in Hashtable
     */
    public int hashCode() {
        int code = 0;
        for (int i = 0; i < set.length; i++) {
            code += set[i] * PRIMES[i];
        }
        return code;
    }

    
    @Override
    /**
     * Used to determine whether two ItemSet objects are equal
     */
    public boolean equals( Object o ) {
        if ( this == o)
            return true;
        return o instanceof ItemSet && Arrays.equals(set, ((ItemSet) o).set);
    }

    /**o
     * Two itemsets are joinable if their k-2 elements are equal.
     * The last test ensures that no duplicates are generated.
     * @param other
     * @return
     */
    public boolean isJoinableWith(ItemSet other) {
        if (other.set.length != set.length)
            return false;
        for (int i = 0; i < set.length - 1; i++)
            if (set[i] != other.set[i])
                return false;
        return set[set.length - 1] != other.set[set.length - 1];
    }

    @Override
    public int compareTo(ItemSet itemSet) {
        if (set.length != itemSet.set.length) {
            return set.length - itemSet.set.length;
        }
        for (int i = 0; i < set.length; i++)
            if (set[i] != itemSet.set[i] )
                return set[i] - itemSet.set[i];
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        int i;
        for (i = 0; i < set.length - 1; i++)
            sb.append(String.valueOf(set[i])).append(", ");
        sb.append(String.valueOf(set[i])).append("} ");
        return sb.toString();
    }

    private static class SubsetGenerator {
        private Set<ItemSet> subsets;
        private final int[] set;

        SubsetGenerator(int[] set) {
            this.set = set;
        }
        Set<ItemSet> getSubsets(int[] set) {
            return calculateSubsets(set);
        }

        static Set<ItemSet> calculateSubsets(int[] set) {
            Set<Integer> s = box(set);
            List<List<Set<Integer>>>  subsets  = new ArrayList<>(set.length);
            for (int i = 0; i < set.length; i++)
                subsets.add(i, new ArrayList<>());
            for (int i : set) {
               Set<Integer> tmp = new TreeSet<>();
               tmp.add(i);
               subsets.get(0).add(tmp);
            }

            //size of subsets being generated
            for (int i = 0; i < set.length - 1; i++) {
                for (Set<Integer> previous : subsets.get(i)) {
                    for (Set<Integer> subsets1 : subsets.get(0)) {
                        Set<Integer> tmp = new TreeSet<>();
                        tmp.addAll(previous);
                        tmp.addAll(subsets1);
                        subsets.get(i + 1).add(tmp);
                    }
                }
                Integer tmp = new Integer(i);
                List<Set<Integer>> subsetOfSizeI = subsets.get(i + 1)
                        .stream()
                        .filter(tm -> tm.size() == tmp.intValue() + 2)
                        .collect(Collectors.toList());
                subsets.remove(i + 1);
                subsets.add(i + 1, subsetOfSizeI);
            }
            return subsets.stream().reduce((ls1, ls2) -> {
              List<Set<Integer>> lists = new ArrayList<>();
              lists.addAll(ls1);
              lists.addAll(ls2);
              return lists;
            }).get().stream()
                    .filter(se -> se.size() < set.length )
                    .map(set1 -> new ItemSet(unbox(set1)))
                    .collect(Collectors.toSet());
        }

        private static int[] unbox(Set<Integer> set) {
           int[] t = new int[set.size()];
           int count = 0;
            for (Integer i: set)
                t[count++] = i;
            return t;
        }
        private static Set<Integer> box(int[] set) {
            TreeSet<Integer> tmp = new TreeSet<>();
            for (int i: set)
                tmp.add(i);
            return tmp;
        }

        static int getParagontiko(int n) {
            return getParagontiko(n, 1);
        }

        static int getParagontiko(int n, int r) {
            for (int i = n; i > r + 1; n *= --i) ;
            return n;
        }
        private int getRCombination(int n, int r) {
            if (n == r)
                return 1;
            if (n < r)
                throw new IllegalArgumentException("There is no way to make subsets of size:" + r + "from a subset of size" + n);
            int diff = n - r;
            if (diff > r) {
                int tmp = r;
                r = diff;
                diff = tmp;
            }
            return getParagontiko(n, r) / getParagontiko(diff);
        }
    }

    public static void main(String[] args) {
        int[] test = new int[4];
        for (int i = 0; i < 4; i++) {
            test[i] = i;
        }
        ItemSet i = new ItemSet(test);
        Set<ItemSet> a = i.getSubsets();
        System.out.println(1);
    }
}
