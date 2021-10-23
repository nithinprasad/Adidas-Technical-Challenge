

## CODING CHALLENGE 
### Adidas

## Features
* Eureka Dashboard
* Caching
* Hiding secure services behind cloud gateway

### Repository

#### Consist of 4 Micro Services
* Public Service
* Eureka Naming Server
* Subscription Service
* Email Service


Api Doucmeation can be found in
[Documentation!](https://ajz007.stoplight.io/docs/adidas)

YAML File can be downloaded from
[Open Api Spec](https://github.com/nithinprasad/Adidas-Technical-Challenge/blob/main/openapi.yaml?raw=true)

## Architecture

![Diagram](https://github.com/nithinprasad/Adidas-Technical-Challenge/blob/main/Architecture.png?raw=true)

## Service Details

### Public Service
Implemented using Cloud API Gateway

Spring Cloud Gateway can help. Spring Cloud Gateway allows you to route traffic to your APIs using simple Java™ instructions  or with YAML configuration files . To hide your services, you set up your network so that the only server accessible from the outside is the gateway. The gateway then becomes a gate-keeper, controlling ingress and egress from outside.

Default Running on port `8989`


### Eureka Naming Server
Cloud-based services also have a habit of changing location and granularity without much warning. To cope better with this, you can combine a gateway with a service registry to allow the applications on your network to find each other dynamically at runtime. If you do this, your applications will be much more resilient to changes. Spring Cloud Netflix Eureka Server is one such service registry.

Default Running on port `8761`


### Subscription Service
Micorservice responsible for implementing subscrption logic 
including persistance of data in Persisitance layer and email notification once subscrpition is created

Default Running on port `7777`

### Email Service
Micorservice Responsible for implementing email notification

Default Running on port `8888`

## Running code
### Public Service

Code can run via

```python
./mvnw spring-boot:run
```
Application name in Naming server

```python
  application:
    name: GATEWAY-SERVICE
```

Default Running on port `8989`

#### Enviornment Varibale
| Name     | Default Value  | Describtion   |
|--------- |----------------|---------------|
|  EUREKA_HOST |localhost   | hostname/service where eureka server is running  |
|  EUREKA_PORT |8761   | port    |

### Eureka Naming Server
Code can run via

```python
./mvnw spring-boot:run
```
Application name in Naming server

```python
  application:
    name: EUREKA-SERVICE
```
Default Running on port `8761`

#### Enviornment Varibale
| Name     | Default Value  | Describtion   |
|--------- |----------------|---------------|
|  EUREKA_HOST |localhost   | hostname/service where eureka server is running  |
|  EUREKA_PORT |8761   | port    |


### Subscription Service
Code can run via

```python
./mvnw spring-boot:run
```
Application name in Naming server

```python
  application:
    name: SUBSCRIPTION-SERVICE
```

#### Enviornment Varibale
Code can run via

```python
./mvnw spring-boot:run
```
Application name in Naming server

```python
  application:
    name: SUBSCRIPTION-SERVICE
```
Default Running on port `7777`

| Name     | Default Value  | Describtion   |
|--------- |----------------|---------------|
|  EUREKA_HOST |localhost   | hostname/service where eureka server is running  |
|  EUREKA_PORT |8761   | port    |


### Email Service
Code can run via

```python
./mvnw spring-boot:run
```
Application name in Naming server

```python
  application:
    name: EMAIL-SERVICE
```

#### Enviornment Varibale
Default Running on port `8888`

| Name     | Default Value  | Describtion   |
|--------- |----------------|---------------|
|  EUREKA_HOST |localhost   | hostname/service where eureka server is running  |
|  EUREKA_PORT |8761   | port    |


## Running via Docker image

* Step 1 : Convert into docker image
### Public Service

Can convert to docker image using

```python
./mvnw spring-boot:build-image
```

### Eureka Naming Server
Can convert to docker image using

```python
./mvnw spring-boot:build-image
```

### Subscription Service
Can convert to docker image using

```python
./mvnw spring-boot:build-image
```


### Email Service
Can convert to docker image using

```python
./mvnw spring-boot:build-image
```


* Step 2 : Run via compose file

Run the container using
```python
docker-compose -f docker-compose.yaml up -d
```

Sample compose file 
```python
 version: '3'
 services:
  adidas-eureka-service:
     image: adidas-eureka-service:0.0.1-SNAPSHOT
     #ports:
      #- 3001:8761
  adidas-public-service:
    image: adidas-public-service:0.0.1-SNAPSHOT
    ports:
     - 3000:8989
    environment:
     - EUREKA_HOST=adidas-eureka-service
     - EUREKA_PORT=8761
  adidas-subscription-service:
    image: adidas-subscription-service:0.0.1-SNAPSHOT
    #ports:
     #- 3002:7777
    environment:
     - EUREKA_HOST=adidas-eureka-service
     - EUREKA_PORT=8761
  adidas-email-service:
    image: adidas-email-service:0.0.1-SNAPSHOT
    #ports:
     #- 3003:8888
    environment:
     - EUREKA_HOST=adidas-eureka-service
     - EUREKA_PORT=8761
```
Can stop the container using
```python
docker-compose -f docker-compose.yaml down
```

## Running via Docker image on docker hub

All images are available in docker hub

* nithinprasad549/adidas-eureka-service:latest
* nithinprasad549/adidas-public-service:latest
* nithinprasad549/adidas-email-service:latest
* nithinprasad549/adidas-subscription-service:latest

Can Start the container using
```python
docker-compose -f docker-compose-hub.yaml up -d
```
Can Stop  the container using
```python
docker-compose -f docker-compose-hub.yaml down
```


### Eureka Dasboard
Once the services are up we can verify the same using
```python
{hosname}:{gatewatip}/registry
```
![Diagram](https://github.com/nithinprasad/Adidas-Technical-Challenge/blob/main/eureka.png?raw=true)
