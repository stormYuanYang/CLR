package graph2;

/**
 * 拓扑排序
 * Created by YangYuan on 2017/12/26.
 */
public class Topological {
    private Iterable<Integer> order;    // 顶点的拓扑顺序

    public Topological(Digraph G) {
        // 先判断是否是有向无环图
        DirectedCycle cycleFinder = new DirectedCycle(G);
        if (! cycleFinder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();// 获取所有顶点的逆后序
        }
    }

    /**
     * G是有向无环图吗
     * @return
     */
    public boolean isDAG() {
        return order != null;
    }

    /**
     * 拓扑有序的所有顶点
     * @return
     */
    public Iterable<Integer> order() {
        return order;
    }
}
