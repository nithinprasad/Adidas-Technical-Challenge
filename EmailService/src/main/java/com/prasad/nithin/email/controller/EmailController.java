package com.prasad.nithin.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.nithin.email.service.EmailNotifyService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmailController {

	@Autowired
	EmailNotifyService emailNotifyService;
	
	@PostMapping
	public String doEmail(@RequestBody String body) {
		emailNotifyService.doNotify(body);
		return "EMail Sent";
	}
	
}
