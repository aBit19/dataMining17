import java.util.ArrayList;
import java.util.List;

import kMean.KMeanCluster;
import kMean.KMeans;
import kMedoid.KMedoid;
import kMedoid.KMedoidCluster;
import data.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//First step load in iris data
		ArrayList<Iris> irisData = DataLoader.LoadAllIrisData();
		
		//Second step --> do the clustering using k-means!
		List<KMeanCluster> FoundClusters_KMeans = KMeans.KMeansPartition(4, irisData);
		FoundClusters_KMeans.forEach(System.out::println);
		//Third step --> do the clustering using k-medoids!
		ArrayList<KMedoidCluster> FoundClusters_KMedoids = KMedoid.KMedoidPartition(3, irisData);
	}

}
