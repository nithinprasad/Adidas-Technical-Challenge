package com.prasad.nithin.subscription.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.nithin.subscription.entity.Subscription;
import com.prasad.nithin.subscription.exception.CustomExcpetion;
import com.prasad.nithin.subscription.service.SubscriptionService;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

	@Autowired
	SubscriptionService subscriptionService;

	@PostMapping(value = "subscribe")
	public ResponseEntity<Subscription> create(@Valid @RequestBody Subscription subscription) {

		return new ResponseEntity<>(subscriptionService.subscribe(subscription), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<Subscription> cancel(@PathVariable String id) throws CustomExcpetion {

		subscriptionService.cancel(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "all")
	public ResponseEntity getAllSubscriptions() {

		return new ResponseEntity<>(subscriptionService.getAllSubscriptions(), HttpStatus.OK);
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<Subscription> findSubscription(@PathVariable String id) throws CustomExcpetion {

		return new ResponseEntity<>(subscriptionService.findById(id), HttpStatus.OK);
	}

}
