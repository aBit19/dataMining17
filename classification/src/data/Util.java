package data;


import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Util {

    private Util() {
        throw new AssertionError("It is not supposed to be invoked.");
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> test) {
        return list.stream().parallel().filter(test).collect(Collectors.toList());
    }

    public static List<Mushroom> getData() {
        return DataManager.LoadData();
    }
}
