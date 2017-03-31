package kMean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import data.*;


public class KMeans {

    private KMeans() {}

	public static List<KMeanCluster> KMeansPartition(int k, ArrayList<Iris> data) {
        Tuple tuple = getKInitialClustersFrom(k, data);
        List<KMeanCluster> clusters = tuple.initialClusters, prev = null;
        List<Iris> remIrises = tuple.remainingIrises;
        while (!clusters.equals(prev)) {
            prev = clusters
                    .stream()
                    .map(cl -> new KMeanCluster(cl.getMembersAndClear()))
                    .collect(Collectors.toList());
            for (Iris iris : remIrises) {
                double minDist = Double.POSITIVE_INFINITY, tmp;
                KMeanCluster minCluster = null;
                for (KMeanCluster cluster : clusters) {
                    if ((tmp = cluster.distFromCentroid(iris)) < minDist) {
                        minDist = tmp;
                        minCluster = cluster;
                    }
                }
                minCluster.addMember(iris);
            }
            clusters.forEach(KMeanCluster::updateCentroid);
            if (remIrises != data)
                remIrises = data;
        }
        return clusters;
	}

	private static Tuple getKInitialClustersFrom(int k, ArrayList<Iris> data) {
        return new Tuple(
                data.subList(0, k)
                        .stream()
                        .map(KMeanCluster::new)
                        .collect(Collectors.toList()),
                data.subList(k, data.size())
        );
    }

    private static class Tuple {
        private final List<Iris> remainingIrises;
        private final List<KMeanCluster> initialClusters;
        Tuple(List<KMeanCluster> initialClusters, List<Iris> remainingIrises) {
            this.initialClusters = initialClusters;
            this.remainingIrises = remainingIrises;
        }
    }
}
