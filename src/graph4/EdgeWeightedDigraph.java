package graph4;

import graph1.Bag;
import java.util.Scanner;

/**
 * Created by YangYuan on 2017/12/29.
 */
public class EdgeWeightedDigraph {
    private final int V;            // 顶点总数
    private int E;                  // 边的总数
    private Bag<DirectedEdge>[] adj;// 邻接表
    // 含有V个顶点的空有向图
    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<DirectedEdge>();
        }
    }

    // 从输入流中读入图
    public EdgeWeightedDigraph(Scanner scanner) {
        this.V = 0;
    }

    // 顶点总数
    public int V() {
        return V;
    }

    // 边的数量
    public int E() {
        return E;
    }

    // 将e添加到该有向图中
    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
        E++;
    }

    // 从v指出的边
    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    // 该有向图中的所有边
    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> bag = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v++) {   // 遍历所有的顶点
            for (DirectedEdge e : adj[v]) {
                bag.add(e);
            }
        }
        return bag;
    }

    // 对象的字符串表示
    public String toString() {
        return "";
    }
}
