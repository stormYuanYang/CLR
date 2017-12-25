package graph1;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by yangyuan on 2017/12/25.
 */
public class Graph
{
    private class Bag<Item> implements Iterable<Item>
    {
        private class Node
        {
            Item item;
            Node next;
        }
        private Node first = null;// 首结点
        private int count = 0;// 结点计数

        // 创建一个空的背包
        public Bag()
        {}

        // 添加一个元素
        public void add(Item item)
        {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            count++;
        }

        @Override
        public Iterator<Item> iterator()
        {
            return new ListIterator();
        }

        private class ListIterator implements Iterator<Item> {
            private Node current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public void remove() {
            }

            @Override
            public Item next()
            {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }

        // 背包是否为空
        public boolean isEmpty()
        {
            return first == null;
        }

        // 背包中的元素数量
        public int size()
        {
            return count;
        }
    }

    private final int V;        // 顶点数目
    private int E;              // 边的数目
    private Bag<Integer>[] adj; // 邻接表

    // 创建一个含有V个顶点但不含有边的图
    public Graph(int V)
    {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    //
    public Graph(Scanner scanner)
    {
        this(scanner.nextInt());    // 读取V并将图初始化
        int E = scanner.nextInt();  // 读取E
        for (int i = 0; i < E; i++) // 添加一条边
        {
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            addEdge(v, w);
        }
    }

    // 顶点数
    public int V()
    {
        return this.V;
    }

    // 边数
    public int E()
    {
        return this.E;
    }

    // 向图中添加一条边v-w
    public void addEdge(int v, int w)
    {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    // 和v相邻的所有顶点
    public Iterable<Integer> adj(int v)
    {
        return adj[v];
    }

    @Override
    public String toString()
    {
        String  s = V + " Vertices: " + E + " edges\n";
        for (int v= 0; v < V; v++)
        {
            s += v + ": ";
            for (int w : adj(v))
                s += w + " ";
            s += "\n";
        }
        return s;
    }

    // 计算顶点v的度
    public static int degree(Graph G, int v)
    {
        int degree = 0;
        for (int w : G.adj(v))
            degree++;
        return degree;
    }

    // 计算所有顶点的最大度数
    public static int maxDegree(Graph G)
    {
        int max = 0;
        for (int v = 0; v < G.V(); v++)
        {
            int oneDegree = degree(G, v);
            if (max < oneDegree)
                max = oneDegree;
        }
        return max;
    }

    // 计算所有顶点的平均度数
    public static double avgDegree(Graph G)
    {
        return (double)G.E() * 2 / G.V();
    }

    // 计算自环的个数
    public static int numberOfSelfLoops(Graph G)
    {
        int count = 0;
        for (int v = 0; v < G.V(); v++)
        {
            for (int w : G.adj(v))
            {
                if (v == w)
                    count++;
            }
        }
        return count / 2;// 每一条边都被记录了两次
    }

    public static void main(String[] args)
    {
        System.out.println("Hello World!");
    }
}
