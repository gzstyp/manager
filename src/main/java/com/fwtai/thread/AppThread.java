package com.fwtai.thread;

/**
 * 多线程练习
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-06-24 15:52
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
public class AppThread extends Thread{

    public AppThread(String name){
        super(name);
    }

    @Override
    public void run(){
        while(!interrupted()){
            System.out.println(getName() + "线程执行了……");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        AppThread appThread1 = new AppThread("first-thread");
        AppThread appThread2 = new AppThread("second-thread");
        appThread1.start();
        appThread2.start();

        appThread1.interrupt();
    }
}