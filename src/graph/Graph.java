package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by yangyuan on 2017/11/17.
 */
public class Graph
{
    /**
     * 深度优先搜索DFS
     * @param G 待搜索的图
     */
    public static void DFS(final Graph G)
    {
        for (GraphNode each : G.graphNodes)
        {
            each.color = EColor.WHITE;
            each.pre = null;
        }
        G.time = 0;
        // 以多个起点进行深度搜索
        for (GraphNode each : G.graphNodes)
            if (each.color.equals(EColor.WHITE))
                DFSVist(G, each);
    }

    /**
     * 深度优先所搜遍历
     * @param G 待搜索的图
     * @param U 开始搜索的结点
     */
    public static void DFSVist(final Graph G, final GraphNode U)
    {
        U.d = ++G.time;
        U.color = EColor.GRAY;

        ListNode listNode = U.listNode;
        while (listNode != null)
        {
            GraphNode adjoinGraphNode = listNode.graphNode;
            if (adjoinGraphNode.color.equals(EColor.WHITE))
            {
                adjoinGraphNode.pre = U;
                DFSVist(G, adjoinGraphNode);
            }
            listNode = listNode.next;
        }
        U.color = EColor.BLACK;
        U.f = ++G.time;
    }

    /**
     * 广度优先搜索BFS
     * @param G     待搜索的图
     * @param SRC    搜索的起始结点
     */
    public static void BFS(final Graph G, final GraphNode SRC)
    {
        // 初始化全部结点状态
        for (GraphNode each : G.graphNodes)
        {
            each.found = false;
            each.d = -1;
            each.pre = null;
        }
        SRC.found = true;
        SRC.d = 0;

        Queue<GraphNode> queue = G.queue;
        queue.clear();
        queue.offer(SRC);
        while (! queue.isEmpty())
        {
            GraphNode u = queue.poll();
            ListNode currentListNode = u.listNode;
            while (currentListNode != null)
            {
                // 通过链表拿到与u邻接的图结点
                GraphNode adjoinGraphNode = currentListNode.graphNode;
                if (! adjoinGraphNode.found)
                {
                    adjoinGraphNode.found = true;
                    adjoinGraphNode.d = u.d + 1;
                    adjoinGraphNode.pre = u;
                    queue.offer(adjoinGraphNode);
                }
                currentListNode = currentListNode.next;
            }
        }
    }

    /**
     * 打印从目标结点到源结点的最短路径(无权图)
     * @param G     无权图
     * @param SRC   源结点
     * @param DEST  目标结点
     */
    public static void printShortestPath(final Graph G, final GraphNode SRC, final GraphNode DEST)
    {
        GraphNode graphNode = DEST;
        while (true)
        {
            if (graphNode.pre == null)
            {
                System.out.println("没有这样的路径!");
                break;
            }

            System.out.println(graphNode.value);
            if (graphNode == SRC)
                break;
            graphNode = graphNode.pre;
        }
    }

    /********************************/
    enum EColor
    {
        WHITE,
        GRAY,
        BLACK
    }

    // 图结点
    class GraphNode
    {
        int value;                  // 图结点携带的数据
        int d = 0;                  // 到源结点s的距离(DFS涂上白色的时间)
        int f = 0;                  // (DFS涂上黑色的时间)
        boolean found = false;      // 是否已被发现过
        EColor color = EColor.WHITE;// 图结点的颜色
        GraphNode pre = null;       // 前驱
        ListNode listNode = null;   // 用来链接所有与此结点邻接的结点
    }

    // 用链表法表示图的结点结构
    class ListNode
    {
        GraphNode graphNode = null; // 图结点
        ListNode next = null;       // 下一个结点
    }

    // BFS用到的队列
    Queue<GraphNode> queue = new ArrayDeque<>();
    // 存储所有的图结点
    List<GraphNode> graphNodes = new ArrayList<>();
    // DFS用到的变量，用来计算时间戳
    private int time = 0;
}
