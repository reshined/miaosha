package com;

import com.seckill.common.util.UUIDUtil;

public class Test {
    public static void main(String[] args) {
        System.out.println(A.str);
    }
}

class B {
    public static final String str = UUIDUtil.uuid();
    static {
        System.out.println("B");
    }
}

class A extends B{
    static {
        System.out.println("A");
    }
}
