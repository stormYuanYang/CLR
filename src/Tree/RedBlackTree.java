package Tree;

/**
 * Created by yangyuan on 2017/11/22.
 */
public class RedBlackTree
{
    private static class TreeNode
    {
        int value;              // 携带的值
        boolean color = false;  // false代表结点为黑色，true代表结点为红色
        TreeNode parent;        // 指向父亲的引用
        TreeNode left;          // 指向左儿子的引用
        TreeNode right;         // 指向右儿子的引用

        TreeNode(int value, boolean color, TreeNode parent, TreeNode left, TreeNode right)
        {
            this.value = value;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 左旋：通过指针操作，在常数时间内局部改变树的结构
     * @param tree  树
     * @param x     轴心(以此为支点进行旋转)
     */
    private static void leftRotate(RedBlackTree tree, TreeNode x)
    {
        TreeNode y = x.right;   // y是x的右儿子
        if (y == tree.nil)
            return;
        x.right = y.left;       // x持有y的左儿子
        if (y.left != tree.nil)
            y.left.parent = x;  // y的左儿子的父亲不再是y而是x
        y.parent = x.parent;    // y的父亲原本是x，现在改成x的父亲
        if (x.parent == tree.nil)
            tree.root = y;      // 设置根节点
        else if (x == x.parent.left)
            x.parent.left = y;  // 设置y为其父亲的左儿子
        else
            x.parent.right = y; // 设置y为其父亲的右儿子
        y.left = x;             // 设置x为y的左儿子
        x.parent = y;           // 设置y为x的父亲
    }

    /**
     * 右旋：通过指针操作，在常数时间内局部改变树的结构
     * @param tree  树
     * @param x     轴心(以此为支点进行旋转)
     */
    public static void rightRotate(RedBlackTree tree, TreeNode x)
    {
        TreeNode y = x.parent;  // y是x的父亲
        if (y == tree.nil)      // x没有父亲结点,那么x应该是树的根节点
            return;
        y.left = x.right;       // y的左儿子由x变更为x的右儿子
        if (x.right != tree.nil)
            x.right.parent = y; // x的右儿子的父亲变更为y
        x.parent = y.parent;    // x的父亲由y变更为y的父亲
        if (y.parent == tree.nil)
            tree.root = x;      // 设置根节点
        else if (y.parent.left == y)
            y.parent.left = x;  // 设置x为其父亲的左儿子
        else
            y.parent.right = x; // 设置x为其父亲的右儿子
        x.right = y;            // x的右儿子变更为y
        y.parent = x;           // y的父亲变更为x
    }

    /**
     * 插入一个结点到红黑树中，并保持其红黑树的性质
     * @param tree  树
     * @param z     被插入的结点
     */
    public static void insert(RedBlackTree tree, TreeNode z)
    {
        TreeNode y = tree.nil;
        TreeNode x = tree.root;

        // 从根结点开始，在树中下降找到z的父亲
        while (x != tree.nil)
        {
            y = x;
            if (z.value < x.value)
                x = x.left;
            else
                x = x.right;
        }
        z.parent = y;                   // 设置z的父亲为y

        if (y == tree.nil)
            tree.root = z;              // 设置根节点为z
        else if (z.value < y.value)
            y.left = z;                 // 设置y的左儿子为z
        else
            y.right = z;                // 设置y的右儿子为z

        z.left = tree.nil;              // 设置z的左儿子为null
        z.right = tree.nil;             // 设置z的右儿子为null
        z.color = true;                 // 设置z的颜色为红色
        insertFixup(tree, z);           // 保持红黑树的性质
    }

    /**
     * 执行这个函数保持红黑树的性质
     * @param tree      树
     * @param z         被插入的结点
     */
    private static void insertFixup(RedBlackTree tree, TreeNode z)
    {
        while (z.parent.color)                              // z父亲是红色的(根据红黑性质保证了z的爷爷是存在的)
        {
            TreeNode y = tree.nil;
            if (z.parent == z.parent.parent.left)           // z父亲是z爷爷的左儿子
            {
                y = z.parent.parent.right;                  // 设置y为z爷爷的右儿子(即y是z的叔叔)
                if (y.color)                                // y是红色的
                {
                    z.parent.color = false;                 // 设置z父亲的颜色为黑色
                    y.color = false;                        // 设置y的颜色为黑色
                    z.parent.parent.color = true;           // 设置z爷爷的颜色为红色
                    z = z.parent.parent;                    // z的引用指向z的爷爷
                }
                else
                {
                    if (z == z.parent.right)                // z是z父亲的右儿子
                    {
                        z = z.parent;                       // z的引用指向z的父亲
                        leftRotate(tree, z);                // 以z为轴心左旋
                    }
                    z.parent.color = false;                 // 设置z父亲的颜色为黑色
                    z.parent.parent.color = true;           // 设置z爷爷的颜色为红色
                    rightRotate(tree, z.parent);            // 以z父亲为轴心右旋
                }
            }
            else                                            // z的父亲是z爷爷的右儿子
            {
                y = z.parent.parent.left;                   // 设置y为z爷爷的左儿子
                if (y.color)                                // y是红色的
                {
                    z.parent.color = false;                 // 设置z父亲的颜色为黑色
                    y.color = false;                        // 设置y的颜色为黑色
                    z.parent.parent.color = true;           // 设置z爷爷的眼色为红色
                    z = z.parent.parent;                    // z的引用指向z的爷爷
                }
                else
                {
                    if (z == z.parent.left)                 // z是z父亲的左儿子
                    {
                        z = z.parent;                       // z的引用指向z的父亲
                        rightRotate(tree, z.left);          // 以原来的z(父亲的左儿子)为轴心右旋
                    }
                    z.parent.color = false;                 // 设置z父亲的颜色为黑色
                    z.parent.parent.color = true;           // 设置z爷爷的颜色为红色
                    leftRotate(tree, z.parent.parent);      // 以z爷爷为轴心左旋
                }
            }
        }
        tree.root.color = false;
    }

    /****************************/
    private TreeNode root;  // 树的根结点
    private final TreeNode nil = new TreeNode(0, false, null, null, null);// 哨兵结点
}
