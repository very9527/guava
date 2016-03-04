package com.google.test;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhiyun on 2016/3/4.
 */
public class TestRateLimiter {
    public static void main(String[] args) {
        RateLimiter limiter = RateLimiter.create(5.0);

        ListeningExecutorService executorService = MoreExecutors
                .listeningDecorator(Executors.newCachedThreadPool());


        for (int i = 0; i < 10; i++) {
            limiter.acquire(); // 请求RateLimiter, 超过permits会被阻塞

            final ListenableFuture<Integer> listenableFuture = executorService
                    .submit(new Task("is "+ i));
        }
    }


}

class Task implements Callable<Integer> {
    String str;
    public Task(String str){
        this.str = str;
    }
    @Override
    public Integer call() throws Exception {
        System.out.println("call execute.." + str);
        TimeUnit.SECONDS.sleep(1);
        return 7;
    }
}