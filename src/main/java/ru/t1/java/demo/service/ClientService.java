package ru.t1.java.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.kafka.KafkaClientProducer;
import ru.t1.java.demo.model.Client;
import ru.t1.java.demo.model.dto.ClientDto;
import ru.t1.java.demo.repository.ClientRepository;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final KafkaClientProducer kafkaClientProducer;

    public void registerClients(List<Client> clients) {
        repository.saveAll(clients)
                .stream()
                .map(Client::getId)
                .forEach(kafkaClientProducer::send);
    }

    public <T> List<T> parseJson(String filePath, Class<T[]> clazz) {

        ObjectMapper mapper = new ObjectMapper();

        T[] dataArray;
        try {
            dataArray = mapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Arrays.asList(dataArray);
    }

    public List<ClientDto> parseJson() {


        // throw  new NotYetImplementedException();
        ObjectMapper mapper = new ObjectMapper();

        ClientDto[] clients = new ClientDto[0];
        try {
            clients = mapper.readValue(new File("src/main/resources/MOCK_DATA.json"), ClientDto[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Arrays.asList(clients);

        // ClientDto[] clients = mapper.readValue(new File("src/main/resources/MOCK_DATA.json"), ClientDto[].class);
        //return Arrays.stream(clients).map(ClientMapper::toEntity).collect(Collectors.toList());
    }

}
