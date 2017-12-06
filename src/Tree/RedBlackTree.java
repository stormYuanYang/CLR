package Tree;

import javax.swing.event.TreeWillExpandListener;
import java.util.Random;

/**
 * Created by yangyuan on 2017/11/22.
 */
public class RedBlackTree
{
    private static class TreeNode
    {
        int value;              // 携带的值
        boolean red;            // 结点颜色(或红或黑,true为红,false为黑)
        TreeNode parent;        // 指向父亲的引用
        TreeNode left;          // 指向左儿子的引用
        TreeNode right;         // 指向右儿子的引用

        TreeNode(int value, boolean red, TreeNode parent, TreeNode left, TreeNode right)
        {
            this.value = value;
            this.red = red;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        TreeNode(int value)
        {
            this.value = value;
        }
    }

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
        if (tree == null || x == null || x.right == null || x.right == tree.nilNode)
            return;

        TreeNode y = x.right;   // x的右儿子被y引用
        x.right = y.left;   // y的左儿子β也成为x的右儿子

        y.parent = x.parent; // x的父亲成为y的父亲
        if (x.parent == tree.nilNode) // x是根节点
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
        if (tree == null || y == null || y.left == null || y.left == tree.nilNode)
            return;

        TreeNode x = y.left;    // y的左儿子被x引用
        y.left = x.right;   // x的右儿子β也成为y的左儿子

        x.parent = y.parent;    // y的父亲成为x的父亲
        if (y.parent == tree.nilNode)   // y是根节点
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
        TreeNode y = tree.nilNode;
        TreeNode x = tree.root;

        // 根据z携带的值找到z的父亲
        while (x != tree.nilNode)
        {
            y = x;
            if (z.value < x.value)
                x = x.left;
            else
                x = x.right;
        }

        z.parent = y;   // y成为z的父亲
        if (y == tree.nilNode)  // 未在树中找到z的父亲
            tree.root = z;  // z成为树的根结点
        else if (z.value < y.value)    // z值小于y的值所以应该是y的左儿子
            y.left = z; // z成为y的左儿子
        else    // z值大于等于y的值所以应该是y的右儿子
            y.right = z;    // z成为y的右儿子

        z.left = tree.nilNode;  // 哨兵结点成为z的左儿子
        z.right = tree.nilNode; // 哨兵结点成为z的右儿子
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

    public static void main(String[] args)
    {
        RedBlackTree tree = new RedBlackTree();
        Random random = new Random();
        for (int i = 0; i < 500; i++)
        {
            int x = random.nextInt(100);
            insert(tree, new TreeNode(x));
        }
        tree.inorderTreeWalk();
    }

    /****************************/
    private final TreeNode nilNode;// 哨兵结点(黑色),表示null,代表 根节点的父节点、所有的叶子结点
    private TreeNode root; // 树的根结点

    public RedBlackTree()
    {
        nilNode = new TreeNode(0, false, null, null, null);
        root = nilNode;
    }

    /**
     * 中序遍历
     * @param x 起点
     */
    private void inorderTreeWalk(TreeNode x)
    {
        if (x == null || x == this.nilNode)
            return;
        inorderTreeWalk(x.left);
        System.out.print(x.value + " ");
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
