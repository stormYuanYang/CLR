package graph3;

import java.util.PriorityQueue;

/**
 * Created by yangyuan on 2017/12/28.
 */
public class PrimMST {
    private Edge[] edgeTo;              // 距离树最近的边
    private double[] distTo;            // 边的的权重
    private boolean[] marked;           // 如果v在树中则为true
    private PriorityQueue<Integer> pq;   // 有效横切边

    public PrimMST(EdgeWeightedGraph G) throws Exception {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        pq = new PriorityQueue<>(G.V());

        distTo[0] = 0F;
        pq.offer(0);
        while (! pq.isEmpty()) {
            visit(G, pq.poll());
        }
    }

    private void visit(EdgeWeightedGraph G, int v) throws Exception {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w])// v-w失效
                continue;
            if (e.weight() < distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (! pq.contains(w)) {
                    pq.offer(w);
                }
            }
        }
    }

    public Iterable<Edge> edges() {
        return null;
    }

    public double weight() {
        double result = 0;
        for (double dist : distTo) {
            result += dist;
        }
        return result;
    }
}
