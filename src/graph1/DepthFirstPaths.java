package graph1;


import java.util.Stack;

/**
 * Created by YangYuan on 2017/12/25.
 */
public class DepthFirstPaths
{
    private boolean[] marked;//对应顶点是否已被搜索过
    private int[] edgeTo;// 从起点到一个顶点的已知路径上的最后一个顶点
    private final int s;// 起点

    public DepthFirstPaths(Graph G, int s)
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }


    private void dfs(Graph G, int v)
    {
        marked[v] = true;
        for (int w : G.adj(v))
        {
            if (! marked[w])
            {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public boolean hashPathTo(int v)
    {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v)
    {
        if (! hashPathTo(v))
            return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        return path;
    }
}
