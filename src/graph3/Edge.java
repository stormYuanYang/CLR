package graph3;

/**
 * 加权边
 * Created by YangYuan on 2017/12/26.
 */
public class Edge implements Comparable<Edge>{
    private final int v;    // 顶点之一
    private final int w;    // 另一个顶点
    private final double weight;    // 边的权重

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * 边的权重
     * @return
     */
    public double weight() {
        return weight;
    }

    /**
     * 边两端的顶点之一
     * @return
     */
    public int either() {
        return v;
    }

    /**
     * 另一个顶点
     * @param v
     * @return
     */
    public int other(int v) throws Exception {
        if (this.v == v) {
            return w;
        }
        else if (this.w == v) {
            return this.v;
        }
        else
            throw new Exception("不存在的边");
    }

    /**
     * 将这条边e与that进行比较
     * @param that
     * @return
     */
    @Override
    public int compareTo(Edge that) {
        double thatWeight = that.weight();
        if (weight < thatWeight) {
            return -1;
        }
        else if (weight > thatWeight) {
            return 1;
        }
        else
            return 0;
    }

    @Override
    public String toString() {
        return String.format("%d--%d %.2f", v, w, weight);
    }
}
