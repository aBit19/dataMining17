package kMedoid;

import java.util.ArrayList;

import data.Iris;
import util.Tuple;

public class KMedoid {

    public static ArrayList<KMedoidCluster> KMedoidPartition(int k, ArrayList<Iris> data) {
        Tuple tuple = Tuple.getKInitialClustersFrom(KMedoidCluster::new, k, data);
	    return null;

	}
	
}
