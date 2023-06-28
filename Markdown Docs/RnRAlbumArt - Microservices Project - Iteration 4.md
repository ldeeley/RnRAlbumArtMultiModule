# RnRAlbumArt - Microservices Project

The high-level objective of this project is to build and deploy a Microservices application to the public cloud (AWS) and in the process demonstrate
* key concepts
* Microservices Architecture concerns/possible solutions
* best practices
* CaaS - the model for deployment will be Containers as a Service


The RnRAlbumArt - Micrososervices Project will be built over a number of iterations with each iteration highlighting and addressing a particular concern.

## Iteration  - 4 - Objectives

* Refactor as a Multi-Module Maven Project
* 12-Factor App [ 3. Config] 
    - Setup Spring Cloud Config Server
    - Setup Spring Cloud Config client
    - Maintain environment config using VCS (GitHub)
* Expand Test Cases

![](Images/add%20a%20config%20server.png)

At the end of this Iteration, should be able to :

* when the User Service starts, as a Config Client, it will obtain configuration data for connecting to the mySQL database via the Config Server. Connnection data will be environment specific according to the Spring Profile


### Setting up a Config Server in a Docker container

### Configuring the User service as Config Client

### Establishing a Multi-Module Maven project

### Using AWS EKS to orchestrate the Docker containers startup




