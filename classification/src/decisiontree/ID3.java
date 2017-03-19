package decisiontree;

import data.Mushroom;
import data.Util;
import enums.Class_Label;

import javax.swing.*;
import java.util.List;
import java.util.function.Predicate;

public class ID3 {
    private final Tree tree;
    private static final IAttributeSelection informationGain = InformationGain.INSTANCE;

    public ID3(List<Mushroom> partition, List<Object> attributeList) {
        tree = generateTree(partition, attributeList, informationGain);
    }

    public Tree getTree() {
        return this.tree;
    }

    private static Tree generateTree(List<Mushroom> partition,
                                     List<Object> attrList,
                                     IAttributeSelection attrSelectionMethod) {
        Node node = new Node();
        if (partitionIsOfSameClass(partition))
             return new Leaf(partition.get(0).m_Class);

        if (attrList.isEmpty())
            return new Leaf(getMajorityClassFrom(partition));

        SplittingCriterion criterion = attrSelectionMethod.split(partition, attrList);
        if (criterion.isDiscrete() && attrSelectionMethod.multiWaySplittingEnabled())
            attrList.remove(criterion.getAttr());

        for (Predicate<Mushroom> predicate : criterion.getOutcomes()) {
            List<Mushroom> list = Util.filter(partition, predicate);
            if (!list.isEmpty())
                node.addCase(predicate, generateTree(list, attrList, attrSelectionMethod));
        }
        return node;
    }

    private static boolean partitionIsOfSameClass(List<Mushroom> lm ) {
        Class_Label sample = lm.get(0).m_Class;
        return lm.stream().allMatch(m -> m.m_Class.equals(sample));
    }

    private static Class_Label getMajorityClassFrom(List<Mushroom> partition) {
        int edible = Util.filter(partition, m -> m.m_Class.equals(Class_Label.edible)).size();
        return edible > partition.size()/2 ? Class_Label.edible : Class_Label.poisonous;
    }

    public static void main(String[] args) {
        List<Mushroom> data =Util.getData(),
        trainingSet = data.subList(0, 2500),
                testSet = data.subList(2500, 3000);
        ID3 id3 = new ID3(trainingSet, Mushroom.getAttributeList());
        Tree tree = id3.getTree();

        int correct = 0;
        for (Mushroom m : testSet) {
            if (tree.classify(m).equals(m.getAttributeValue(Class_Label.class)))
                correct++;
        }

        System.out.println("correct predictions: " + correct);
    }
}
