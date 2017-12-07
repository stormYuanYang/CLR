package Tree;

import java.util.Random;

/**
 * Created by yangyuan on 2017/11/22.
 */
public class RedBlackTree
{
    private static class TreeNode
    {
        int order;              // 比较结点顺序的值(不能有相同的值)
        boolean red;            // 结点颜色(或红或黑,true为红,false为黑)
        TreeNode parent;        // 指向父亲的引用
        TreeNode left;          // 指向左儿子的引用
        TreeNode right;         // 指向右儿子的引用

        TreeNode(int order, boolean red, TreeNode parent, TreeNode left, TreeNode right)
        {
            this.order = order;
            this.red = red;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        TreeNode(int order)
        {
            this.order = order;
        }
    }

    // 哨兵结点(黑色),表示null,代表 根节点的父节点、所有的叶子结点
    private static final TreeNode NIL_NODE  = new TreeNode(0, false, null, null, null);

    /**
     *  左旋: 消耗常数时间进行的指针操作,改变树的结构
     *  轴心、轴心的右儿子(对应右旋中的y)都不能为空,为空就无法进行旋转
     *  以x结点为轴心，将其右儿子向左旋转(参考汽车方向盘的左转)
     * @param tree  树
     * @param x     轴心
     */
    public static void leftRotate(RedBlackTree tree, TreeNode x)
    {
        // 树、x结点、x的右儿子有一个为空,就无法左旋
        if (tree == null || x == null || x.right == null || x.right == NIL_NODE)
            return;

        TreeNode y = x.right;   // x的右儿子被y引用
        x.right = y.left;   // y的左儿子β也成为x的右儿子

        y.parent = x.parent; // x的父亲成为y的父亲
        if (x.parent == NIL_NODE) // x是根节点
            tree.root = y;  // 重新设置根节点
        else if (x == x.parent.left) // x是父亲的左儿子
            x.parent.left = y; // y成为x父亲的左儿子
        else
            x.parent.right = y; // y成为x父亲的右儿子

        x.parent = y; // y成为x的父亲
        y.left = x; // x成为y的左儿子
    }

    /**
     * 右旋：消耗常数时间进行的指针操作，改变树的结构
     * 轴心、轴心的左儿子(对应左旋中的x)都不能为空，为空就无法进行旋转
     * 以y结点为轴心，将其左儿子向右旋转(参考汽车方向盘的右转)
     * @param tree  树
     * @param y     轴心
     */
    public static void rightRotate(RedBlackTree tree, TreeNode y)
    {
        // 树、y结点、y的左儿子有一个为空,就无法右旋
        if (tree == null || y == null || y.left == null || y.left == NIL_NODE)
            return;

        TreeNode x = y.left;    // y的左儿子被x引用
        y.left = x.right;   // x的右儿子β也成为y的左儿子

        x.parent = y.parent;    // y的父亲成为x的父亲
        if (y.parent == NIL_NODE)   // y是根节点
            tree.root = x;
        else if (y == y.parent.left)    // y是其父亲的左儿子
            y.parent.left = x;  // x成为y父亲的左儿子
        else
            y.parent.right = x; // x成为y父亲的右儿子

        y.parent = x;   // x成为y的父亲
        x.right = y;    // y成为x的右儿子
    }

    /**
     * 向树中插入一个结点，并保持红黑树性质
     * @param tree  红黑树
     * @param z     被插入的结点
     */
    public static void insert(RedBlackTree tree, TreeNode z)
    {
        if (tree == null || z == null)
            return;
        TreeNode y = NIL_NODE;
        TreeNode x = tree.root;

        // 根据z携带的值找到z的父亲
        while (x != NIL_NODE)
        {
            y = x;
            if (z.order < x.order)
                x = x.left;
            else
                x = x.right;
        }

        z.parent = y;   // y成为z的父亲
        if (y == NIL_NODE)  // 未在树中找到z的父亲
            tree.root = z;  // z成为树的根结点
        else if (z.order < y.order)    // z顺序值小于y的顺序值所以应该是y的左儿子
            y.left = z; // z成为y的左儿子
        else    // z值大于等于y的值所以应该是y的右儿子
            y.right = z;    // z成为y的右儿子

        z.left = NIL_NODE;  // 哨兵结点成为z的左儿子
        z.right = NIL_NODE; // 哨兵结点成为z的右儿子
        z.red = true;   // z涂红

        insertFixup(tree, z);   // 插入新节点后保持红黑树的性质
    }

    /**
     * 保持红黑树的性质
     * @param tree  树
     * @param z     插入的结点(红色) 左儿子、右儿子都是黑色的(tree.nilNode)
     */
    private static void insertFixup(RedBlackTree tree, TreeNode z)
    {
        if (tree == null || z == null)
            return;
        while (z.parent.red)    // z父亲为红色
        {
            TreeNode grandfather = z.parent.parent; // 获取z爷爷的引用
            if (z.parent == grandfather.left)   // z父亲是z爷爷的左儿子
            {
                TreeNode rightUncle = grandfather.right;    // 获取z右叔叔的引用
                if (rightUncle.red) // 叔叔为红色
                {
                    z.parent.red = false;   // z父亲涂黑
                    rightUncle.red = false; // z叔叔涂黑
                    grandfather.red = true; // z爷爷涂红
                    z = grandfather;    // z指向z爷爷
                }
                else
                {
                    if (z == z.parent.right)    // z是父亲的右儿子
                    {
                        z = z.parent;   // z指向z父亲
                        // 以z为轴心左旋(并不会改变祖父的位置,所以即使旋转之后依然有z.parent.parent==grandfather)
                        // 旋转之后，儿子成为父亲，父亲成为儿子，而爷爷保持不变
                        leftRotate(tree, z);
                    }
                    z.parent.red = false;   // z父亲涂黑
                    grandfather.red = true; // z爷爷图红
                    rightRotate(tree, grandfather); // 以爷爷为轴心右旋
                }
            }
            else    // z父亲是z爷爷的右儿子
            {
                TreeNode leftUncle = grandfather.left;  // 获取z左叔叔的引用
                if (leftUncle.red)  // 叔叔为红色
                {
                    z.parent.red = false;   // z父亲涂黑
                    leftUncle.red = false;  // z叔叔涂黑
                    grandfather.red = true; // z爷爷涂红
                    z = grandfather;    // z指向z爷爷
                }
                else
                {
                    if (z == z.parent.left) // z是父亲的左儿子
                    {
                        z = z.parent;   // z指向父亲
                        // 以z为轴心右旋(并不会改变祖父的位置,所以即使旋转之后依然有z.parent.parent==grandfather)
                        // 旋转之后，儿子成为父亲，父亲成为儿子，而爷爷保持不变
                        rightRotate(tree, z);
                    }
                    z.parent.red = false;   // z父亲涂黑
                    grandfather.red = true; // z爷爷涂红
                    leftRotate(tree, grandfather);  // 以爷爷为轴心左旋
                }
            }
        }
        tree.root.red = false;  // 根节点涂黑
    }

    /**
     * 替换子树:用新的子树替换旧的子树
     * @param tree      树
     * @param oldNode   旧子树的根节点
     * @param newNode   新子树的根节点
     */
    private static void transplant(RedBlackTree tree, TreeNode oldNode, TreeNode newNode)
    {
        if (oldNode.parent == NIL_NODE) // 旧结点就是树的根节点
            tree.root = newNode;    // 新节点设置为树的根
        else if (oldNode == oldNode.parent.left)    // 旧结点是父亲的左儿子
            oldNode.parent.left = newNode;  // 设置新结点为父亲左儿子
        else    // 旧结点是父亲的右儿子
            oldNode.parent.right = newNode; // 设置新结点为父亲右儿子
        newNode.parent = oldNode.parent;    // 旧结点父亲设置为新结点的父亲
    }

    /**
     * 删除结点，并保持红黑树性质
     * @param tree  树
     * @param z     被删除的结点z
     */
    public static void delete(RedBlackTree tree, TreeNode z)
    {
        TreeNode y = z; // z的引用被y持有
        TreeNode x = NIL_NODE;  // x为空
        boolean yOriginalColor = y.red; // 记录y的颜色变化轨迹

        if (z.left == NIL_NODE) // 左儿子为空
        {
            x = z.right;    // z右儿子被x持有
            transplant(tree, z, z.right);   // 用z的右子树替换z
        }
        else if (z.right == NIL_NODE)   // 左儿子不为空,右儿子为空
        {
            x = z.left; // z左儿子被x持有
            transplant(tree, z, z.left);    // 用z的左子树替换z
        }
        else    // 左、右儿子都不为空
        {
            y = minimum(z.right);   // 找到z的后继,并让其被y持有
            yOriginalColor = y.red; // y不再指向z，而是指向z的后继,所以更新颜色变化
            x = y.right;    // y的右儿子被x持有
            if (y.parent == z)  // y的父亲是z
                x.parent = y;   // y成为x的父亲
            else
            {
                transplant(tree, y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(tree, z, y);
            y.left = z.left;
            y.left.parent = y;
            y.red = z.red;
        }

        if (false == yOriginalColor)
            deleteFixup(tree, x);
    }

    /**
     * 在执行删除操作之后保持红黑树的性质
     * @param tree
     * @param x
     */
    public static void deleteFixup(RedBlackTree tree, TreeNode x) {
        if (tree == null || x == null)
            return;
        while (x != NIL_NODE && x.red == false)
        {
            TreeNode w = NIL_NODE;
            if (x == x.parent.left)
            {
                w = x.parent.right;
                if (w.red)
                {
                    w.red = false;
                    x.parent.red = true;
                    leftRotate(tree, x.parent);
                    w = x.parent.right;
                }

                if (w.left.red == false && w.right.red == false)
                {
                    w.red = true;
                    x = x.parent;
                }
                else
                {
                    if (w.right.red == false)
                    {
                        w.left.red = false;
                        w.red = true;
                        rightRotate(tree, w);
                        w = x.parent.right;
                    }
                    w.red = x.parent.red;
                    x.parent.red = false;
                    w.right.red = false;
                    leftRotate(tree, x.parent);
                    x = tree.root;
                }
            }
            else
            {
                // TODO
            }
        }

        x.red = false;
    }

    /**
     * 找到x为根的树中最小的结点
     * @param x     根结点
     * @return      最小的结点
     */
    public static TreeNode minimum(TreeNode x)
    {
        if (x == null || x == NIL_NODE) // x为空直接返回
            return null;
        while (x.left != NIL_NODE)  // x的左儿子不为空就继续寻找
            x = x.left;
        return x;
    }

    public static void main(String[] args)
    {
        RedBlackTree tree = new RedBlackTree();
        for (int i = 500; i > 0; i--)
        {
            insert(tree, new TreeNode(i));
        }
        tree.inorderTreeWalk();

        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(5000);
                }
                catch (Exception e)
                {

                }
                System.out.println("Hello");
            }
        };

        t.start();
        System.out.println("Fuck you");
    }

    /****************************/
    private TreeNode root; // 树的根结点

    public RedBlackTree()
    {
        root = NIL_NODE;
    }

    /**
     * 中序遍历
     * @param x 起点
     */
    private void inorderTreeWalk(TreeNode x)
    {
        if (x == null || x == NIL_NODE)
            return;
        inorderTreeWalk(x.left);
        System.out.print(x.order + " ");
        inorderTreeWalk(x.right);
    }

    /**
     * 以root为起点进行中序遍历
     */
    public void inorderTreeWalk()
    {
        inorderTreeWalk(root);
        System.out.println();
    }
}
