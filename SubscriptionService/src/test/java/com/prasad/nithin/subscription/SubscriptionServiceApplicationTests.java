package com.prasad.nithin.subscription;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.prasad.nithin.subscription.entity.GENDER;
import com.prasad.nithin.subscription.entity.Subscription;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class SubscriptionServiceApplicationTests {

    private static final Set<Subscription> SUBSCRIPTIONS = new HashSet<>();
    
    private static Subscription currentSub;
    
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort
    int randomServerPort;
   

    private String url;

    @BeforeEach
    public void setUp() {
        url = String.format("http://localhost:%d/", randomServerPort);
    }

    @Test
    @Order(1)
    void contextLoads() {
    }

    @Test
    @Order(2)
    public void verifySubCreation() {

        ResponseEntity<Subscription> responseEntity = invokeEntity("subscriptions/subscribe", HttpMethod.POST, generateRandomSubscription(), Subscription.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Subscription subscribe = responseEntity.getBody();
        Assertions.assertNotNull(subscribe);
        Assertions.assertNotNull(subscribe.get_id());
        SUBSCRIPTIONS.add(subscribe);
        currentSub=subscribe;
    }
    
    @Test
    @Order(3)
    public void verifySubDetails() {

        ResponseEntity<Subscription> responseEntity = invokeEntity("subscriptions/"+currentSub.get_id(), HttpMethod.GET, generateRandomSubscription(), Subscription.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Subscription subscribe = responseEntity.getBody();
        Assertions.assertNotNull(subscribe);
        Assertions.assertNotNull(subscribe.get_id());
        Assertions.assertEquals(subscribe.get_id(),currentSub.get_id());

    }
    
    @Test
    @Order(4)
    public void getAll() {

        ResponseEntity<Subscription[]> responseEntity = invokeEntity("subscriptions/all",HttpMethod.GET, generateRandomSubscription(), Subscription[].class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Subscription[] subscriptions = responseEntity.getBody();
        Assertions.assertEquals(subscriptions.length,SUBSCRIPTIONS.size());
        Optional<Subscription> optionalSub=Arrays.asList(subscriptions).stream().filter(each->each.get_id().equals(currentSub.get_id())).findFirst();
        if(!optionalSub.isPresent()) {
        	Assertions.fail();
        }
    }
    
    @Test
    @Order(4)
    public void cancelSub() {

        ResponseEntity<String> responseEntity = invokeEntity("subscriptions/"+currentSub.get_id(), HttpMethod.DELETE, generateRandomSubscription(), String.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        SUBSCRIPTIONS.remove(currentSub);
    }
    
    @Test
    @Order(5)
    public void verifyDetailsofCancelldSub() {

        ResponseEntity<String> responseEntity = invokeEntity("subscriptions/"+currentSub.get_id(), HttpMethod.GET, generateRandomSubscription(), String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    
    @Test
    @Order(6)
    public void verifyDeleteSubRemovedFromList() {

        ResponseEntity<Subscription[]> responseEntity = invokeEntity("subscriptions/all", HttpMethod.GET, generateRandomSubscription(), Subscription[].class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Subscription[] subscriptions = responseEntity.getBody();
        Optional<Subscription> optionalSub=Arrays.asList(subscriptions).stream().filter(each->each.get_id().equals(currentSub.get_id())).findFirst();
        if(optionalSub.isPresent()) {
        	Assertions.fail();
        }
    }


    private Subscription generateRandomSubscription() {
		Subscription subscription=new Subscription();
		subscription.setDateOfBirth(generateDob());
		subscription.setEmail(generateRandomEmail());
		subscription.setGender(Arrays.asList(GENDER.values()).parallelStream().findAny().get());
		subscription.setHaveConsent(new Random().nextBoolean());
		subscription.setNewsLetterId(UUID.randomUUID().toString());
		return subscription;
	}
    
    public static LocalDate generateDob() {
    	Random random = new Random();
    	int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
    	int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
    	long randomDay = minDay + random.nextInt(maxDay - minDay);

    	LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
		return randomBirthDate;
    }

	
    private <T> ResponseEntity<T> invokeEntity(String path, HttpMethod method, Object entity, Class<T> reposeClass) {
        return invokeEntity(path, method, entity, reposeClass, Collections.emptyMap());
    }

    private <T> ResponseEntity<T> invokeEntity(String path, HttpMethod method, Object entity, Class<T> reposeClass, Map<String, Object> args) {
        return invokeEntity(path, method, entity, reposeClass, args, Collections.emptyMap());
    }

    private <T> ResponseEntity<T> invokeEntity(String path, HttpMethod method, Object entity, Class<T> reposeClass, Map<String, Object> pathTemplate, Map<String, String> queryParam) {
        HttpHeaders headers = new HttpHeaders();
        String fullURI = url + path;
        LinkedMultiValueMap<String, String> multiValuesMap = new LinkedMultiValueMap<String, String>();
        queryParam.forEach(multiValuesMap::add);
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(fullURI)
                .queryParams(multiValuesMap)
                .uriVariables(pathTemplate);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<?> httpEntity = new HttpEntity<Object>(entity, headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(builder.toUriString(), method, httpEntity, reposeClass, pathTemplate);
        log.info("Rest api response {}", responseEntity.toString());
        return responseEntity;
    }
    
    public static String generateRandomEmail() {
        log.info("Generating a Random email String");
        String allowedChars = "abcdefghijklmnopqrstuvwxyz" + "1234567890" + "_-.";
        String email = "";
        String temp = RandomStringUtils.random(10, allowedChars);
        email = temp.substring(0, temp.length() - 9) + "@testdata.com";
        return email;
    }

}