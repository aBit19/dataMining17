package decisiontree;

import data.Mushroom;
import enums.Class_Label;

public class Leaf implements Tree {
    public final Class_Label label;

    public Leaf(Class_Label label) {
        this.label = label;
    }


    @Override
    public Tree run(Mushroom m) {
        return this;
    }
}
