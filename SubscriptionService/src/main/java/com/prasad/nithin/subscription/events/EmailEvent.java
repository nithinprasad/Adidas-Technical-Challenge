/**
 * 
 */
package com.prasad.nithin.subscription.events;

import org.springframework.context.ApplicationEvent;

import com.prasad.nithin.subscription.entity.Subscription;

import lombok.Getter;

/**
 * @author nithinprasad
 *
 */
@Getter
public class EmailEvent extends ApplicationEvent{

	
	private Subscription subscription;
	
	public EmailEvent(Subscription subscription) {
		super(subscription);
		this.subscription=subscription;
	}

}
