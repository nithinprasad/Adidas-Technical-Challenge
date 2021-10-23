/**
 * 
 */
package com.prasad.nithin.subscription.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.prasad.nithin.subscription.entity.Subscription;
import com.prasad.nithin.subscription.events.EmailEventPublisher;
import com.prasad.nithin.subscription.exception.CustomExcpetion;
import com.prasad.nithin.subscription.repository.SubscriptionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author nithinprasad
 *
 */
@Service
@Slf4j
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository repository;
	
	@Autowired
	private EmailEventPublisher emailEventPublisher;

	@CacheEvict(cacheNames =  "subs",allEntries = true)
	public Subscription subscribe(Subscription subscription) {
		Subscription res= repository.save(subscription);
		emailEventPublisher.publishEvent(res);
		return res;
	}

	@Caching(evict = { 
			  @CacheEvict(cacheNames = ("subs"),allEntries = true), 
			  @CacheEvict(cacheNames = ("sub"), key="#id") })
	public void cancel(String id) throws CustomExcpetion {

		repository.deleteById(findById(id).get_id());

	}

	@Cacheable(cacheNames = ("subs"))
	public List<Subscription> getAllSubscriptions() {
		log.info("value returned from db");
		return repository.findAll();
	}

	@Cacheable(cacheNames = "sub", key="#id")
	public Subscription findById(String id) throws CustomExcpetion {
		log.info("value returned from db");
		return repository.findById(id).stream().findFirst()
				.orElseThrow(() -> new CustomExcpetion(CustomExcpetion.ExceptionTypes.ERROR_NOT_FOUND));
	}

}
