package decisiontree;

import data.Mushroom;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Node implements Tree {
    private final List<TransitionPredicate> transitionPredicates = new ArrayList<>();

    public void addCase(Predicate<Mushroom> f, Tree t) {
        transitionPredicates.add(new TransitionPredicate(f, t));
    }

    @Override
    public Tree run(Mushroom m) {
        for (TransitionPredicate tr : transitionPredicates)
            if (tr.f.test(m))
                return tr.tree;
        throw new IllegalArgumentException("No predicate matching these mushrooms: " + m.toString());
    }

    private static class TransitionPredicate {
        private final Predicate<Mushroom> f;
        private final Tree tree;

        TransitionPredicate(Predicate<Mushroom> f, Tree tree) {
            this.f = f;
            this.tree = tree;
        }
    }
}


