package tdas;

public interface Queue {
    int getFirst();
    void remove();
    void add(int value);
    boolean isEmpty();

    //EJERCICIO 1 A
    void mark(int value);
    void unmark(int value);
    boolean isMarked(int value);
    int[] toArray();
}
