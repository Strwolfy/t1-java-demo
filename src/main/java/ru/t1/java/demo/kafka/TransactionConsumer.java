package ru.t1.java.demo.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.repository.TransactionRepository;

@Service
public class TransactionConsumer {

    @Autowired
    private TransactionRepository transactionRepository;  // Репозиторий для сохранения данных

    @KafkaListener(topics = "t1_demo_transactions", groupId = "group_id")  // Прослушиваем топик t1_demo_transactions
    public void consumeTransactionMessage(String message) throws JsonProcessingException {
        // Преобразуем JSON-сообщение в объект Transaction
        ObjectMapper objectMapper = new ObjectMapper();
        Transaction transaction = objectMapper.readValue(message, Transaction.class);

        // Сохраняем объект Transaction в базу данных
        transactionRepository.save(transaction);
    }

    private Transaction parseTransaction(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        Transaction transactionDto = null;

        try {
            // Преобразуем строку JSON в объект TransactionDto
            transactionDto = objectMapper.readValue(message, Transaction.class);
        } catch (Exception e) {

            System.err.println("Error parsing transaction message: " + e.getMessage());
            e.printStackTrace();
        }

        return transactionDto;
    }
}
