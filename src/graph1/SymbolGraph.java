package graph1;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SymbolGraph
{
	private Map<String, Integer> st;//符号名-->索引
	private String[] keys;// 索引-->符号名
	private Graph G;// 内部的图对象

	// 根据文件名和字符串分隔符 构造图
    public SymbolGraph(String fileName, String seperator) throws Exception
    {
		st = new HashMap<String, Integer>();
		// 第一遍 构造两种索引
        FileInputStream fileInputStream = new FileInputStream(fileName);
        Scanner scanner = new Scanner(fileInputStream);
		while (scanner.hasNextLine())// 构造st
		{
			String[] a = scanner.nextLine().split(seperator);
			for (int i = 0; i < a.length; i++)
			{
                if (! st.containsKey(a[i]))
                    st.put(a[i], st.size());// 每个不同符号的索引是唯一的，且随着输入编号递增
            }
		}
        keys = new String[st.size()];
        for (String name : st.keySet())
            keys[st.get(name)] = name;// 构造反向索引数组
		scanner.close();
		fileInputStream.close();

		// 第二遍 构造图
		G = new Graph(st.size());
		fileInputStream = new FileInputStream(fileName);
		scanner = new Scanner(fileInputStream);
		while (scanner.hasNextLine())// 构造图
        {
            String[] a = scanner.nextLine().split(seperator);
            int v = st.get(a[0]);// 对应符号的索引v，也就是图中顶点的编号
            for (int i = 1; i < a.length; i++)
                G.addEdge(v, st.get(a[i]));
        }
		scanner.close();
		fileInputStream.close();
	}

	// key是否是一个顶点
    public boolean contains(String vertex)
    {
        return st.containsKey(vertex);
    }

	// key的索引
    public int index(String key)
    {
        return st.get(key);
    }

	// 索引v的顶点名
    public String name(int v)
    {
        return keys[v];
    }

	// 内部的图对象
    public Graph G()
    {
        return G;
    }
}
