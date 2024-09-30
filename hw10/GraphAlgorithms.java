import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Abdullojon Yusupov
 * @userid abdullojony
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List},
     * {@code java.util.Queue}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        Set<Vertex<T>> vSet = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> result = new ArrayList<>();

        queue.add(start);
        vSet.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> v = queue.remove();
            result.add(v);
            for (VertexDistance<T> vd : graph.getAdjList().get(v)) {
                if (!vSet.contains(vd.getVertex())) {
                    queue.add(vd.getVertex());
                    vSet.add(vd.getVertex());
                }
            }
        }

        return result;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start))
            throw new IllegalArgumentException();

        Set<Vertex<T>> vSet = new HashSet<>();
        List<Vertex<T>> result = new ArrayList<>();

        dfsHelper(start, graph, vSet, result);

        return result;
    }

    private static <T> void dfsHelper(Vertex<T> v, Graph<T> g, Set<Vertex<T>> vSet, List<Vertex<T>> result) {
        // mark v in a visited set
        vSet.add(v);
        result.add(v);

        // for all neighbors of v, if it is not visited recurse it
        for (VertexDistance<T> vd : g.getAdjList().get(v)) {
            if (!vSet.contains(vd.getVertex())) {
                dfsHelper(vd.getVertex(), g, vSet, result);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from {@code start}, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from {@code start} to every
     *          other node in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start))
            throw new IllegalArgumentException();

        // we need distance Map
        // we need priority queue
        // we need visited set
        Map<Vertex<T>, Integer> dsMap = new HashMap<>();
        PriorityQueue<VertexDistance<T>> pQueue = new PriorityQueue<>();
        Set<Vertex<T>> vSet = new HashSet<>();

        // we need to set all distances to infinity
        for (Vertex<T> v : graph.getVertices()) {
            dsMap.put(v, Integer.MAX_VALUE);
        }

        // we need to set start as zero and insert in priority Queue
        pQueue.add(new VertexDistance<>(start, 0));

        // we loop while pQueue is not empty and vSet is not full
        while (!pQueue.isEmpty() && vSet.size() < graph.getVertices().size()) {
            // we pop least element from pQueue and visit it
            VertexDistance<T> e = pQueue.remove();

            // we check if it's not already visited
            if (vSet.contains(e.getVertex())) continue;

            // we visit and update the distance
            vSet.add(e.getVertex());
            dsMap.put(e.getVertex(), e.getDistance());

            // for all neighbors of e we update their distance and insert them in pQueue
            for (VertexDistance<T> vd : graph.getAdjList().get(e.getVertex())) {
                if (!vSet.contains(vd.getVertex())) {
                    pQueue.add(new VertexDistance<>(vd.getVertex(), e.getDistance() + vd.getDistance()));
                }
            }
        }

        return dsMap;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start))
            throw new IllegalArgumentException();
        
        // we need priority queue for edges
        // we need visited set
        // we need set of edges for MST
        Queue<Edge<T>> pQueue = new PriorityQueue<>();
        Set<Vertex<T>> vSet = new HashSet<>();
        Set<Edge<T>> mst = new HashSet<>();

        // add starting edges to pQueue and visit start
        for (VertexDistance<T> vd : graph.getAdjList().get(start)) {
            pQueue.add(new Edge<>(start, vd.getVertex(), vd.getDistance()));
        }
        vSet.add(start);

        // loop until pQueue is not empty and vSet is not full
        while (!pQueue.isEmpty() && vSet.size() < graph.getVertices().size()) {
            // pop edge with minimum weight and add it to vSet and mst if it's not visited
            Edge<T> e = pQueue.remove();
            if (vSet.contains(e.getV())) continue;
            vSet.add(e.getV());
            mst.add(e);
            mst.add(new Edge<>(e.getV(), e.getU(), e.getWeight()));

            // add neighboring edges to pQueue if they are not in mst or vSet
            for (VertexDistance<T> vd : graph.getAdjList().get(e.getV())) {
                if (!vSet.contains(vd.getVertex())) {
                    pQueue.add(new Edge<>(e.getV(), vd.getVertex(), vd.getDistance()));
                }
            }
        }

        return mst;
    }
}
