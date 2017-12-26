package graph2;

import graph1.Bag;

import java.util.Scanner;

/**
 * Created by YangYuan on 2017/12/26.
 */
public class Digraph {

    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    /**
     * 创建一幅含有V个顶点但没有边的有向图
     *
     * @param V
     */
    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    /**
     * 从输入流中读取一幅有向图
     *
     * @param scanner
     */
    public Digraph(Scanner scanner) {
        this.V = 0;
    }

    /**
     * 顶点总数
     *
     * @return
     */
    public int V() {
        return V;
    }

    /**
     * 边的总数
     *
     * @return
     */
    public int E() {
        return E;
    }

    /**
     * 向有向图中添加一条边v-->w
     * @param v
     * @param w
     */
    public void addEdge(int v, int w)
    {
        adj[v].add(w);
        E++;
    }

    /**
     * 获取和v邻接的所有顶点
     * @param v
     * @return
     */
    public Iterable<Integer> adj(int v)
    {
        return  adj[v];
    }

    /**
     * 获取反向图
     * @return
     */
    public Digraph reverse()
    {
        Digraph R = new Digraph(V);// 构造新的图
        // 遍历所有的边
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                R.addEdge(w, v);// 添加反向的连接
            }
        }
        return R;
    }

    @Override
    public String toString()
    {
        return "I am Digraph!";
    }
}
