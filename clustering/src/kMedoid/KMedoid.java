package kMedoid;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

import data.Iris;
import kMean.KMeanCluster;
import util.Tuple;

final public class KMedoid {

    public static ArrayList<KMedoidCluster> KMedoidPartition(int k, ArrayList<Iris> data) {
        Tuple tuple = Tuple.getKInitialClustersAndRemainingObjectsFrom(k, data, KMeanCluster::new);
        List<KMedoidCluster> clusters = tuple.getInitialClusters();
        List<Iris> remIrises = tuple.getRemainingIrises();

        double prevCost = Double.MAX_VALUE, newCost = 0.0;
        while (prevCost != newCost) {
            assignObjectsToClosestCluster(remIrises, clusters);
            Iris random = getNonRepresentantiveObject(data, clusters);
            for (KMedoidCluster cluster : clusters) {
                prevCost = getCostAndClear(clusters);
                Iris tmp = cluster.getAndSetMedoid(random);
                assignObjectsToClosestCluster(data, clusters);
                newCost = getCostOf(clusters);
            }
        }
	    return null;
	}
    private static void assignObjectsToClosestCluster(List<Iris> irises, List<KMedoidCluster> clusters) {
        for (Iris iris : irises) {
            double min = Double.MAX_VALUE, tmp;
            KMedoidCluster closestCluster = null;
            for (KMedoidCluster cl : clusters) {
                if ((tmp = cl.getDistFromMemoidTo(iris)) < min) {
                    min = tmp;
                    closestCluster = cl;
                }
            }
            closestCluster.addMember(iris);
        }
    }

    private static Iris getNonRepresentantiveObject(List<Iris> objects, List<KMedoidCluster> clusters) {
        return objects.stream().
                filter(iris -> clusters
                        .stream()
                        .allMatch(cluster -> !cluster.isRepresantitiveObject(iris)))
                .findFirst().get();
    }
    private static double getCostOf(List<KMedoidCluster> clusters) {
        return getCostAnd(clusters, KMedoidCluster::getCost);
    }

	private static double getCostAndClear(List<KMedoidCluster> clusters) {
        return  getCostAnd(clusters, KMedoidCluster::getCostAndClear);
    }

    private static double getCostAnd(List<KMedoidCluster> clusters, ToDoubleFunction<KMedoidCluster> f) {
        return clusters.stream().mapToDouble(f).summaryStatistics().getAverage();
    }

}
