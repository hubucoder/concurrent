package cn.itcast.n4;

import java.util.ArrayList;
import java.util.HashSet;

public class TestThreadSafe {

    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;
    public static void main(String[] args) {

//        HashSet<Integer> set = new HashSet<>();
//        int[] ints = new int[set.size()];
//        for (Integer integer : set) {
//
//        }

//        ThreadUnsafe threadUnsafe = new ThreadUnsafe();
//        for (int i = 0; i < THREAD_NUMBER; i++) {
//            new Thread(() -> {
//                threadUnsafe.method1(LOOP_NUMBER);
//            }, "Thread" + i).start();
//        }

        ThreadSafeSubClass test = new ThreadSafeSubClass();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                test.method1(LOOP_NUMBER);
            }, "Thread" + (i+1)).start();
        }
    }
}
class ThreadUnsafe {
    ArrayList<String> list = new ArrayList<>();
    public void method1(int loopNumber) {
        for (int i = 0; i < loopNumber; i++) {
            method2();
            method3();
        }
    }

    private void method2() {
        list.add("1");
    }

    private void method3() {
        list.remove(0);
    }
}

class ThreadSafe {
    public final void method1(int loopNumber) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < loopNumber; i++) {
            method2(list);
            method3(list);
        }
    }

    public void method2(ArrayList<String> list) {
        list.add("1");
    }

    public void method3(ArrayList<String> list) {
        System.out.println(1);
        list.remove(0);
    }
}

class ThreadSafeSubClass extends ThreadSafe{
    @Override
    public void method3(ArrayList<String> list) {
//        System.out.println(2);
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}