/**
 * Copyright (C), 2007-2019, 北京易才博普奥管理顾问有限公司
 * FileName: CASDemo
 * Author:   Xiaoliang Ma
 * Date:     2019/4/16 0016 23:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈一句话功能简述〉 
 * 〈〉
 *
 * @author alone
 * @create 2019/4/16 0016
 * @since 1.0.0
 * 1 CAS 是什么
 *      比较并交换
 */

public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current data:" + atomicInteger.get());

    }

}