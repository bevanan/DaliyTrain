package main.java.com.bevan.thread.source;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zbf
 * @since 2024/6/11 下午6:25
 *
 * 银行系统的账户转账
 * 题目：编写一个多线程程序，模拟银行账户之间的并发转账。要求使用线程安全的方式处理转账，确保在并发环境下不会出现数据不一致的问题。
 */
public class BankTransfer {
    public static void main(String[] args) throws InterruptedException {
        Account account1 = new Account(1000);
        Account account2 = new Account(1000);
        Bank bank = new Bank();

        Runnable task1 = () -> {
            for (int i = 0; i < 10; i++) {
                bank.transfer(account1, account2, 100);
            }
        };

        Runnable task2 = () -> {
            for (int i = 0; i < 10; i++) {
                bank.transfer(account2, account1, 100);
            }
        };

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final balance of account1: " + account1.getBalance());
        System.out.println("Final balance of account2: " + account2.getBalance());
    }
}

class Bank {
    public void transfer(Account from, Account to, int amount) {
        Account firstLock = from.getBalance() < to.getBalance() ? from : to;
        Account secondLock = from.getBalance() < to.getBalance() ? to : from;

        firstLock.getLock().lock();
        secondLock.getLock().lock();
        try {
            if (from.withdraw(amount)) {
                to.deposit(amount);
                System.out.println("Transferred " + amount + " from " + from + " to " + to);
            } else {
                System.out.println("Failed to transfer " + amount + " from " + from + " to " + to);
            }
        } finally {
            secondLock.getLock().unlock();
            firstLock.getLock().unlock();
        }
    }
}

class Account {
    private int balance;
    private final Lock lock = new ReentrantLock();

    public Account(int initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(int amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public int getBalance() {
        return balance;
    }

    public Lock getLock() {
        return lock;
    }
}