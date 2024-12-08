package tdas;
public interface Graph {

    void addNode(int node);
    void removeNode(int node);
    Set nodes();
    void addEdge(int from, int to);
    void removeEdge(int from, int to);
    boolean edgeExists(int from, int to);
    int weight(int from, int to);

}
