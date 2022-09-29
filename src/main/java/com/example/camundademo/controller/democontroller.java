package com.example.camundademo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.camundademo.producer.Producer;
import io.camunda.zeebe.client.ZeebeClient;

@RestController
public class democontroller {
	
	@Autowired
	ZeebeClient client;
	
	@Autowired
	public Producer  producer;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/kafkastart")
		  public Map sendMessage(@RequestBody Map Message) {
		    producer.publishToTopic(Message);
		    System.out.println(Message);
		    return Message;
	}
	
	@PostMapping("/Complete")
	public void handleJob(@RequestBody Map<String, Object> body) {

		
		System.out.println("Complete key : " + body);
		
		String getKey = (String) body.get("jobKey");
		
        System.out.println("getKey : "+getKey);
		
		long key = Long.parseLong(getKey);
		//Map variableMap = new HashMap<String, Object>();
	  // variableMap.put("corespondentData", key);
		 client.newCompleteCommand(key).variables("").send().join();

	}
	    
	@GetMapping("/cancel/{key}")
	public String cancel(@PathVariable long key) {
	    
	    System.out.println("Cancelling  the flow");
	    client.newCancelInstanceCommand(key).send();
	    return "flow cancelled";
	    
	}
	
 }
	
		
	
	

