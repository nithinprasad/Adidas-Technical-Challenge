package com.prasad.nithin.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.nithin.email.service.EmailNotifyService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/email")
public class EmailController {

	@Autowired
	EmailNotifyService emailNotifyService;
	
	@PostMapping(value = "notify")
	public String doEmail(@RequestBody String body) {
		emailNotifyService.doNotify(body);
		return "Email Sent succcessfully";
	}
	
}
