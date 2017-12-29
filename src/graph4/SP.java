package graph4;

/**
 * 最短路径
 * Created by YangYuan on 2017/12/29.
 */
public class SP {
    public SP(EdgeWeightedDigraph G, int s) {

    }

    // 从顶点s到v的距离，如果不存在则路径为无穷大
    public double distTo(int v) {
        return 0;
    }

    // 是否存在从顶点s到v的路径
    public boolean hasPathTo(int v) {
        return false;
    }

    // 从顶点s到v的路径，如果不存在则为null
    public Iterable<DirectedEdge> pathTo(int v) {
        return null;
    }

    // 边的松弛
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
    }
}
