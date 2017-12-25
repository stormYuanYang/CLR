package graph1;

/**
 * Created by YangYuan on 2017/12/25.
 */
public class CC
{
    private boolean[] marked;// 顶点是否已被搜索的标记
    private int[] id;// 记录对应顶点属于哪个连通分量
    private int count;// 连通分量的计数 也是区分连通分量的标记 

    public CC(Graph G)
    {
        marked = new boolean[G.V()];
        id = new int[G.V()];
		// 对所有顶点进行深度优先遍历 查看是否会有新的连通分量
        for (int s = 0; s < G.V(); s++)
        {
            if (! marked[s])// 如果进入这个if，说明是一个新的连通分量
            {
                dfs(G, s);
                count++;
            }
        }
    }

	// 深度优先遍历
    private void dfs(Graph G, int v)
    {
        marked[v] = true;// 标记顶点v为已搜索
        id[v] = count;// 记录顶点v属于的连通分量
        for (int w : G.adj(v))
        {
            if (! marked[w])
                dfs(G, w);
        }
    }

    // v和w连通吗
    public boolean connected(int v, int w)
    {
        return id[v] == id[w];// v和w存储的否是同一连通分量标记
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
