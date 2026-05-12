package com.cool.java_core.bank;

import lombok.Getter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class BankAccount {
    private final long id;
    private long balance;

    private final Lock lock = new ReentrantLock();

    public BankAccount(long id, long initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public void deposit(long amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.printf("Пополняем баланс счета %d на сумму %d. Баланс стал %d.%n ", id, amount, balance);
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(long amount) {
        lock.lock();
        try {
            if (balance < amount) return false;
            balance -= amount;
            System.out.printf("Списываем со счета %d сумму %d. Баланс стал %d.%n ", id, amount, balance);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public long getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
