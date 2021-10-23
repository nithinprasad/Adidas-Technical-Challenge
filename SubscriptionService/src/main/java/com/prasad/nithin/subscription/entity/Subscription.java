/**
 * 
 */
package com.prasad.nithin.subscription.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nithinprasad
 *
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String _id;
	private String firstName;
	@NotEmpty
	@Size(max = 70)
	@Email
	@NotNull
	private String email;
	@Enumerated(EnumType.STRING) 
	private GENDER gender;
	@Past(message = "date of birth must be less than today")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull 
	private LocalDate dateOfBirth;
	@NotNull
	private Boolean haveConsent;
	@NotEmpty
	@Size(max = 70)
	private String newsLetterId;
}
