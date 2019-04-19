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
public class LockDemo {
    /***
     * 公平锁 是指多个线程按照申请锁的顺序来获取锁，类似打饭，先来后到
     * 非公平锁 是指多个线程获取锁的顺序并不是按照申请锁的顺序，有坑后申请的线程比先申请的线程优先获取锁
     *          在高并发的情况下，有可能会造成优先级翻转或饥饿现象
     */
    /**
     * 括号内不填默认为非公平锁，填true为公平锁
     * Lock lock = new ReentrantLock(true);
     *
     * 公平锁：在并发环境中，每个线程在获取锁时会先查看此锁维护的等待队列，如果为空，或者当前线程等待队列的第一个，就占有锁，
     * 否则就会加入到等待队列中，以后会按照FIFO的规则从队列中取到自己
     *
     * 非公平锁：非公平锁比较粗鲁，上来就直接尝试获取锁，如果尝试失败，就在采用类似公平锁的方式
     * Synchronized也是非公平锁 
     */
    Lock lock = new ReentrantLock();

}