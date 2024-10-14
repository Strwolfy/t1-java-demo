package ru.t1.java.demo.mapper;

import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.AccountType;
import ru.t1.java.demo.model.dto.AccountDto;

public class AccountMapper {

    public static Account toEntity(AccountDto dto) {
        Account account = new Account();
        account.setClientId(dto.getClientId());
        account.setAccountType(AccountType.valueOf(dto.getAccountType()));
        account.setBalance(dto.getBalance());
        return account;
    }
}

