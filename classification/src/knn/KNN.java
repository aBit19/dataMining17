package knn;

import data.Mushroom;
import enums.Class_Label;

import java.io.IOException;
import java.util.*;

public class KNN {
    final int neighbors;
    final List<Mushroom> lm;

    public KNN(int neighbors, List<Mushroom> lm) {
        this.neighbors = neighbors;
        this.lm = lm;
    }

    public Class_Label classify(Mushroom testM) throws IOException {
        List<TupleMush> mt = new ArrayList<>();
        for(Mushroom mushroom : lm)
            mt.add(new TupleMush(testM.computeDistance(mushroom), mushroom));

        mt.sort((t1, t2) -> t1.dist.compareTo(t2.dist));
        int edible = 0, poisonous = 0;

        for (TupleMush t : mt.subList(0, neighbors)) {
            if (t.m.m_Class.equals(Class_Label.edible)) edible++;
            else poisonous++;
        }
        return   poisonous > edible ? Class_Label.poisonous : Class_Label.edible;
    }

    private class TupleMush  {
        final Double dist;
        public final Mushroom m;

        public TupleMush(double dist, Mushroom m) {
            this.dist = dist;
            this.m = m;
        }
    }

    public static void main(String[] args) {

    }

}