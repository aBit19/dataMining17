package kMean;
import java.util.ArrayList;

import data.Iris;

//ToDo: Compute cluster mean based on cluster members.
public class KMeanCluster {

	private final ArrayList<Iris> clusterMembers;
	private Iris centroid;
	public KMeanCluster() {
	    clusterMembers = new ArrayList<>();
    }

    public void addMember(Iris iris) {
	    if (clusterMembers.isEmpty())
	        centroid = iris;
	    else
	        centroid = centroid.getDistanceFrom(iris);
        clusterMembers.add(iris);
    }

    public double distFromCentroid(Iris iris) {
	    return 0.0;
    }

    public int size() {
	    return clusterMembers.size();
    }

	@Override
	public String toString() {
		String toPrintString = "-----------------------------------CLUSTER START------------------------------------------" + System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder(toPrintString);
		for(Iris i : clusterMembers)
			sb.append(i.toString() + System.getProperty("line.separator"));
		sb.append("-----------------------------------CLUSTER END-------------------------------------------" + System
                .getProperty("line.separator"));
		
		return sb.toString();
	}

}
