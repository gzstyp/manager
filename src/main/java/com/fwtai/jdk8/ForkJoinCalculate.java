package com.fwtai.jdk8;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 并行流和串行流
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-08-19 21:12
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
public class ForkJoinCalculate extends RecursiveTask<Long>{

    private long start;

    private long end;

    private final long THRESHOLD = 100000L;

    public ForkJoinCalculate(){}

    public ForkJoinCalculate(final long start,final long end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute(){
        long length = end - start;
        if(length <= THRESHOLD){
            long sum = 0;
            for(long i = start;i<= end;i++){
                sum += i;
            }
            return sum;
        }else{
            final long middle = (start + end) / 2;
            ForkJoinCalculate left = new ForkJoinCalculate(start,middle);
            left.fork();
            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1,end);
            right.fork();
            return left.join() + right.join();
        }
    }

    //使用 for循环
    public void demoFor(){
        final Instant start = Instant.now();
        long sum = 0;
        for(long i = 0; i < 10000000000L; i++){
            sum += i;
        }
        final Instant end = Instant.now();
        final long mi = Duration.between(start,end).toMillis();
        System.out.println("耗时:"+mi);
        System.out.println(sum);
    }

    //使用 ForkJoin 框架
    protected void demoForkJoin(){
        final Instant start = Instant.now();
        final ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0,10000000000L);
        Long sum = pool.invoke(task);
        final Instant end = Instant.now();
        final long mi = Duration.between(start,end).toMillis();
        System.out.println("耗时:"+mi);
        System.out.println(sum);
    }

    public static void main(String[] args){
        //new ForkJoinCalculate().demoFor();// 3662,-5340232226128654848
        new ForkJoinCalculate().demoForkJoin();// 1873,-5340232216128654848
    }
}