package ink.usr.common.core.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 异步工具类
 */
public class AsyncUtil {

    /**
     * 使用了 Java 提供的 ExecutorService 接口来创建一个线程池
     */
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * 异步任务接口
     */
    public interface AsyncTask<T> {
        T call();
    }

    /**
     * 执行异步任务
     *
     * @param task 异步任务
     */
    public static <T> Future<T> executeAsyncTask(AsyncTask<T> task) {
        return executor.submit(task::call);
    }

    /**
     * 执行异步任务
     *
     * @param task 异步任务
     */
    public static void executeAsyncTask(Runnable task) {
        executor.submit(task);
    }

}
