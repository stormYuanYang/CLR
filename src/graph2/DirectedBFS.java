package graph2;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by YangYuan on 2017/12/26.
 */
public class DirectedBFS
{
    private boolean[] marked;

    public DirectedBFS(Digraph G, int s)
    {
        marked = new boolean[G.V()];
        bfs(G, s);
    }

    /**
     * 广度优先搜索
     * @param G
     * @param s
     */
    private void bfs(Digraph G, int s)
    {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(s);
        marked[s] = true;
        while (! queue.isEmpty()) {
            int v = queue.poll();
            marked[v] = true;
            for (int w : G.adj(v)) {
                if (! marked[w])
                    queue.offer(w);// 抛入队尾
            }
        }
    }

    /**
     * 是否可达
     * @param v
     * @return
     */
    public boolean marked(int v)
    {
        return marked[v];
    }
}
