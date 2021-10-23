/**
 * 
 */
package com.prasad.nithin.subscription.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prasad.nithin.subscription.entity.Subscription;
import com.prasad.nithin.subscription.events.EmailEventPublisher;
import com.prasad.nithin.subscription.exception.CustomExcpetion;
import com.prasad.nithin.subscription.repository.SubscriptionRepository;

/**
 * @author nithinprasad
 *
 */
@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository repository;
	
	@Autowired
	private EmailEventPublisher emailEventPublisher;

	public Subscription subscribe(Subscription subscription) {
		Subscription res= repository.save(subscription);
		emailEventPublisher.publishEvent(res);
		return res;
	}

	public void cancel(String id) throws CustomExcpetion {

		repository.deleteById(findById(id).get_id());

	}

	public List<Subscription> getAllSubscriptions() {
		return repository.findAll();
	}

	public Subscription findById(String id) throws CustomExcpetion {
		return repository.findById(id).stream().findFirst()
				.orElseThrow(() -> new CustomExcpetion(CustomExcpetion.ExceptionTypes.ERROR_NOT_FOUND));
	}

}
