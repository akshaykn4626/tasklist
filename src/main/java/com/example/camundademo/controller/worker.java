package com.example.camundademo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;

@SpringBootApplication
@RestController
@CrossOrigin
public class worker {
	
	@Autowired
    private JavaMailSender mailSender;
	

	 final RestTemplate rest = new RestTemplate();
	    
		 
	 @ZeebeWorker(type = "Worker1", name = "Worker1")
     public void Task1(final JobClient client, final ActivatedJob job) throws MessagingException 
     {
        System.out.println("Worker1 Started");
        Map<String, Object> variablemap=job.getVariablesAsMap();
        
        System.out.println("variablemap===>"+variablemap);
        Map<String, Object> variablesAsMap = job.getVariablesAsMap();
               
               String sender="signup46523@gmail.com";
               String receiver = variablesAsMap.get("Correspondentemail").toString();
               String subject="Demo";
               String body ="<p><a href=\"http://localhost:4200/tasklist\">Click Here</a></p>";
              
               sendMail(sender, receiver, subject, body);
               String resultMessage = "Mail Sent Successfully to " + receiver;
              
                            HashMap<String, Object> variables = new HashMap<>();
                              variables.put("result", resultMessage);
              
                              client.newCompleteCommand(job.getKey())
                       .variables(variables)
                         .send().join();
        
         System.out.println("woker1 ended");
         
         
             
         }


	 @ZeebeWorker(type = "Worker2", name = "Worker2")
     public void Task2(final JobClient client, final ActivatedJob job) throws MessagingException 
     {
        System.out.println("Worker2 Started");
        Map<String, Object> variablemap=job.getVariablesAsMap();
        
        System.out.println("variablemap===>"+variablemap);
        Map<String, Object> variablesAsMap = job.getVariablesAsMap();
               
               String sender="signup46523@gmail.com";
               String receiver = variablesAsMap.get("BOuseremail").toString();
               String subject="Demo";
               String body ="<p><a href=\"http://localhost:4200/tasklist\">Click Here</a></p>";
              
               sendMail(sender, receiver, subject, body);
               String resultMessage = "Mail Sent Successfully to " + receiver;
              
                            HashMap<String, Object> variables = new HashMap<>();
                              variables.put("result", resultMessage);
              
                              client.newCompleteCommand(job.getKey())
                       .variables(variables)
                         .send().join();
        
        // client.newCompleteCommand(job.getKey()).send().join();
         System.out.println("woker2 ended");
         
         
             
         }


	 
	 
	 
	 
	 public void sendMail(String sender,String receiver, String subject, String body) 
	    {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom(sender);
	        message.setTo(receiver);
	        message.setSubject(subject);
	        message.setText(body);
	        mailSender.send(message);
	    }
	 
}
