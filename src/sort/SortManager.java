package sort;

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
    private SortManager()
    {
        LOGGER.info("SortManager()");
    }
}
