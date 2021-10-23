/**
 * 
 */
package com.prasad.nithin.email.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author nithinprasad
 *
 */
@Service
@Slf4j
public class EmailNotifyService {

	
	public void doNotify(String entity) {
		
		//TODO: Call email service
		log.info("Email sent suvessfully {}",entity);
	}
	
	
}
