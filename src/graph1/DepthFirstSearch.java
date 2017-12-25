package graph1;

/**
 * Created by YangYuan on 2017/12/25.
 */
public class DepthFirstSearch
{
    private boolean[] marked;// 顶点是否已经被搜索过的标记数组
    private int count;

    public DepthFirstSearch(Graph G, int s)
    {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    // 深度优先搜索
    private void dfs(Graph G, int v)
    {
        marked[v] = true;
        count++;
        for (int w : G.adj(v))// 遍历与v相邻的结点
        {
            if (marked[w] == false)// 只对还未搜索过的顶点进行搜索
                dfs(G, w);
        }
    }

    // 结点是否已被搜索过
    public boolean marked(int w)
    {
        return marked[w];
    }

    // 被搜索的结点计数
    public int count()
    {
        return count;
    }
}
