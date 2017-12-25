package graph1;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by YangYuan on 2017/12/25.
 */
public class BreadthFirstPaths
{
    private boolean[] marked;   // 到达该顶点的最短路径是否已知
    private int[] edgeTo;       // 到达该顶点的已知路径上的最后一个顶点
    private final int s;        // 起点

    public BreadthFirstPaths(Graph G, int s)
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int s)
    {
        Queue<Integer> queue = new ArrayDeque<Integer>();
        marked[s] = true;// 起点标记为已搜索
        queue.offer(s);// 入队
        while (! queue.isEmpty())
        {
            int v = queue.poll();// 从队列中删去下一个顶点
            for (int w : G.adj(v))// 遍历与当前结点v相邻的结点
            {
                if (! marked[w])    // 如果w还未搜索过
                {
                    edgeTo[w] = v;// 保存最短路径的最后一条边
                    marked[w] = true;// 标记它，因为最短路径已知
                    queue.offer(w);// 将w抛入队尾
                }
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
        for (int i = v; i != s; i = edgeTo[i])
            path.push(i);
        return path;
    }
}
