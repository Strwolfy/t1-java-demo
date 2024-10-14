package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.java.demo.aop.HandlingResult;
import ru.t1.java.demo.aop.LogException;
import ru.t1.java.demo.aop.Track;
import ru.t1.java.demo.kafka.AccountConsumer;
import ru.t1.java.demo.kafka.KafkaClientProducer;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.model.dto.AccountDto;
import ru.t1.java.demo.model.dto.ClientDto;
import ru.t1.java.demo.service.AccountService;
import ru.t1.java.demo.service.ClientService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {

    @Value("${t1.kafka.topic.account_registration}")
    private String account_registration;


    private final ClientService clientService;
    private final KafkaClientProducer kafkaClientProducer;

    @Value("${t1.kafka.topic.client_registration}")
    private String topic;

    @LogException
    @Track
    @GetMapping(value = "/parse")
    @HandlingResult
    public void parseSource()  {
        List<ClientDto> clientDtos = clientService.parseJson();
        clientDtos.forEach(dto -> {
            kafkaClientProducer.sendTo(topic, dto);
        });
    }

    @LogException
    @Track
    @GetMapping(value = "account/parse")
    @HandlingResult
    public void parseAccount() {
        List<AccountDto> clientDtos = clientService.
                parseJson("src/main/resources/Account.json", AccountDto[].class);

        clientDtos.forEach(dto -> {
            kafkaClientProducer.sendTo(account_registration, dto);
        });
    }

}
