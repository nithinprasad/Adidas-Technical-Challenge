/**
 * 
 */
package com.prasad.nithin.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasad.nithin.subscription.entity.Subscription;

/**
 * @author nithinprasad
 *
 */
public interface SubscriptionRepository extends JpaRepository<Subscription, String>{

}
