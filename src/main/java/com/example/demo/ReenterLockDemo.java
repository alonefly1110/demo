/**
 * Copyright (C), 2007-2019, 北京易才博普奥管理顾问有限公司
 * FileName: LockDemo
 * Author:   Xiaoliang Ma
 * Date:     2019/4/18 0018 23:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author alone
 * @create 2019/4/18 0018
 * @since 1.0.0
 */


class Phone implements Runnable {
    public synchronized void sendSMS() throws Exception {
        System.out.println(Thread.currentThread().getId() + "\t invoked sendSMS()");
        sendEmail();
    }

    private synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getId() + "\t ####################invoked sendEmail()");
    }

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoked get()");
            set();
        } finally {
            lock.unlock();

        }
    }

    public void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t ############invoked set()");
        } finally {
            lock.unlock();

        }
    }
}


public class ReenterLockDemo {
    /***
     * 公平锁 是指多个线程按照申请锁的顺序来获取锁，类似打饭，先来后到
     * 非公平锁 是指多个线程获取锁的顺序并不是按照申请锁的顺序，有坑后申请的线程比先申请的线程优先获取锁
     *          在高并发的情况下，有可能会造成优先级翻转或饥饿现象
     */
    /**
     * 括号内不填默认为非公平锁，填true为公平锁
     * Lock lock = new ReentrantLock(true);
     * <p>
     * 公平锁：在并发环境中，每个线程在获取锁时会先查看此锁维护的等待队列，如果为空，或者当前线程等待队列的第一个，就占有锁，
     * 否则就会加入到等待队列中，以后会按照FIFO的规则从队列中取到自己
     * <p>
     * 非公平锁：非公平锁比较粗鲁，上来就直接尝试获取锁，如果尝试失败，就在采用类似公平锁的方式
     * Synchronized也是非公平锁
     * <p>
     * 可重入锁（递归锁）
     * 指的是同一线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码，在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
     * 也就是说，线程可以进入任何一个它已经拥有的锁所同步着的代码块
     * <p>
     * 可重入锁可以避免死锁
     * <p>
     * 只要锁配对，加几层锁都可以 加锁几次就要解锁几次
     */
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t3 = new Thread(phone, "t3");
        Thread t4 = new Thread(phone, "t4");


        t3.start();
        t4.start();
    }
}