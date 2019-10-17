package peterfajdiga.flexfish.evolver.chromosome.expressor;

import java.util.Arrays;
import java.util.List;

public class SequentialListView<T> {
    private List<T> list;

    public SequentialListView(final List<T> list) {
        this.list = list;
    }

    public SequentialListView(final T[] array) {
        this.list = Arrays.asList(array);
    }

    public T read() {
        final T item = list.get(0);
        list = list.subList(1, list.size());
        return item;
    }

    public boolean hasNext() {
        return !list.isEmpty();
    }
}
