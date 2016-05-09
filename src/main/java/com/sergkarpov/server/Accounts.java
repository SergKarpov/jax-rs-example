package com.sergkarpov.server;

import com.sun.jersey.spi.resource.Singleton;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by serg on 05.05.2016.
 */
@Singleton
public class Accounts {
    private static Accounts instance = new Accounts();

    private Accounts() {
        balance = new HashMap<String, BigDecimal>();

        // Data for test
        balance.put("0001", new BigDecimal("200"));
        balance.put("0002", new BigDecimal("100"));

    }

    public static Accounts getInstance(){
        return instance;
    }

    private Map<String, BigDecimal> balance;

    public synchronized BigDecimal getBalance(String account){
        return balance.get(account);
    }

    private void setBalance(String account, BigDecimal amount){
         balance.put(account, amount);
    }

    public synchronized void transfer(BigDecimal amount, String fromAccount, String toAccount) {
        if (!balance.containsKey(fromAccount)) {
            throw new RuntimeException("fromAccount " + fromAccount + " not found!");
        }
        if (!balance.containsKey(toAccount)) {
            throw new RuntimeException("toAccount " + toAccount + " not found!");
        }
        setBalance(fromAccount, getBalance(fromAccount).subtract(amount));
        setBalance(toAccount, getBalance(toAccount).add(amount));
    }
}
