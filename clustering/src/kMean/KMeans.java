package kMean;
import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.IREM;
import data.*;


public class KMeans {

    private KMeans() {}

	public static ArrayList<KMeanCluster> KMeansPartition(int k, ArrayList<Iris> data) {
		Iris[] initialClusters = getInitialClustersFrom(k, data);
		return null;
	}

	private static Iris[] getInitialClustersFrom(int k, ArrayList<Iris> data) {
        Iris[] init = new Iris[k];
        for (int i = 0; i < k; i++) {
            init[i] = data.get(i);
            data.remove(init[i]);
        }
        return init;
    }

}
