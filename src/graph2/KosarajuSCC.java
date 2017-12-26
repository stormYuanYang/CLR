package graph2;

/**
 * 计算强连通分量的Kosaraju算法
 * Created by YangYuan on 2017/12/26.
 */
public class KosarajuSCC {
    private boolean[] marked;   // 对应顶点是否已被搜索过
    private int[] id;   // 强连通分量的标识符
    private int count;  // 强连通分量的数量

    public KosarajuSCC(Digraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        // 对G的反向图进行顶点排序
        DepthFirstOrder order = new DepthFirstOrder(G.reverse());
        for (int s : order.reversePost()) {// 按G的反向图的逆后序遍历
            if (! marked[s]) {
                dfs(G, s);// 对原图进行深度优先搜索
                count++;
            }
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v)) {
            if (! marked[w]) {
                dfs(G, w);
            }
        }
    }

    /**
     * 两个顶点是否是强连通
     * @param v
     * @param w
     * @return
     */
    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    /**
     * v所属强连通分量的标识符
      */
    public int id(int v) {
        return id[v];
    }

    /**
     * 强连通分量的数量
     * @return
     */
    public int count() {
        return count;
    }
}
