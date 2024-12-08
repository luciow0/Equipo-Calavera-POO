package tdas;

import tdas.Queue;

public class StaticQueue implements Queue {

    private static final int MAX = 10000;
    private final int[] array;
    private boolean[] marked;
    private int count;

    public StaticQueue() {
        this.array = new int[MAX];
        this.marked = new boolean[MAX];
        this.count = 0;
    }

    @Override
    public int getFirst() {
        if (isEmpty()) {
            throw new RuntimeException("No se puede obtener el primero de una cola vacía");
        }
        return array[0];
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new RuntimeException("No se puede eliminar el primero de una cola vacía");
        }
        for (int i = 0; i < count - 1; i++) {
            this.array[i] = this.array[i + 1];
            this.marked[i] = this.marked[i + 1];
        }
        count--;
    }

    @Override
    public void add(int value) {
        if (this.count == MAX) {
            throw new RuntimeException("La cola está llena");
        }
        this.array[this.count] = value;
        this.marked[this.count] = false;
        this.count++;
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public void mark(int value) {
        for (int i = 0; i < count; i++) {
            if (array[i] == value) {
                marked[i] = true;
                return;
            }
        }
        throw new RuntimeException("Elemento no encontrado para marcar");
    }

    @Override
    public void unmark(int value) {
        for (int i = 0; i < count; i++) {
            if (array[i] == value) {
                marked[i] = false;
                return;
            }
        }
        throw new RuntimeException("Elemento no encontrado para desmarcar");
    }

    @Override
    public boolean isMarked(int value) {
        for (int i = 0; i < count; i++) {
            if (array[i] == value) {
                return marked[i];
            }
        }
        return false;
    }

    @Override
    public int[] toArray() {
        int[] result = new int[this.count];
        System.arraycopy(this.array, 0, result, 0, this.count);
        return result;
    }
}
