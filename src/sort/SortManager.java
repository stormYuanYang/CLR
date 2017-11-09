package sort;

import org.jetbrains.annotations.Contract;

import java.util.Random;
import java.util.logging.Logger;

/**
 * 排序算法的简单实现，当然目前都是串行执行的版本，之后应该会实现一个concurrency的版本，
 * 类似java的java.util.concurrent包类似泛型之类的技术并未实现
 * 掌握了算法原理思想，待实际使用时再写出所需的对应数据版本或者改写成泛型即可
 * 某些排序算法可能是由多个经典排序算法组合而成，命名时采用取各自排序算法首字母的方式组成新的排序算法名字
 * 注意代码并未按实际工作需要的健壮性要求来编写，只是注重实现算法，当然改写成工作中需要的代码也是很容易的
 * Created by yangyuan on 2017/11/1.
 * EMAIL: yytqmj@vip.qq.com
 */
public class SortManager
{
    /* ***********  静态private *************** */
    private static Logger LOGGER = Logger.getLogger(SortManager.class.toString());// 日志对象

    /**
     * 实现SortManager单例用到的内部枚举类
     */
    private static enum EInstance
    {
        Instance,
        ;

        private SortManager m_instance;

        EInstance()
        {
            m_instance = new SortManager();
        }

        @Contract(pure = true)
        SortManager getInstance()
        {
            return m_instance;
        }
    }

    /* *********** 静态 public ************** */

    /**
     * 采用枚举实现SortManager单例
     * @return
     */
    @Contract(pure = true)
    public static SortManager getInstance()
    {
        return EInstance.Instance.getInstance();
    }

    /* ************ 普通 private *********** */
    private int[] m_mergeL;     // 用于归并排序，缓存元素
    private int[] m_mergeR;     // 用于归并排序，缓存元素
    private Random m_random = new Random();// 随机数生成器

    /**
     * 构造函数声明和定义为私有——避免外部代码创建新的SortManager对象
     */
    private SortManager() {}

    /**
     * 插入排序:
     * 对指定闭区间[left, right]进行插入排序，排序完成之后
     * 整个序列按非降序排列
     * @param A     待排数组
     * @param left  左边界
     * @param right 右边界
     */
    private void insertionSort(int[] A, int left, int right)
    {
        for (int i = left + 1; i <= right; i++)
        {
            int key = A[i], j = i - 1;
            for (; j >= 0 && A[j] > key; j--)
                A[j + 1] = A[j];
            A[j + 1] = key;
        }
    }

    /**
     * 快速排序：
     * 对指定的序列A[left, right]进行非降序排序
     * @param A     待排数组
     * @param left  左边界
     * @param right 右边界
     */
    private void quickSort(int[] A, int left, int right)
    {
        if (left >= right)
            return;
        int mid = partition(A, left, right);
        quickSort(A, left, mid - 1);
        quickSort(A, mid + 1, right);
    }

    /**
     * 划分算法：
     * 对一段序列进行划分，划分结束之后
     * 会出现一个指定位置mid(i+1),满足A[left, mid] <= A[mid] <= A[mid, right]
     * 划分过程返回的就是指定位置mid
     * @param A     待排数组
     * @param left  左边界
     * @param right 右边界
     * @return      指定的中间边界mid(i+1),满足A[left, mid] <= A[mid] <= A[mid, right]
     */
    private int partition(int[] A, int left, int right)
    {
        int key = A[right], i = left - 1;
        for (int j = left; j < right; j++)
        {
            if (A[j] <= key)
            {
                i++;
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
        int temp = A[i + 1];
        A[i + 1] = A[right];
        A[right] = temp;
        return i + 1;
    }

    /**
     * 随机化快速排序:
     * 采用随机化的划分算法，减少出现性能退化的情况
     * @param A     待排数组A
     * @param left  左边界
     * @param right 右边界
     */
    private void randomizeQuickSort(int[] A, int left, int right)
    {
        if (left >= right)
            return;
        int mid = randomizePartition(A, left, right);
        randomizeQuickSort(A, left, mid - 1);
        randomizeQuickSort(A, mid + 1, right);
    }

    /**
     * 随机化划分:
     * 为了避免出现大部分导致划分性能退化的情况，
     * 在选取作为划分的主元时，并不直接选取A[right]
     * 而是随机选取一个元素作为主元
     * @param A     待排数组A
     * @param left  左边界
     * @param right 右边界
     * @return
     */
    private int randomizePartition(int[] A, int left, int right)
    {
        int index = left + m_random.nextInt(right - left + 1);
        int temp = A[index];
        A[index] = A[right];
        A[right] = temp;
        return partition(A, left, right);
    }

    /**
     * 对传入的数组A[left, right]这个闭区间里面的元素进行归并排序
     * @param A     待排数组A
     * @param left  左边界
     * @param right 右边界
     */
    private void mergeSort(int[] A, int left, int right)
    {
        if (left >= right)
            return;
        int mid = (left + right) / 2;
        mergeSort(A, left, mid);
        mergeSort(A, mid + 1, right);
        merge(A, left, mid, right);
    }

    /**
     * 归并过程:
     * 将两个有序的子序列合并成一个有序的序列，
     * 以下用L代表左边集合里的元素，R代表右边集合里的元素
     * L和R分别缓存在外部空间，再根据集合里元素的大小按顺序
     * 放回A[left, right],这个过程完成之后A[left, right]
     * 即变得有序
     * 就像分别有左右两堆扑克，我们同时取左右两堆最上面的扑克，
     * 点数较小的将其放入篮子里，另一张我们拿在手上较大的扑克暂时
     * 留在手上，而我们另一只手（左手取左边的扑克、右手取右边的扑克）
     * 继续拿扑克，和我们这只手上的扑克点数进行比较，将点数较小的放入篮子里
     * 这样一直循环直到有某一堆扑克已经全部被放入了篮子里，就将另一堆扑克
     * 直接放篮子即可
     * @param A     待排数组A
     * @param left  左边界
     * @param mid   中间边界
     * @param right 右边界
     */
    private void merge(int[] A, int left, int mid, int right)
    {
        int[] L = m_mergeL;
        int[] R = m_mergeR;
        int lLength = mid - left + 1;
        for (int i = 0, j = left; i < lLength; i++, j++)
            L[i] = A[j];
        int rLength = right - mid;
        for (int i = 0, j = mid + 1; i < rLength; i++, j++)
            R[i] = A[j];

        /**
         * L和R的长度之和必然是等于length = right - (left - 1) = lLength + rLength
         * 遍历需要归并的数组元素,也就是区间A[left, right],长度为length
         * 1 如果L中的元素任有剩余且R中的元素已经全部放入了A中就直接取L[a]放入A中，否则
         * 2 如果L中的元素已经全部放入了A中且R中的元素有剩余就直接取R[b]放入A中，否则
         * 3 如果L和R中元素均有剩余元素（此条件必然满足，由1、2判定条件和L、R和A[left, right]的长度关系保证，所以无需
         *   再次显示加上下标越界判定条件）;在此条件之上，那么L[a] < R[b],将L[a]放入A中，否则将R[b]放入A中
         *
         * 直到L、R中所有的元素都放回了A[left, right]中，循环结束，此时A[left, right]已经有序
         */
        int a = 0, b = 0;
        for (int i = left; i <= right; i++)
        {
            if (a < lLength && b == rLength)
                A[i] = L[a++];
            else if (a == lLength && b < rLength)
                A[i] = R[b++];
            else if (L[a] <= R[b])
                A[i] = L[a++];
            else
                A[i] = R[b++];
        }
    }

    /**
     * 堆排序相关:
     * 二叉堆可以分为最小堆和最大堆
     * 最大堆性质满足——除了根节点之外A[parentIndex(i)] >= A[i];
     * 最小对性质满足——除了根节点之外A[parentIndex(i)] <= A[i]
     * 这里采用最大堆来实现堆排序
     */

    /**
     * 在二叉堆中获取父亲的下标
     * 比如A[1]、A[2]的父亲的下标就是0
     * @param childIndex    孩子的下标
     * @return              父亲的下标
     */
    private int parentIndex(int childIndex)
    {
        return (childIndex - 1) >> 1;
    }

    /**
     * 在二叉堆中获取左孩子的下标
     * 比如A[1]的左孩子下标就是3
     * @param parentIndex   父亲的下标
     * @return              左孩子的下标
     */
    private int leftIndex(int parentIndex)
    {
        return (parentIndex << 1) + 1;
    }

    /**
     * 在二叉堆中获取右孩子的下标
     * 比如A[1]的右孩子下标就是4
     * @param parentIndex   父亲的下标
     * @return              右孩子的下标
     */
    private int rightIndex(int parentIndex)
    {
        return (parentIndex << 1) + 2;
    }

    /**
     * 这个函数的作用是维护最大堆的性质(递归版本) 时间复杂度O(lgn)
     * 调用这个函数时必须要保证以leftIndex(i)和rightIndex(i)为根节点的二叉树都是最大堆
     * 也就是说以下标i为父亲节点的左、右子树都必须是最大堆，但此时我们并未保证A[i]不小于其
     * 任意一个孩子；如果A[i]小于其孩子，此函数就会让其降级，而让孩子们升级，从而保持最大堆的性质
     * @param A          数组A
     * @param parent     以此下标为父亲节点的下标
     */
    private void maxHeapify(int[] A, int parent, int heapSize)
    {
        int left = leftIndex(parent);    // 左孩子下标
        int right = rightIndex(parent);  // 右孩子下标
        int largest = parent;            // 最大元素的下标，初始默认就是A[i]最大

        // 如果左孩子大于父亲则最大元素下标更新为左孩子的下标
        if (left < heapSize && A[left] > A[parent])
            largest = left;
        // 如果右孩子大于父亲则最大元素下标更新为右孩子的下标
        if (right < heapSize && A[right] > A[parent])
            largest = right;
        /**
         * 父亲不是最大的需要将其降级，父亲被降级之后可能任然不满足最大堆性质
         * 所以需要递归调用maxHeapify确保最大堆性质
         */
        if (parent != largest)
        {
            int temp = A[parent];
            A[parent] = A[largest];
            A[largest] = temp;
            // 父亲节点的下标现在是largest了,因为父亲和其孩子进行了交换
            maxHeapify(A, largest, heapSize);
        }
    }

    /**
     * 这个函数的作用是维护最大堆的性质(循环版本) 时间复杂度O(lgn)
     * 调用这个函数时必须要保证以leftIndex(i)和rightIndex(i)为根节点的二叉树都是最大堆
     * 也就是说以下标i为父亲节点的左、右子树都必须是最大堆，但此时我们并未保证A[i]不小于其
     * 任意一个孩子；如果A[i]小于其孩子，此函数就会让其降级，而让孩子们升级，从而保持最大堆的性质
     * @param A          数组A
     * @param parent     以此下标为父亲节点的下标
     */
    private void maxHeapify1(int[] A, int parent, int heapSize)
    {
        while (true)
        {
            int left = leftIndex(parent);    // 左孩子下标
            int right = rightIndex(parent);  // 右孩子下标
            int largest = parent;            // 最大元素的下标，初始默认就是A[i]最大

            // 如果左孩子大于父亲则最大元素下标更新为左孩子的下标
            if (left < heapSize && A[left] > A[parent])
                largest = left;
            // 如果右孩子大于父亲则最大元素下标更新为右孩子的下标
            if (right < heapSize && A[right] > A[parent])
                largest = right;
            // 父亲已经是最大的退出循环
            if (parent == largest)
                break;
            /**
             * 父亲不是最大的需要将其降级，父亲被降级之后可能任然不满足最大堆性质
             * 所以需要继续循环以确保最大堆性质
             */
            int temp = A[parent];
            A[parent] = A[largest];
            A[largest] = A[temp];
            // 父亲节点的下标现在是largest了,因为父亲和其孩子进行了交换
            parent = largest;
        }
    }

    /**
     * 将一个数组A转换成一个最大堆(时间复杂度是O(n),当然说是O(nlgn)也没错但是不够渐近紧确)
     * @param A 数组A
     */
    private void buildMaxHeap(int[] A)
    {
        /**
         * 闭区间A[A.length / 2, A.length - 1]都是叶子节点
         * 因为单个节点已经满足最大堆的性质，所以无需对其调用maxHeapify()
         * 所以从A[A.length / 2 - 1]到A[0]自底向上的调用maxHeapify()建立最大堆
         */
        for (int i = (A.length >> 1) - 1; i >= 0; i--)
            maxHeapify1(A, i, A.length);
    }

    /* ************ 普通 public *********** */
    /**
     * 插入排序：
     * 对指定的数组进行插入排序，排序完成之后
     * 整个序列按非降序排列
     * @param A
     */
    public void insertionSort(int[] A)
    {
        insertionSort(A, 0, A.length - 1);
    }

    /**
     * 快速排序:
     * 对指定数组进行快速排序，排序完成后
     * 整个序列按非降序排列
     * @param A
     */
    public void quickSort(int[] A)
    {
        quickSort(A, 0, A.length - 1);
    }

    /**
     * 随机化快速排序(快速排序的改进版本):
     * 对指定数组进行快速排序，排序完成后
     * 整个序列按非降序排列
     * @param A
     */
    public void randomizeQuickSort(int[] A)
    {
        randomizeQuickSort(A, 0, A.length - 1);
    }

    /**
     * 归并排序:
     * 采用分治法的思想对传入的数组进行排序
     * 因为归并排序过程中需要额外的和待排数组元素数量相同的存储空间
     * 为避免频繁的开辟和释放空间，所以在排序开始之前就分配好所需的空间
     * 等排序完成之后再交由gc回收
     * @param A
     */
    public void mergeSort(int[] A)
    {
        int length = (A.length + 1) / 2;
        m_mergeL = new int[length];
        m_mergeR = new int[length];
        mergeSort(A, 0, A.length - 1);
        m_mergeL = null;// let GC do its work
        m_mergeR = null;
    }

    /**
     * 堆排序O(nlgn)
     * 采用最大堆来实现排序，非降序序列
     * 首先建立一个最大堆
     * 循环执行
     * {
     *      交换A[0]和堆中的最后一个元素(交换后,堆中的最后一个元素必然是当前堆里最大的)
     *      交换后heapSize减小1，因为交换后，A[0]可能不再保持
     *      最大堆性质，所以需要调用maxHeapify()保持最大堆性质
     *      直到heapSize减小到2才退出循环
     * }
     * 循环结束后，整个数组即变得有序
     * @param A 待排数组
     */
    public void heapSort(int[] A)
    {
        buildMaxHeap(A);// O(n)
        // 循环时间复杂度O(nlgn)
        int heapSize = A.length;
        for (int i = A.length - 1; i >= 1; i--)
        {
            int temp = A[0];
            A[0] = A[i];
            A[i] = temp;
            maxHeapify1(A, 0, --heapSize);
        }
    }

    /**
     * 计数排序O(n) (使用的限制条件很苛刻，且需要开辟大量的存储空间,不过有一个很适用的例子:高考成绩排名[0, 751))
     * 而且也不一定要求数值的范围一定是[0,k)，可以是[a, b)，只不过下标需要进行映射了，代码编写麻烦些。
     * 首先要满足元素属于[0, k)(包含0而不包含k)里的一个整数，则有
     * 计数排序的思想还是比较简单的,我们知道小于a的元素个数，不就知道a的位置了吗
     * 比如，小于a的有10个元素，那么a就在A[10]
     * 当然我们要考虑到可能会有元素相同的情况
     * @param A     待排数组A
     * @param k     数据的上限边界(不包含)
     */
    public void countingSort(int[] A,final int k)
    {
        int[] C = new int[k];
        int[] OUT = new int[A.length];
        for (int i = 0; i < k; i++)
            C[i] = 0;
        // 记录对应数值的元素个数 比如C[1] == A中数值为1的元素的数量
        for (int i = 0; i < A.length; i++)
            C[A[i]]++;
        // 现在C[i]存的是小于等于A中小于等于i的元素数量
        for (int i = 1; i < k; i++)
            C[i] += C[i - 1];
        /**
         * 将元素放到其对应位置上；注意--C[A[i]]因为下标从0开始，得向左偏移一个位置
         * 比如,小于等于10的数字共有6个(这其中当然包含它自己)，所以10应该放在OUT[9]而不是OUT[10]中
         * 这里必须采用下标递减的循环方式放置元素，这样才能保证相等元素间原来的相对位置不发生变化，即排序的稳定性
         * 如果采用下标递增的循环方式放置元素，虽然排序的结果也是正确的，但是相等元素的相对位置发生了变化(对调了
         * 比如在未排序的数组中A[2]==A[5]==A[7]==A[8],放入OUT中他们的相对位置就成了A[8],A[7],A[5],A[2])，
         * 这样的话，这个排序就不是稳定的
          */
        for (int i = A.length - 1; i >= 1; i--)
            OUT[--C[A[i]]] = A[i];
        // 写回原数组
        for (int i = 0; i < A.length; i++)
            A[i] = OUT[i];
    }
}
