package tdas;

import java.util.Random;

public class StaticGraph implements Graph {

    private static final int MAX_NODES = 15;

    private final int[][] adjacencyMatrix;
    private final Dictionary dictionary;
    private int totalNodes;

    public StaticGraph() {
        this.adjacencyMatrix = new int[MAX_NODES][MAX_NODES];
        this.dictionary = new StaticDictionary(); // Asumo que el límite es mayor a MAX_NODES
        this.totalNodes = 0;
    }

    @Override
    public void addNode(int node) {
        if (this.totalNodes == 0) { // Greedy
            this.dictionary.add(node, this.totalNodes);
            this.totalNodes++;
            return;
        }

        // Esto se puede colocar dentro de un condicional this.totalNodes != 0
        Set nodes = this.dictionary.getKeys();
        while (!nodes.isEmpty()) {
            int current = nodes.choose();
            if (current == node) {
                throw new RuntimeException("El nodo ya existe");
            }
            nodes.remove(current);
        }

        this.dictionary.add(node, this.totalNodes);
        this.totalNodes++;
    }

    @Override
    public void removeNode(int node) {
        if (this.totalNodes == 0) { // Greedy
            throw new RuntimeException("El nodo no existe");
        }

        // Esto se puede colocar dentro de un condicional this.totalNodes != 0
        int before = this.totalNodes;
        Set nodes = this.dictionary.getKeys();
        int index = this.dictionary.get(node);
        int last = -1;
        while (!nodes.isEmpty()) {
            int current = nodes.choose();
            if (this.dictionary.get(current) == before - 1) {
                last = current;
            }
            if (current == node) {
                this.dictionary.remove(node, index);
                this.totalNodes--;
            }
            nodes.remove(current);
        }
        int after = this.totalNodes;

        if (last == node) {
            return;
        }

        if (before != after) { // Evito complejidad cúbica
            for (int i = 0; i < before; i++) {
                this.adjacencyMatrix[i][index] = this.adjacencyMatrix[i][after];
                this.adjacencyMatrix[index][i] = this.adjacencyMatrix[after][i];
            }

            this.dictionary.remove(last, this.dictionary.get(last));
            this.dictionary.add(last, index);
            return;
        }

        throw new RuntimeException("El nodo no existe");
    }

    @Override
    public Set nodes() {
        return this.dictionary.getKeys();
    }

    @Override
    public void addEdge(int from, int to) {
        if (this.notIn(from) || this.notIn(to)) {
            throw new RuntimeException("No existe alguno de los nodos");
        }

        int indexFrom = this.dictionary.get(from);
        int indexTo = this.dictionary.get(to);

        if (this.adjacencyMatrix[indexFrom][indexTo] != 0) {
            throw new RuntimeException("Ya existe la arista");
        }

        Random random = new Random();
        int x = random.nextInt(1, 100);

        this.adjacencyMatrix[indexFrom][indexTo] = x;
    }

    private double randomProbability() {
        return new Random().nextDouble();
    }

    private boolean notIn(int node) {
        Set nodes = this.dictionary.getKeys();
        while (!nodes.isEmpty()) {
            int current = nodes.choose();
            if (current == node) {
                return false;
            }
            nodes.remove(current);
        }
        return true;
    }

    @Override
    public void removeEdge(int from, int to) {
        if (!edgeExists(from, to)) {
            throw new RuntimeException("No existe la arista");
        }

        int indexFrom = this.dictionary.get(from);
        int indexTo = this.dictionary.get(to);

        this.adjacencyMatrix[indexFrom][indexTo] = 0;
    }

    @Override
    public boolean edgeExists(int from, int to) {
        if (notIn(from) || notIn(to)) {
            return false;
        }

        int indexFrom = this.dictionary.get(from);
        int indexTo = this.dictionary.get(to);

        return this.adjacencyMatrix[indexFrom][indexTo] != 0;
    }

    @Override
    public int weight(int from, int to) {
        if (!edgeExists(from, to)) {
            throw new RuntimeException("No existe la arista");
        }

        int indexFrom = this.dictionary.get(from);
        int indexTo = this.dictionary.get(to);

        return this.adjacencyMatrix[indexFrom][indexTo];
    }

    public int getTotalNodes() {
        return this.totalNodes;
    }

    public int[][] getAdjacencyMatrix() {
        return this.adjacencyMatrix;
    }


}
