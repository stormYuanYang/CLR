package graph1;

/**
 * Created by YangYuan on 2017/12/25.
 */
public class CC
{
    private boolean[] marked;
    private int[] id;
    private int count;

    public CC(Graph G)
    {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int s = 0; s < G.V(); s++)
        {
            if (! marked[s])
            {
                dfs(G, s);
                count++;
            }
        }
    }

    private void dfs(Graph G, int v)
    {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v))
        {
            if (! marked[w])
                dfs(G, w);
        }
    }

    // v和w连通吗
    public boolean connected(int v, int w)
    {
        return id[v] == id[w];
    }

    // 连通分量数
    public int count()
    {
        return count;
    }

    // v所在的连通分量的标识符(0~count()-1)
    public int id(int v)
    {
        return id[v];
    }
}
