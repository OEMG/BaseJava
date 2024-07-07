package com.basejava.webapp;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {

        final MainConcurrency mainConcurrency = new MainConcurrency();
//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    mainConcurrency.dec();
                }
            });

            thread.start();
//            threads.add(thread);
        }
//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        System.out.println(mainConcurrency.counter);
    }

    private void inc() {
        synchronized (LOCK1) {
            synchronized (LOCK2) {
                counter++;
            }
        }
    }

    private void dec() {
        synchronized (LOCK2) {
            synchronized (LOCK1) {
                counter--;
            }
        }
    }
}
