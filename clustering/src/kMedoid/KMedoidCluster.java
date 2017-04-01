package kMedoid;
import java.util.ArrayList;
import data.Iris;

public class KMedoidCluster {
    private final ArrayList<Iris> ClusterMembers;
    private Iris Medoid;

    public KMedoidCluster(Iris medoid) {
        this.ClusterMembers = new ArrayList<>();
        this.Medoid = medoid;
    }
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder( "-----------------------------------CLUSTER START------------------------------------------" + System.getProperty("line.separator"))
                .append("Medoid: "+this.Medoid.toString() + System.getProperty("line.separator"));

		for(Iris i : this.ClusterMembers)
			sb.append(i.toString()).append(System.getProperty("line.separator"));

		 sb.append("-----------------------------------CLUSTER END-------------------------------------------" + System
                .getProperty("line.separator"));
		
		return sb.toString();
	}
	
}
