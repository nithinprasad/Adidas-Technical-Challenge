package com.prasad.nithin.subscription.events;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.prasad.nithin.subscription.exception.CustomExcpetion;
import com.prasad.nithin.subscription.rest.RestClient;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailEventHandler {

	@Autowired
	RestClient restClient;
	
	@EventListener
	@Async
	public void handleEvent(EmailEvent emailEvent) throws CustomExcpetion {
		restClient.notifyEmail(emailEvent.getSubscription());
	}

}
