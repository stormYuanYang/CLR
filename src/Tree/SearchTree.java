package Tree;

/**
 * Created by yangyuan on 2017/11/13.
 */
public class SearchTree
{
    private static class TreeNode {
        TreeNode parent;
        TreeNode left;
        TreeNode right;
        int value;
    }

    public static void inorderTreeWalk(final TreeNode x) {
        if (x != null) {
            inorderTreeWalk(x.left);
            System.out.println(x.value);
            inorderTreeWalk(x.right);
        }
    }

    public static void preorderTreeWalk(final TreeNode x) {
        if (x != null) {
            System.out.println(x.value);
            preorderTreeWalk(x.left);
            preorderTreeWalk(x.right);
        }
    }

    public static void postorderTreeWalk(final TreeNode x) {
        if (x != null) {
            postorderTreeWalk(x.left);
            postorderTreeWalk(x.right);
            System.out.println(x.value);
        }
    }

    public static TreeNode treeSearch(final TreeNode x, final int k) {
        if (x == null)
            return null;
        TreeNode y = x;
        while (y != null && y.value != k) {
            if (k < y.value)
                y = y.left;
            else
                y = y.right;
        }
        return y;
    }

    public static TreeNode minimumNode(final TreeNode x) {
        if (x == null)
            return null;
        TreeNode y = x;
        while (y.left != null)
            y = y.left;
        return y;
    }

    public static TreeNode maximumNode(final TreeNode x) {
        if (x == null)
            return null;
        TreeNode y = x;
        while (y.right != null)
            y = y.right;
        return y;
    }

    /**
     * 查找x的后继
     *
     * @param x
     * @return
     */
    public static TreeNode treeSuccessor(final TreeNode x)
    {
        if (x == null)
            return null;
        TreeNode a = x, b = x.parent;
        if (x.right != null)
            return minimumNode(a.right);
        while (b != null && a == b.right) {
            a = b;
            b = b.parent;
        }
        return b;
    }

    /**
     * 查找x的前驱
     * @param x
     * @return
     */
    public static TreeNode treePredecessor(final TreeNode x)
    {
        if (x == null)
            return null;
        TreeNode a = x, b = x.parent;
        // 如果x的左子树不为空,则其前驱是左子树中最大的结点
        if (x.left != null)
            return maximumNode(x.left);
        if (b != null && a == b.left)
        {
            a = b;
            b = b.parent;
        }
        return b;
    }

    /**
     * 向二叉搜索树里插入结点
     * @param tree
     * @param newNode
     */
    public static void treeInsert(final SearchTree tree, final TreeNode newNode)
    {
        if (tree == null)
        {
            System.err.println("树的引用为空 插入结点失败");
            return;
        }
        if (newNode == null)
        {
            System.err.println("插入结点时，插入结点是null");
            return;
        }

        TreeNode y = null, x = tree.root;
        while (x != null)
        {
            y = x;
            if (newNode.value < x.value)
                x = x.left;
            else
                x = x.right;
        }
        newNode.parent = y;

        if (y == null)
            tree.root = newNode;
        else if (newNode.value < y.value)
            y.left = newNode;
        else
            y.right = newNode;

    }

    /**
     * 用一颗子树替换另一颗子树
     * @param tree      树
     * @param oldNode   旧子树的根结点
     * @param newNode   新子树的根节点
     */
    public static void transplant(final SearchTree tree, final TreeNode oldNode, final TreeNode newNode)
    {
        if (oldNode.parent == null)
            tree.root = newNode;
        else if (oldNode == oldNode.parent.left)
            oldNode.parent.left = newNode;
        else
            oldNode.parent.right = newNode;
        if (newNode != null)
            newNode.parent = oldNode.parent;
    }

    /**
     * 删除结点
     * @param tree
     * @param deletedNode
     */
    public static void treeDelete(final SearchTree tree, final TreeNode deletedNode)
    {
        if (deletedNode.left == null)
            transplant(tree, deletedNode, deletedNode.right);
        else if (deletedNode.right == null)
            transplant(tree, deletedNode, deletedNode.left);
        else
        {
            TreeNode y = minimumNode(deletedNode.right);
            if (y.parent != deletedNode)
            {
                transplant(tree, y, y.right);
                y.right = deletedNode.right;
                y.right.parent = y;
            }
            transplant(tree, deletedNode, y);
            y.left = deletedNode.left;
            y.left.parent = y;
        }
    }



    private TreeNode root;
}
