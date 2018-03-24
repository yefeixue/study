package com.MyNetty.pool;

import com.MyNetty.NioServerBoss;
import com.MyNetty.NioServerWorker;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * selector线程管理者
 *
 * @author -琴兽-
 */
public class NioSelectorRunnablePool {
    /**
     * 场景演示：
     * 一个门
     * 10个客人
     * bosse   接待者（带到桌子）
     * worker  服务者（给客人点餐）
     */


    private final AtomicInteger bossIndex = new AtomicInteger();

    /**
     * boss线程数组   接待者 ---- 多个，可以同时接待多个客人
     */
    private Boss[] bosses;


    private final AtomicInteger workerIndex = new AtomicInteger();
    /**
     * worker线程数组   服务者 ---- 多个，可以同时接待多个客人
     */
    private Worker[] workeres;


    public NioSelectorRunnablePool(Executor boss, Executor worker) {
        initBoss(boss, 1);
        initWorker(worker,/*获取当前cpu可用个数*/ Runtime.getRuntime().availableProcessors() * 2);
    }

    /**
     * 初始化boss线程
     *
     * @param boss
     * @param count
     */
    private void initBoss(Executor boss, int count) {
        this.bosses = new NioServerBoss[count];
        for (int i = 0; i < bosses.length; i++) {
            bosses[i] = new NioServerBoss(boss, "boss thread " + (i + 1), this);
        }

    }

    /**
     * 初始化worker线程
     *
     * @param worker
     * @param count
     */
    private void initWorker(Executor worker, int count) {
        this.workeres = new NioServerWorker[count];
        for (int i = 0; i < workeres.length; i++) {
            workeres[i] = new NioServerWorker(worker, "worker thread " + (i + 1), this);
        }
    }

    /**
     * 获取一个worker
     *
     * @return
     */
    public Worker nextWorker() {
        return workeres[Math.abs(workerIndex.getAndIncrement() % workeres.length)];

    }

    /**
     * 获取一个boss
     *
     * @return
     */
    public Boss nextBoss() {
        return bosses[Math.abs(bossIndex.getAndIncrement() % bosses.length)];
    }

}
