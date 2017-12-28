package graph3;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 最小生成树 PRIM算法的延时实现
 * Created by YangYuan on 2017/12/26.
 */
public class LazyPrimMST {
    private boolean[] marked;           // 最小生成树的顶点
    private Queue<Edge> mst;            // 最小生成树的边
    private PriorityQueue<Edge> pq;     // 横切边(这里包括已经失效的边)

    public LazyPrimMST(EdgeWeightedGraph G) throws Exception {
        pq = new PriorityQueue<Edge>();
        marked = new boolean[G.V()];
        mst = new ArrayDeque<Edge>(G.V() - 1);// 最后有且只有v-1条边
        visit(G, 0);    // 暂定输入的图G是连通的
        while (! pq.isEmpty()) {
            Edge e = pq.poll();         // 优先级队列出队
            int v = e.either();
            int w = e.other(v);
            //  两个顶点都已经在最小生成树中，那么这两个点之间的边也就失效了
            if (marked[v] && marked[w]) {
                continue;// 跳过失效的边
            }
            mst.offer(e);   // 将边添加到树中
            // 将顶点(v或w)添加到树中
            if (! marked[v]) {
                visit(G, v);
            }
            if (! marked[w]) {
                visit(G, w);
            }
        }
    }

    public void visit(EdgeWeightedGraph G, int v) throws Exception {
        marked[v] = true;// 标记v为已经搜索
        for (Edge e : G.adj(v)) {
            if (! marked[e.other(v)]) {
                pq.offer(e);
            }
        }
    }

    /**
     * 最小生成树的所有边
     * @return
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * 最小生成树的权重
     * @return
     */
    public double weight() {
        // TODO
        return 0;
    }
}
