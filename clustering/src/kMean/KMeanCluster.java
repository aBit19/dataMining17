package kMean;
import java.util.ArrayList;
import java.util.List;

import data.Iris;

//ToDo: Compute cluster mean based on cluster members.
public class KMeanCluster {

	private List<Iris> clusterMembers;
	private Iris centroid;

	public KMeanCluster(Iris iris) {
	    centroid = iris;
	    clusterMembers = new ArrayList<>();
	    clusterMembers.add(iris);
    }

    public KMeanCluster(List<Iris> clusterMembers) {
	    this.clusterMembers = clusterMembers;
	    updateCentroid();
    }

    public void addMember(Iris iris) {
	    clusterMembers.add(iris);
    }

    public double distFromCentroid(Iris iris) {
	    return centroid.getDistanceFrom(iris);
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

	public void updateCentroid() {
        centroid = clusterMembers
                .stream()
                .reduce(Iris::getMean).get();
    }

    public List<Iris> getMembersAndClear() {
        List<Iris> tmp = clusterMembers;
        clusterMembers = new ArrayList<>();
        return tmp;
    }

	@Override
    public boolean equals(Object o) {
	    if (o == this)
	        return true;
	    if (! (o instanceof KMeanCluster))
	        return false;
	    KMeanCluster other = (KMeanCluster) o;
	    return centroid.equals(other.centroid)
                && clusterMembers.size() == other.clusterMembers.size()
                && clusterMembers.equals(other.clusterMembers);
    }
}
