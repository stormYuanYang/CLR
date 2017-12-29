package graph3;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by YangYuan on 2017/12/29.
 */
public class KruskalMST {
    private Queue<Edge> mst;

    public KruskalMST(EdgeWeightedGraph G) {
        mst = new ArrayDeque<Edge>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(G.E());
    }
}
