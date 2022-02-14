package com.strimzitest.kafkaconsumer;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import model.User;

@Service
@Slf4j
public class KafkaConsumer{
	   @KafkaListener(topics = "my-topic", groupId = "kafka-joydeep", containerFactory = "kafkaListenerContainerFactory")
	    public void listenWithHeaders(
	            @Payload User message,
	            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
	        log.info("Received Message: \n" + message + "\nfrom partition: " + partition);
	    }

}
