package graph2;

/**
 * 顶点对的可达性
 * Created by YangYuan on 2017/12/26.
 */
public class TransitiveClosure {
    private DirectedDFS[] all;

    /**
     * 预处理
     * @param G
     */
    public TransitiveClosure(Digraph G) {
        all = new DirectedDFS[G.V()];
        // 遍历所有的顶点,对每一个顶点进行深度优先遍历
        for (int v = 0; v < G.V(); v++) {
            all[v] = new DirectedDFS(G, v);
        }
    }

    /**
     * w是从v可达的吗
     * @param v
     * @param w
     * @return
     */
    public boolean reachable(int v, int w) {
        return all[v].marked(w);
    }
}
