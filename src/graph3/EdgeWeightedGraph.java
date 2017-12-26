package graph3;

import graph1.Bag;

import java.util.Scanner;

/**
 * Created by YangYuan on 2017/12/26.
 */
public class EdgeWeightedGraph {
    private int V;  // 顶点总数
    private int E;  // 边的总数
    private Bag<Edge>[] adj;    // 邻接表

    /**
     * 创建一幅含有V个顶点的空图
     * @param V
     */
    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = E;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Edge>();
        }
    }

    /**
     * 从输入流中读取图
     * @param scanner
     */
    public EdgeWeightedGraph(Scanner scanner) {
    }

    /**
     * 图的顶点
     * @return
     */
    public int V() {
        return V;
    }

    /**
     * 图的边数
     * @return
     */
    public int E() {
        return E;
    }

    /**
     * 向图中添加一条边e
     * @param e
     */
    public void addEdge(Edge e) throws Exception {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    /**
     * 和v相关联的所有边
     * @param v
     * @return
     */
    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    /**
     * 图的所有边
     * @return
     */
    public Iterable<Edge> edges() throws Exception {
        Bag<Edge> b = new Bag<Edge>();
        for (int v = 0; v < this.V; v++) {
            for (Edge e : adj[v]) {
                if (e.other(v) > v) {
                    b.add(e);
                }
            }
        }
        return b;
    }

    @Override
    public String toString() {
        return "HELLO WORLD!";
    }
}
