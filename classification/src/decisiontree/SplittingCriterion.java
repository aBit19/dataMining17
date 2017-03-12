package decisiontree;

import data.Mushroom;

import java.util.function.Predicate;

public interface SplittingCriterion {
    boolean isDiscrete();
    Iterable<Predicate<Mushroom>> getOutcomes();
    Object getAttr();
}
