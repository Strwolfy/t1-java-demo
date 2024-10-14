package ru.t1.java.demo.aop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class MetricAspectTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testMethodExecutionTimeAndKafkaMessage() throws Exception {
        // Имитируем вызов метода с аннотацией @Metric
        mockMvc.perform(MockMvcRequestBuilders.get("/test-metric-method"))
                .andExpect(status().isOk());

        // Проверяем, что сообщение было отправлено в Kafka
        verify(kafkaTemplate, times(1)).send(eq("t1_demo_metric_trace"), any(String.class));    }

    @RestController
    public static class TestController {

        @GetMapping("/test-metric-method")
        public ResponseEntity<String> testMetricMethod() throws InterruptedException {
            // Симулируем выполнение метода
            Thread.sleep(100);
            return ResponseEntity.ok("Test completed");
        }
    }
}
