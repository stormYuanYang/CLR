package graph2;

/**
 * Created by YangYuan on 2017/12/26.
 */
public class DirectedDFS {
    private boolean[] marked;

    /**
     * 在G中找到从s可达的所有顶点
     *
     * @param G
     * @param s
     */
    public DirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    /**
     * 在G中找到从sources中的所有顶点可达的所有顶点
     *
     * @param G
     * @param sources
     */
    public DirectedDFS(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        // 从sources中的每个结点开始dfs
        for (int s : sources) {
            if (! marked[s])
                dfs(G, s);
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (! marked[w])
                dfs(G, w);
        }
    }

    /**
     * v是从s可达的吗
     * @param v
     * @return
     */
    public boolean marked(int v)
    {
        return marked[v];
    }
}
