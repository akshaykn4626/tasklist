package com.example.camundademo.producer;
import java.util.HashMap;
import java.util.Map;
import javax.print.attribute.HashAttributeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import io.camunda.zeebe.client.ZeebeClient;

@Service
public class Consumer {

 @Autowired
  ZeebeClient zeebe;

 @KafkaListener(topics = "zeebe", groupId = "mygroup")
  public String consumeFromTopic(Map message) {
    System.out.println("Consumed message " + message);
    Map inputMap = new HashMap<String,Object>();
    
   String Housebreak =  (String) message.get("Housebreak");
	/*
	 * String Correspondentemail = (String) message.get("Correspondentemail");
	 * String BOuseremail = (String) message.get("BOuseremail");
	 */    
    inputMap.put("Housebreak", Housebreak);
	/*
	 * inputMap.put("Correspondentemail", Correspondentemail);
	 * inputMap.put("BOuseremail", BOuseremail);
	 */
    
   zeebe.newPublishMessageCommand().messageName("start").correlationKey("").variables(inputMap).send();
    return Housebreak;

 }

}