package com.qq.reader.zebra.task;

import com.qq.reader.zebra.log.Logger;
import com.qq.reader.zebra.utils.ZebraUtil;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanglulu on 2020/10/19.
 * for 任务执行器
 */
public class TaskHandler {
    private static final String TAG = "TaskHandler";
    private static TaskHandler instance;
    /**执行线程池*/
    private ExecutorService executor;
    /**任务队列 FIFO*/
    private final LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    /**用于执行 Task*/
    private final Runnable invokeTask = () -> {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Runnable task = taskQueue.take();
                Logger.d(TAG, "添加任务线程池：" + task);
                addExecutor(task);
            }
        } catch (InterruptedException e) {
            Logger.e(TAG, "invokeTask 抛出异常");
            e.printStackTrace();
        }
    };

    public static TaskHandler getInstance() {
        if (instance == null) {
            synchronized (TaskHandler.class) {
                if (instance == null) {
                    instance = new TaskHandler();
                }
            }
        }
        return instance;
    }

    private TaskHandler() {
        //启动执行任务
        executorService().execute(invokeTask);
    }

    private synchronized ExecutorService executorService() {
        if (executor == null) {
            //核心线程：4 最大线程数：10 存活时间：5 分钟
            executor = new ThreadPoolExecutor(
                    3,
                    10,
                    5 * 60,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(), ZebraUtil.threadFactory("DataProviderLoader"), (r, executor) -> {
                //处理被抛出来的任务(被拒绝的任务)
                enqueue(r);
                Logger.e(TAG, "executorService: 任务拒绝！");
            });
        }
        return executor;
    }

    /**入队添加任务*/
    public synchronized void enqueue(Runnable task) {
        try {
            taskQueue.add(task);
        } catch (Exception e) {
            Logger.e(TAG, "addTask 抛出异常");
            e.printStackTrace();
        }
    }

    /**添加到线程池执行*/
    private synchronized void addExecutor(Runnable runnable) {
        executorService().execute(runnable);
    }
}
