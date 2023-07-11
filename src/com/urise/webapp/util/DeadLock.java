package com.urise.webapp.util;

class Table{
}

public class DeadLock {
    static final Table table1 = new Table();
    static final Table table2 = new Table();
    static public void algoritm1() {
        synchronized (table1) {
            printLog(1, 1);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (table2) {
                printLog(1, 2);
            }
        }
    }

    static public void algoritm2() {
        synchronized (table2) {
            printLog(2, 2);
            synchronized (table1) {
                printLog(2, 1);
            }
        }
    }

    static public void printLog(int algoritm, int table) {
        System.out.printf("Алгоритм %d | Работа с таблицей %d%n", algoritm, table);
    }

    public static void main(String[] args) {
        new Thread(DeadLock::algoritm1).start();
        new Thread(DeadLock::algoritm2).start();
    }
}