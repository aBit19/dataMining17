import java.util.Arrays;

/***
 * The ItemSet class is used to store information concerning a single transaction.
 * Should not need any changes.
 *
 */
public class ItemSet {
	
	/***
	 * The PRIMES array is internally in the ItemSet-class' hashCode method
	 */
	private static final int[] PRIMES = { 2, 3, 5, 7, 11, 13, 17, 23, 27, 31, 37 };
    int[] set;

    /***
     * Creates a new instance of the ItemSet class.
     * @param set Transaction content
     */
    public ItemSet( int[] set ) {
        this.set = set;
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

    /**
     * Two itemsets are joinable if their k-2 elements are equal.
     * The last test ensures that no duplicates are generated.
     * @param other
     * @return
     */
    public boolean isJoinableWith(ItemSet other) {
        if (other.set.length != set.length)
            return false;
        for (int i = 0; i < set.length - 2; i++)
            if (set[i] != other.set[i])
                return false;
        return set[set.length - 1] < other.set[set.length - 1];
    }
}
