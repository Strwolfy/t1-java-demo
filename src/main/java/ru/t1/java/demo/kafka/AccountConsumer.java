package ru.t1.java.demo.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Client;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.model.dto.AccountDto;
import ru.t1.java.demo.model.dto.ClientDto;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.service.AccountService;
import ru.t1.java.demo.service.ClientService;
import ru.t1.java.demo.util.AccountMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountConsumer {

    private final AccountService accountService;
    private final AccountRepository accountRepository;  // Внедрение репозитория

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
            topics = "${t1.kafka.topic.account_registration}",
          //  groupId = "group_id",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeAccount(@Payload List<AccountDto> messageList,
                               Acknowledgment ack) {
        try {
            List<Account> accounts =
                    messageList.stream().map(AccountMapper::toEntity).toList();

            accountService.registerAccount(accounts);
        } finally {
            ack.acknowledge();
        }
    }

    private Account parseAccountMessage(String message) {
        try {
            return objectMapper.readValue(message, Account.class);
        } catch (Exception e) {
            System.err.println("Failed to parse account message: " + message);
            e.printStackTrace();
            return null;
        }
    }
}

