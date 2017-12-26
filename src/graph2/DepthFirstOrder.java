package graph2;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * 有向图中基于深度优先搜索的顶点排序
 * Created by YangYuan on 2017/12/26.
 */
public class DepthFirstOrder {
    private boolean[] marked;// 对应结点是否已被搜索过
    private Queue<Integer> pre; // 顶点的前序排列
    private Queue<Integer> post;// 顶点的后序排列
    private Stack<Integer> reversePost;// 所有顶点的逆后序

    public DepthFirstOrder(Digraph G) {
        pre = new ArrayDeque<>();
        post = new ArrayDeque<>();
        reversePost = new Stack<>();
        marked = new boolean[G.V()];
        // 对图中的每一个顶点进行深度优先搜索
        for (int v = 0; v < G.V(); v++) {
            if (! marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Digraph G, int v) {
        pre.offer(v);
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (! marked[w]) {
                dfs(G, w);
            }
        }
        post.offer(v);
        reversePost.push(v);
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
