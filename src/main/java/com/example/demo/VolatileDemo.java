/**
 * Copyright (C), 2007-2019, 北京易才博普奥管理顾问有限公司
 * FileName: VolatileDemo
 * Author:   Xiaoliang Ma
 * Date:     2019/4/16 0016 21:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author alone
 * @create 2019/4/16 0016
 * @since 1.0.0
 */
class MyData {
    volatile int number = 0;

    public void addT060() {
        this.number = 60;
    }

    //number前加了volatile修饰
    public void addPlusPlus() {
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addAtomic() {
        atomicInteger.getAndIncrement();
    }
}

/**
 * @param
 * @author xiaoliang Ma
 * @title
 * @description 1 验证volatile的可见性
 * 1.1 假如 int number = 0 ；number变量之前没有添加volatile关键字修饰没有可见性
 * 1.2 添加了volatile，可以解决可见性问题
 * <p>
 * <p>
 * <p>
 * 2.验证Volatile不保证原子性
 * 2.1 原子性的意思：
 * 不可分割，完整性，即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割。需要整体完整
 * 要么同时成功，要么同时失败
 * <p>
 * <p>
 * <p>
 * 2.2 volatile是否可以保证原子性
 * 2.3 why?
 *
 * 2.4 如何解决
 *      1. 加sync
 *      2.使用atomicInteger
 * @date 2019/4/16 0016
 * @return
 */

public class VolatileDemo {


    //volatile不保证原子性验证
    public static void main(String[] args) {

        MyData myData = new MyData();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAtomic();

                }
            }, String.valueOf(i)).start();

        }

        //需要等待上面20个线程都全部计算完成，再用main线程取得最终结果值
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t finally number value:" + myData.number);
        System.out.println(Thread.currentThread().getName()+"\t AtomicInteger value："+myData.atomicInteger);
    }

    //volatile可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
    public static void seeOkByVolatile() {
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addT060();
            System.out.println(Thread.currentThread().getName() + "\t updated number value:" + myData.number);
        }, "AAA").start();

        //第二个线程
        while (myData.number == 0) {
            //main线程一直等待循环，直到number值不再等于0
        }
        System.out.println(Thread.currentThread().getName() + "\t mission is over");

    }

}