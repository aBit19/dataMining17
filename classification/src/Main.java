import data.*;
import data.Util;
import knn.KNN;

import java.io.IOException;
import java.util.List;


/**
 * Main class to run program from.
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args)  throws IOException{
		// First step - Load data and convert to data.Mushroom objects.
		for (int i = 1; i <= 20 ; i++) {

			List<Mushroom> mushrooms = Util.getData();
			List<Mushroom> trainingSet = mushrooms.subList(0, 2000),
					testSet = mushrooms.subList(2000, 3000);
			KNN knn = new KNN(i, trainingSet);
			int errors = 0;
			for (Mushroom m : testSet) {
				if (!m.m_Class.equals(knn.classify(m)))
					errors++;
			}
			System.out.println("Number of errors: " + errors + " for i: " + i);
		}
	}

}
