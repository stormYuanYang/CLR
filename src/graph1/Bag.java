package graph1;

import java.util.Iterator;

/**
 * Created by mshz on 2017/12/26.
 */
public class Bag<Item> implements Iterable<Item>
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