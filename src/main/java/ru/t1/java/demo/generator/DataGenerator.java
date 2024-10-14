package ru.t1.java.demo.generator;

import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.AccountType;
import ru.t1.java.demo.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private static final Random RANDOM = new Random();

    public static List<Account> generateAccounts(int count) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Account account = new Account();
            account.setClientId((long) i);
            account.setAccountType(RANDOM.nextBoolean() ? AccountType.DEBIT : AccountType.CREDIT);
            account.setBalance(BigDecimal.valueOf(RANDOM.nextDouble() * 10000));
            accounts.add(account);
        }
        return accounts;
    }

    public static List<Transaction> generateTransactions(int count, List<Account> accounts) {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Transaction transaction = new Transaction();
            Account account = accounts.get(RANDOM.nextInt(accounts.size()));
            // transaction.setAccountId(account.getId());
            transaction.setAmount(BigDecimal.valueOf(RANDOM.nextDouble() * 1000));
            // transaction.setTimestamp(LocalDateTime.now());
            transactions.add(transaction);
        }
        return transactions;
    }
}
