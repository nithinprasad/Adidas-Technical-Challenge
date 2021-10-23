/**
 * 
 */
package com.prasad.nithin.subscription.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.prasad.nithin.subscription.entity.Subscription;

/**
 * @author nithinprasad
 *
 */
@Component
public class EmailEventPublisher {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	/**
     * Publish event.
     *
     * @param payment the payment
     */
    public void publishEvent(Subscription subscription){
            EmailEvent notifyEmail=new EmailEvent(subscription);
            applicationEventPublisher.publishEvent(notifyEmail );
        }
}