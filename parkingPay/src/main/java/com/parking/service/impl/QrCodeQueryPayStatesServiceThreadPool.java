package com.parking.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class QrCodeQueryPayStatesServiceThreadPool {

    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public static void executeThread(Runnable runnable) {
        cachedThreadPool.execute(runnable);
    }
}
