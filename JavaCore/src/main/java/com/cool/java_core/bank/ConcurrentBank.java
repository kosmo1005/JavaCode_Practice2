package com.cool.java_core.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentBank {
    private final Map<Long, BankAccount> accounts = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong();

    public BankAccount createAccount(long initialBalance) {
        long id = idGen.incrementAndGet();
        BankAccount account = new BankAccount(id, initialBalance);
        accounts.put(id, account);
        System.out.printf("Создан счет №%d с балансом %d.%n", id, initialBalance);
        return account;
    }

    public void transfer(BankAccount from, BankAccount to, long amount) {

        BankAccount first = from.getId() < to.getId() ? from : to;
        BankAccount second = from.getId() < to.getId() ? to : from;

        first.getLock().lock();
        second.getLock().lock();

        try {
            if (from.withdraw(amount)) {
                to.deposit(amount);
            }
        } finally {
            second.getLock().unlock();
            first.getLock().unlock();
        }
    }

    public long getTotalBalance() {
        long sum = 0;

        List<BankAccount> snapshot;
        synchronized (accounts) {
            snapshot = new ArrayList<>(accounts.values());
        }

        for (BankAccount acc : snapshot) {
            sum += acc.getBalance();
        }

        return sum;
    }
}
