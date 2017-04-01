package kMean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import data.*;
import util.Tuple;


public class KMeans {

    private KMeans() {}

	public static List<KMeanCluster> KMeansPartition(int k, ArrayList<Iris> data) {
        Tuple tuple = Tuple.getKInitialClustersFrom(KMeanCluster::new, k, data);
        List<KMeanCluster> clusters = tuple.getInitialClusters(), prev = null;
        List<Iris> remIrises = tuple.getRemainingIrises();
        while (!clusters.equals(prev)) {
            prev = getSnapshotOfAndClear(clusters);
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

	private static List<KMeanCluster> getSnapshotOfAndClear(List<KMeanCluster> clusters) {
        return clusters
                .stream()
                .map(cl -> new KMeanCluster(cl.getMembersAndClear()))
                .collect(Collectors.toList());
    }
}
