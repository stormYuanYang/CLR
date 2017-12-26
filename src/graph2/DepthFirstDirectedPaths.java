package graph2;

import java.util.Stack;

/**
 * Created by YangYuan on 2017/12/26.
 */
public class DepthFirstDirectedPaths
{
    private boolean[] marked;// 结点是否已被搜索
    private int[] edgeTo;
    private int s;  // 起点s

    public DepthFirstDirectedPaths(Digraph G, int s)
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(Digraph G, int v)
    {
        marked[v] = true;// 标记为已访问
        for (int w : G.adj(v)) {
            if (! marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v)
    {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v)
    {
        if (! hasPathTo(v))
            return null;
        Stack<Integer> path = new Stack<>();
        for (int i = v; i != s; i = edgeTo[i]) {
            path.push(i);
        }
        return path;
    }
}
