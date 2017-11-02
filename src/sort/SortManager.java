package sort;

import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by yangyuan on 2017/11/1.
 */
public class SortManager
{
    /* ***********  静态private *************** */
    private static Logger LOGGER = Logger.getLogger(SortManager.class.toString());

    private static enum EInstance
    {
        Instance,
        ;

        private SortManager m_instance;

        EInstance()
        {
            m_instance = new SortManager();
        }

        SortManager getInstance()
        {
            return m_instance;
        }
    }

    /* *********** 静态 public ************** */
    public static SortManager getInstance()
    {
        return EInstance.Instance.getInstance();
    }

    /* ************ 普通 private *********** */
    private int[] m_mergeL;
    private int[] m_mergeR;

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
        Random random = new Random();
        int index = left + random.nextInt(right - left + 1);
        int temp = A[index];
        A[index] = A[right];
        A[right] = temp;
        return partition(A, left, right);
    }

    private void mergeSort(int[] A, int left, int right)
    {
        if (left >= right)
            return;
        int mid = (left + right) / 2;
        mergeSort(A, left, mid);
        mergeSort(A, mid + 1, right);
        merge(A, left, mid, right);
    }

    private void merge(int[] A, int left, int mid, int right)
    {
        int lLength = mid - left + 1;
        for (int i = 0, j = left; i < lLength; i++, j++)
            m_mergeL[i] = A[j];

        int rLength = right - mid;
        for (int i = 0, j = mid + 1; i < rLength; i++, j++)
            m_mergeR[i] = A[j];

        for (int i = left; i <= right; i++)
        {
            
        }
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

    public void mergeSort(int[] A)
    {
        int length = (A.length + 1) / 2;
        m_mergeL = new int[length];
        m_mergeR = new int[length];
        mergeSort(A, 0, A.length - 1);
        m_mergeL = null;// let GC do its work
        m_mergeR = null;
    }
}
