package com.wisn.mainmodule.protocal.service;


import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractNioSelector implements Runnable {
    private Executor executor;
    private String threadName;
    protected Selector selector;
    private AtomicBoolean weakup = new AtomicBoolean();
    private Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();

    public AbstractNioSelector(Executor executor, String threadName) {
        this.executor = executor;
        this.threadName = threadName;
        openSelector();
    }

    private void openSelector() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.execute(this);
    }

    public void registerTask(Runnable task) {
        taskQueue.add(task);
        Selector selector = this.selector;
        if (selector != null) {
            if (weakup.compareAndSet(false, true)) {
                selector.wakeup();
            }
        } else {
            taskQueue.remove(task);
        }
    }

    @Override
    public void run() {
        Thread.currentThread().setName(threadName);
        while (true) {
            weakup.set(false);
            selector(selector);
            processTaskQueue();
            process(selector);
        }

    }


    private void processTaskQueue() {
        for (; ; ) {
            Runnable poll = taskQueue.poll();
            if (poll == null) {
                break;
            }
            poll.run();
        }
    }

    public abstract void selector(Selector selector);

    public abstract void process(Selector selector);
}
