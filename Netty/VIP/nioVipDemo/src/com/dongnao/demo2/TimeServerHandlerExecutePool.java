package com.dongnao.demo2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Five老师
 * @createTime 2017年11月13日 下午3:49:31
 * 
 */
public class TimeServerHandlerExecutePool {

	private ExecutorService executor;
	
	public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
		executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120l, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
	}

	public void execute(Runnable task) {
		executor.execute(task);
	}

}
