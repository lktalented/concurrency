package com.xhh.concurrency.example.commonSafe;

import java.util.Iterator;
import java.util.Vector;

public class VectorExample3 {

    /**
     * 由于foreach循环比较简单，所以一般我们在遍历数组和集合的时候，都会优先考虑使用它。
     * 但是在需要边迭代边删除的情况下，不能使用foreach，必须显示写出迭代器对象，否则会报错： java.util.ConcurrentModificationException
     * @param v1
     */
    private static void test1(Vector<Integer> v1) { // foreach
        for(Integer i : v1) {
            if (i.equals(3)) {
                v1.remove(i);
            }
        }
    }

    /**
     * 在使用Iterator的时候，迭代器会新建一个线程，把原来的线程中的对象重新拷贝一份，
     * 在进行删除，修改等操作时，原来的线程只负责迭代，而Iterator负责迭代和删除操作，
     * Iterator每次迭代都会检查迭代器里的对象和原线程中的对象个数是否一致，不一致则抛出：ConcurrentModificationException。
     *
     * 解决方法：不能使用集合中的remove方法，使用Iterrator中的remove方法。
     * @param v1
     */
    private static void test2(Vector<Integer> v1) { // iterator
        Iterator<Integer> iterator = v1.iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            if (i.equals(3)) {
                iterator.remove();
                //v1.remove(i);

            }
        }
    }

    // success
    private static void test3(Vector<Integer> v1) { // for
        for (int i = 0; i < v1.size(); i++) {
            if (v1.get(i).equals(3)) {
                v1.remove(i);
            }
        }
    }

    public static void main(String[] args) {

        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        test2(vector);
    }
}
