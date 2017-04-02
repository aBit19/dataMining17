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
        assignObjectsToClosestCluster(remIrises, clusters);

        double prevCost = Double.MAX_VALUE, newCost = 0.0;
        while (prevCost != newCost) {
            for (Iris iris : data) {
                for (KMedoidCluster cluster : clusters) {
                    if (!cluster.isRepresantitiveObject(iris)) {
                        prevCost = getCostAndClear(clusters);
                        Iris tmp = cluster.getAndSetMedoid(iris);
                        assignObjectsToClosestCluster(data, clusters);
                        newCost = getCostOf(clusters);
                        if (newCost <= prevCost) {
                            List<Iris> members = cluster.getMembersAndClear();
                            assignObjectsToClosestCluster(members, clusters);
                        } else {
                            cluster.getAndSetMedoid(tmp);
                            assignObjectsToClosestCluster(data, clusters);
                        }
                    }
                }
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
