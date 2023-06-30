# RnRAlbumArt - Microservices Project

The high-level objective of this project is to build and deploy a Microservices application to the public cloud (AWS) and in the process demonstrate
* key concepts
* Microservices Architecture concerns/possible solutions
* best practices
* CaaS - the model for deployment will be Containers as a Service


The RnRAlbumArt - Micrososervices Project will be built over a number of iterations with each iteration highlighting and addressing a particular concern.

## Iteration  - 5 - Objectives

* Implement Config Server
* Dockerise the Config Server, Make it a Eureka Discovery Client
* Implement User service as a config-client 12-Factor App [3. Externalise Configuration under VCS]

![](Images/config%20and%20git.png)

### Setting up a Config Server

include these dependencies

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

add annotations to the main class

![](Images/config%20annotations.png)

edit the application.yml file of the Config Server to show where the Eureka server is hosted and GitHub repo (and Branch) that contains all the config data

![](Images/config%20repo%20details.png)

### Setting up a Config Client

Tell the Config client where the config server is located and the name of the application for which the config details are needed

![](Images/config%20client%20application%20yml.png)

