package auth_server.infrastructure;

import auth_server.enums.AuthLogMessage;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

  private final KafkaTemplate<String, Map<String, String>> kafkaTemplate;

  @Value("${app.kafka.topic-name}")
  private String topicName;

  public void createEvent(Map<String, String> payload, String type) {
    log.info(AuthLogMessage.EVENT_SENDING.getMessage() , type);

    ProducerRecord<String, Map<String, String>> producerRecord = new ProducerRecord<>(topicName, payload);
    producerRecord.headers().add("type", type.getBytes());

    CompletableFuture<SendResult<String, Map<String, String>>> future = kafkaTemplate.send(
        producerRecord);
    future.thenAccept(result -> {
      log.info(AuthLogMessage.EVENT_PUBLISHED.getMessage(), payload);
    }).exceptionally(ex -> {
      log.error(AuthLogMessage.EVENT_ERROR.getMessage(), ex);
      return null;
    });
  }
}

