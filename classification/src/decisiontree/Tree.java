package decisiontree;

import data.Mushroom;

interface Tree {

    default Enum classify(Mushroom m) {
        Tree t;
        for (t = this; t instanceof Node; t = t.run(m)){}
        return ((Leaf) t).label;
    }

    Tree run(Mushroom m);
}
