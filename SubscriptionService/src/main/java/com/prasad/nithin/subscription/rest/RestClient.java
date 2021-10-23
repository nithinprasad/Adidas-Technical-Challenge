package com.prasad.nithin.subscription.rest;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.prasad.nithin.subscription.entity.Subscription;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestClient {

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	@LoadBalanced
	public static RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Value("${email.uri}")
	private String EMAIL_GATEWAY_URL;

	@Async
	public ResponseEntity<String> notifyEmail(Subscription subscription) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(subscription.toString(), headers);
		log.info("Post {} to URI {}", subscription, EMAIL_GATEWAY_URL);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(EMAIL_GATEWAY_URL, entity, String.class);
		log.info("Response {}", responseEntity);
		return responseEntity;

	}

}
