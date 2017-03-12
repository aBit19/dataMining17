package decisiontree;

import data.Mushroom;
import java.util.List;

public interface IAttributeSelection {

    SplittingCriterion split(List<Mushroom> lm, List<Object> attribute);
    boolean multiWaySplittingEnabled();
}
