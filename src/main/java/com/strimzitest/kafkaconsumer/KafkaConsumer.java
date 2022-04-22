package com.strimzitest.kafkaconsumer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

import lombok.extern.slf4j.Slf4j;
import model.LogData;

@Service
@Slf4j
public class KafkaConsumer{
	@Autowired
	private FileUploader financeFileUploader;

	
	  @KafkaListener(topics = "my-topic", groupId = "kafka-joydeep", containerFactory = "kafkaListenerContainerFactory")
	    public void listenWithHeaders(
	            @Payload LogData message,
	            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
	        log.info("Received Message: \n" + message + "\nfrom partition: " + partition);
	        financeFileUploader.uploadS3Report(message);
	        log.info("uploaded to s3 bucket with success");
	    }


}
