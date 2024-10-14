package ru.t1.java.demo.util;

import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.AccountType;
import ru.t1.java.demo.model.dto.AccountDto;

import java.math.BigDecimal;

public class AccountMapper {

    public static Account toEntity(AccountDto dto) {
        if (dto == null) {
            throw new NullPointerException("AccountDto is null");
        }
        return Account.builder()
               // .id(dto.getId())  // Устанавливаем id, если нужно
                .clientId(dto.getClientId())
                .accountType(AccountType.valueOf(dto.getAccountType()))  // Преобразуем String в enum
                .balance(dto.getBalance())
                .build();
    }

    public static AccountDto toDto(Account entity) {
        if (entity == null) {
            throw new NullPointerException("Account entity is null");
        }
        return AccountDto.builder()
              //  .id(entity.getId())
                .clientId(entity.getClientId())
                .accountType(entity.getAccountType().name())  // Преобразуем enum в String
                .balance(entity.getBalance())
                .build();
    }
}
