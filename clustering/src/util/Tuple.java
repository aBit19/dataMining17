package util;
import data.Iris;
import kMean.KMeanCluster;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Tuple<T> {

    private final List<T> initialClusters;
    private final List<Iris> remainingIrises;

    private Tuple(List<T> initialClusters, List<Iris> remainingIrises) {
        this.initialClusters = initialClusters;
        this.remainingIrises = remainingIrises;
    }

    public List<T> getInitialClusters() {
        return initialClusters;
    }

    public List<Iris> getRemainingIrises() {
        return remainingIrises;
    }

    public static<T> Tuple getKInitialClustersAndRemainingObjectsFrom(int k, ArrayList<Iris> data, Function<Iris, T> f) {
        return new Tuple(
                data.subList(0, k)
                        .stream()
                        .map(f)
                        .collect(Collectors.toList()),
                data.subList(k, data.size())
        );
    }

}
