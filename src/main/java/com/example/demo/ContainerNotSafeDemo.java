/**
 * Copyright (C), 2007-2019, 北京易才博普奥管理顾问有限公司
 * FileName: ContainerNotSafeDemo
 * Author:   Xiaoliang Ma
 * Date:     2019/4/18 0018 22:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.demo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 〈一句话功能简述〉
 * 〈集合不安全问题〉
 *
 * @author alone
 * @create 2019/4/18 0018
 * @since 1.0.0
 */
public class ContainerNotSafeDemo {

    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }

    }

    public static void setNotSafe() {
        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
        //hashSet底层为HashMap，set关心的只是key，对应的value是恒定的常量Object
        new HashSet<>();//底层为HashMap


    }


    public static void listNotSafe() {
        //ArrayList线程不安全
        List<String> list = new CopyOnWriteArrayList<>();


        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
        //java.util.ConcurrentModificationException
        /*
          1.故障现象
           java.util.ConcurrentModificationException
         2.导致原因
            并发争抢修改导致，一个线程正在修改，另一个线程争抢，导致并发修改异常
         3.解决方案
            3.1 Vector
            3.2 Collections.synchronizedList(new ArrayList<>());
            3.3 new CopyOnWriteArryaList
                写时复制
                    CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加，而是先将当前容器Object[]进行copy，
                    复制出一个新的容器Object[] newElements，然后新的容器Object[] newElements里添加元素，添加完语速之后，
                    再将原容器的引用指向新的容器setArray(newElements)。这样做的好处是可以对CopyOnWrite容器进行并发的读，
                    而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器
            public boolean add(E e) {
                final ReentrantLock lock = this.lock;
                lock.lock();
                try {
                    Object[] elements = getArray();
                    int len = elements.length;
                    Object[] newElements = Arrays.copyOf(elements, len + 1);
                    newElements[len] = e;
                    setArray(newElements);
                    return true;
                } finally {
                    lock.unlock();
                }
            }


         4.优化建议（同样的错误不犯第二次）
         */
    }

}