package utils;

import tdas.StaticGraph;

public class StaticGraphUtil {


    //EJERCICIO 2 A =================================================================

    public static int[][] getLargestEquivalenceSubgraph(StaticGraph graph) {
        int n = graph.getTotalNodes();
        int[][] adjMatrix = graph.getAdjacencyMatrix();
        boolean[] visited = new boolean[n];
        int[][] largestSubgraph = new int[n][n];

        int[] componentSize = new int[n];
        int largestComponentIndex = -1;
        int largestComponentSize = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int size = dfs(i, adjMatrix, visited, componentSize, n);
                if (size > largestComponentSize) {
                    largestComponentSize = size;
                    largestComponentIndex = i;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (componentSize[i] == largestComponentSize) {
                for (int j = 0; j < n; j++) {
                    if (componentSize[j] == largestComponentSize) {
                        largestSubgraph[i][j] = adjMatrix[i][j];
                    }
                }
            }
        }

        return largestSubgraph;
    }

    private static int dfs(int node, int[][] adjMatrix, boolean[] visited, int[] componentSize, int n) {
        visited[node] = true;
        int size = 1;
        componentSize[node] = size;

        for (int i = 0; i < n; i++) {
            if (adjMatrix[node][i] != 0 && !visited[i]) {
                size += dfs(i, adjMatrix, visited, componentSize, n);
            }
        }

        return size;
    }

    //EJERCICIO 2 A =================================================================

}
