/**
 * Copyright (C), 2007-2019, 北京易才博普奥管理顾问有限公司
 * FileName: T1
 * Author:   Xiaoliang Ma
 * Date:     2019/4/16 0016 22:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.demo;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author alone
 * @create 2019/4/16 0016
 * @since 1.0.0
 */
public class T1 {
    volatile int n = 0;

    public void add() {
        n++;
    }

    public static void main(String[] args) {
        T1 t1 = new T1();t1.add();
    }
}