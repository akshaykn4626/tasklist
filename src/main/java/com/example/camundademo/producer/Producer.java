package com.example.camundademo.producer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
  public static final String topic = "zeebe";


 @Autowired
  private KafkaTemplate < String, Object > kafkaTemp;

    public Map publishToTopic(Map message) {
        System.out.println("Publishing to topic " + topic);
        kafkaTemp.send(topic,message);
        return message;
     }
    

 }



