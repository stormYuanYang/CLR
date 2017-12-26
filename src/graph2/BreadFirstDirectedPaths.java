package graph2;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by YangYuan on 2017/12/26.
 */
public class BreadFirstDirectedPaths {
    private boolean[] marked;
    private int s;  // 起点
    private int[] edgeTo;

    public BreadFirstDirectedPaths(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;

        bfs(G, s);
    }

    private void bfs(Digraph G, int s) {
        marked[s] = true;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(s);
        while (! queue.isEmpty()) {
            int v = queue.poll();
            for (int w : G.adj(v)) {
                if (! marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    queue.offer(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (! hasPathTo(v))
            return null;
        Stack<Integer> path = new Stack<>();
        for (int i = v; i != s; i = edgeTo[i])
            path.push(i);
        return path;
    }
}
