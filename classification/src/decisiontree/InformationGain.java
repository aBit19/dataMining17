package decisiontree;

import data.Mushroom;
import data.Util;
import enums.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

//Enforce singleton property.
public enum InformationGain implements IAttributeSelection {
    INSTANCE;
    private final boolean multiwayEnabled = true;

    public static InformationGain getInstance() {
        return INSTANCE;
    }

    @Override
    public SplittingCriterion split(List<Mushroom> mushrooms, List<Object> attributes) {
        Object attribute = getAttributeWithMaxInformationGain(mushrooms, attributes);
        if (attribute == null)
            System.out.println(1);
        List<Predicate<Mushroom>> predicates = new ArrayList<>();
        for (Object outcome : Mushroom.getAttributeOutcomes(attribute)) {
           predicates.add(m -> m.getAttributeValue(attribute).equals(outcome));
        }

        return new Criterion(true, predicates, attribute);
    }

    private static Object getAttributeWithMaxInformationGain(List<Mushroom> partition, List<Object> attributes) {
        if (attributes.size() == 16)
            System.out.printf("1");
        double min = Double.MAX_VALUE, curr;
        Object attrWithMinInfo = null;
        for (Object attribute : attributes) {
            curr = infoInRegardsToAttribute(partition, attribute);
            if (curr < min) {
                min = curr;
                attrWithMinInfo = attribute;
            }
        }

        return attrWithMinInfo;
    }

    private static double info(List<Mushroom> list) {
        double size = list.size();
        double countEdible = Util.filter(list, m -> m.m_Class.equals(Class_Label.edible)).size();
        double res =  - calculateTerm(countEdible/size) - calculateTerm((size - countEdible)/size);
        return res;
    }

    private static double calculateTerm(double probability) {
        if (probability == 0.0)
            return 0.0;
        if (probability == 1.0)
            probability = 0.9999;
        return probability * Math.log(probability);
    }

    private static double infoInRegardsToAttribute(List<Mushroom> list, Object attribute) {
        List<Mushroom> tuplesHavingOutcome;
        double weight, sum = 0.0, partitionSize = list.size();
        for (Object outcome : Mushroom.getAttributeOutcomes(attribute)) {
            tuplesHavingOutcome = Util.filter(list, m -> m.getAttributeValue(attribute).equals(outcome));
            weight = tuplesHavingOutcome.size()/ partitionSize;
            sum += weight != 0 ? weight * info(tuplesHavingOutcome) : 0;
        }
        return sum;
    }


    @Override
    public boolean multiWaySplittingEnabled() {
        return multiwayEnabled;
    }

    private static class Criterion implements SplittingCriterion {

        private final List<Predicate<Mushroom>> tests = new ArrayList<>();
        private final Object attribute;
        private final boolean isDiscrete;

        Criterion(boolean isDiscrete, List<Predicate<Mushroom>> predicates, Object attribute) {
            this.tests.addAll(predicates);
            this.attribute = attribute;
            this.isDiscrete = isDiscrete;
        }

        @Override
        public Object getAttr() {
            return  attribute;
        }

        @Override
        public Iterable<Predicate<Mushroom>> getOutcomes() {
            return tests;
        }

        @Override
        public boolean isDiscrete() {
            return isDiscrete;
        }
    }
}
