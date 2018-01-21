package com.wisn.mainmodule.protocal.service.pool;



import com.wisn.mainmodule.protocal.service.HandlerByteToMessage;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class NioSelectorRunnablePool {
    private HandlerByteToMessage handlerByteToMessage;
    private AtomicInteger bossInteger = new AtomicInteger();
    private Boss[] bosses;
    private AtomicInteger workInteger = new AtomicInteger();
    private Worker[] workers;
    private AtomicInteger businessInteger = new AtomicInteger();
    private Business[] business;


    public NioSelectorRunnablePool(Executor bossExecutor, Executor workExecutor, Executor businessExecutor, HandlerByteToMessage handlerByteToMessage) {
        initBoss(bossExecutor, 1);
        initWorke(workExecutor, 3);
        initBusiness(businessExecutor, 9);
        this.handlerByteToMessage = handlerByteToMessage;
    }

    private void initBusiness(Executor businessExecutor, int count) {
        business = new NioSocketBusiness[count];
        for (int i = 0; i < count; i++) {
            business[i] = new NioSocketBusiness(businessExecutor, "business" + i, this);
        }
    }


    private void initBoss(Executor bossExecutor, int count) {
        bosses = new NioSocketBoss[count];
        for (int i = 0; i < count; i++) {
            bosses[i] = new NioSocketBoss(bossExecutor, "boss" + i, this);
        }
    }

    private void initWorke(Executor workExecutor, int count) {
        workers = new NioSocketWorker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new NioSocketWorker(workExecutor, "worker" + i, this);
        }
    }

    public Boss nextBoss() {
        return bosses[Math.abs(bossInteger.incrementAndGet() % bosses.length)];
    }

    public Worker nextWorker() {
        return workers[Math.abs(workInteger.incrementAndGet() % workers.length)];
    }

    public Business nextBusiness() {
        return business[Math.abs(businessInteger.incrementAndGet() % business.length)];
    }

    public HandlerByteToMessage getHandlerByteToMessage() {
        return handlerByteToMessage;
    }

    public void setHandlerByteToMessage(HandlerByteToMessage handlerByteToMessage) {
        this.handlerByteToMessage = handlerByteToMessage;
    }
}
