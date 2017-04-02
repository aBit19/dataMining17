package kMedoid;
import java.util.ArrayList;
import java.util.List;

import data.Iris;

final public class KMedoidCluster {
    private final ArrayList<Iris> clusterMembers;
    private Iris medoid;

    public KMedoidCluster(Iris medoid) {
        this.clusterMembers = new ArrayList<>();
        this.medoid = medoid;
    }

    public Iris getAndSetMedoid(Iris iris) {
        Iris tmp = medoid;
        medoid = iris;
        return tmp;
    }

    public double getDistFromMemoidTo(Iris iris) {
        return medoid.getDistanceFrom(iris);
    }

    public void addMember(Iris iris) {
        clusterMembers.add(iris);
    }

    public double getCostAndClear() {
        double q = getCost();
        clusterMembers.clear();
        return q;
    }

    public List<Iris> getMembersAndClear() {
        List<Iris> tmp = clusterMembers;
        clusterMembers.clear();
        return tmp;
    }

    public double getCost() {
        return  clusterMembers
                .parallelStream()
                .mapToDouble(medoid::getDistanceFrom)
                .sum();
    }


    public boolean isRepresantitiveObject(Iris iris) {
        return iris == medoid;
    }
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder( "-----------------------------------CLUSTER START------------------------------------------" + System.getProperty("line.separator"))
                .append("medoid: "+this.medoid.toString() + System.getProperty("line.separator"));

		for(Iris i : this.clusterMembers)
			sb.append(i.toString()).append(System.getProperty("line.separator"));

		 sb.append("-----------------------------------CLUSTER END-------------------------------------------" + System
                .getProperty("line.separator"));
		
		return sb.toString();
	}
	
}
