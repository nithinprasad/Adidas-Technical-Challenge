package com.prasad.nithin.ps.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/emailFallback")
    public Mono<String> emailFallback() {
        return Mono.just("Email Service is taking too long to respond or is down. Please try again later");
    }
    @RequestMapping("/subscriptionFallBack")
    public Mono<String> subscriptionFallBack() {
        return Mono.just("Subscription Service is taking too long to respond or is down. Please try again later");
    }
    @RequestMapping("/eurekaFallback")
    public Mono<String> eurekaFallback() {
        return Mono.just("Eureka Service is taking too long to respond or is down. Please try again later");
    }
}