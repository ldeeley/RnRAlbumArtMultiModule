# RnRAlbumArt - Microservices Project

The high-level objective of this project is to build and deploy a Microservices application to the public cloud (AWS) and in the process demonstrate
* key concepts
* Microservices Architecture concerns/possible solutions
* best practices


The RnRAlbumArt - Micrososervices Project will be built over a number of iterations with each iteration highlighting and addressing a particular concern.

## Iteration  - 2 - Objectives

* Implement a CRUD repository accessible by REST endpoints to persist application User data
* Implement Unit and Integration tests
* Persist User data to a MySQL database running in a container in the same cluster
* Add a Global Exception Handler for the application

(Security - Authentication/Authorization and User data encryption later)

At the end of this Iteration, should be able to :

* access the application, running in the cloud, to perform CRUD operations for User records via POSTMAN
* Execute unit & integration tests as part of the build stage

![](Images/Iteration%202%20end%20state.png)

![](Images/spring%20boot.png)  - The Spring Boot app at this stage has
* WebLayer - RestController
* Service Layer
* Repository Layer
* Exception Handler



![](Images/git.png) - The GitHub repo is [https://github.com/ldeeley/RnRAlbumArt](https://github.com/ldeeley/RnRAlbumArt)


## Docker

##### Running MySQL inside a Docker container locally/Connecting SpringBoot app to this instance

MySQL - Pull an image from Docker hub to create a MySQL database inside a Docker container. This command will spin up a MySQL database docker image, exposing container port 3306 to the localport 3307. 


```plaintext
leste@AQUARIUS MINGW64 ~/IdeaProjects/RnRAlbumArt (master)
$ docker run --name mydockermysql --net=rocknroll -p 3307:3306 -e MYSQL_ROOT_PASSWORD=password -d mysql:latest
Unable to find image 'mysql:latest' locally
latest: Pulling from library/mysql
46ef68baacb7: Pulling fs layer
94c1114b2e9c: Pulling fs layer
ff05e3f38802: Pulling fs layer
41cc3fcd9912: Pulling fs layer
07bbc8bdf52a: Pulling fs layer
6d88f83726a9: Pulling fs layer
cf5c7d5d33f7: Pulling fs layer
9db3175a2a66: Pulling fs layer
feaedeb27fa9: Pulling fs layer
41cc3fcd9912: Waiting
07bbc8bdf52a: Waiting
6d88f83726a9: Waiting
cf5c7d5d33f7: Waiting
9db3175a2a66: Waiting
cf91e7784414: Pulling fs layer
b1770db1c329: Pulling fs layer
feaedeb27fa9: Waiting
cf91e7784414: Waiting
b1770db1c329: Waiting
94c1114b2e9c: Verifying Checksum
94c1114b2e9c: Download complete
ff05e3f38802: Verifying Checksum
ff05e3f38802: Download complete
07bbc8bdf52a: Verifying Checksum
07bbc8bdf52a: Download complete
6d88f83726a9: Verifying Checksum
6d88f83726a9: Download complete
41cc3fcd9912: Verifying Checksum
41cc3fcd9912: Download complete
9db3175a2a66: Verifying Checksum
9db3175a2a66: Download complete
feaedeb27fa9: Verifying Checksum
feaedeb27fa9: Download complete
cf91e7784414: Download complete
b1770db1c329: Verifying Checksum
b1770db1c329: Download complete
46ef68baacb7: Verifying Checksum
46ef68baacb7: Download complete
cf5c7d5d33f7: Verifying Checksum
cf5c7d5d33f7: Download complete
46ef68baacb7: Pull complete
94c1114b2e9c: Pull complete
ff05e3f38802: Pull complete
41cc3fcd9912: Pull complete
07bbc8bdf52a: Pull complete
6d88f83726a9: Pull complete
cf5c7d5d33f7: Pull complete
9db3175a2a66: Pull complete
feaedeb27fa9: Pull complete
cf91e7784414: Pull complete
b1770db1c329: Pull complete
Digest: sha256:15f069202c46cf861ce429423ae3f8dfa6423306fbf399eaef36094ce30dd75c
Status: Downloaded newer image for mysql:latest
7aad61a650db6079b640d0d17a62b211b8f20d599569b558326e75cef8dc39fd

leste@AQUARIUS MINGW64 ~/IdeaProjects/RnRAlbumArt (master)
$ docker ps
CONTAINER ID   IMAGE                COMMAND                  CREATED          STATUS          PORTS                               NAMES
7aad61a650db   mysql:latest         "docker-entrypoint.s…"   8 seconds ago    Up 6 seconds    33060/tcp, 0.0.0.0:3307->3306/tcp   mydockermysql
```


In order to bake the Spring Boot application into a Docker image, it is necessary to specify a Dockerfile

```plaintext
FROM openjdk:11
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

Issue the following command to build a Docker image with the SpringBoot app baked inside

```plaintext
leste@AQUARIUS MINGW64 ~/IdeaProjects/RnRAlbumArt (master)
$ docker build -t rnralbumart  .
#1 [internal] load build definition from Dockerfile
#1 sha256:4ac259960de770a53360811b011be042b48dcece456e2a756d0c8ce2a90180a1
#1 DONE 0.0s

#1 [internal] load build definition from Dockerfile
#1 sha256:4ac259960de770a53360811b011be042b48dcece456e2a756d0c8ce2a90180a1
#1 transferring dockerfile: 31B done
#1 DONE 0.0s

#2 [internal] load .dockerignore
#2 sha256:2f20e1b29549e72677b5d4aa31785348fdec3259781db064526f95acbbdff83d
#2 transferring context: 2B done
#2 DONE 0.0s

#3 [internal] load metadata for docker.io/library/openjdk:11
#3 sha256:87dc9b3cee4adf6787fd792601b37fcaad0ed6bd5314a02f15c26446f91634ad
#3 DONE 0.5s

#4 [1/2] FROM docker.io/library/openjdk:11@sha256:99bac5bf83633e3c7399aed725c8415e7b569b54e03e4599e580fc9cdb7c21ab
#4 sha256:8c2fff55dee4aa503dfc85309a0c6a50b5d76e4b584cef86f8babc8f37841c8e
#4 DONE 0.0s

#5 [internal] load build context
#5 sha256:734478a3c723f3e2231887afb48985b06480327f27ab1654e2cf956ecae37a02
#5 transferring context: 39.02MB 0.7s done
#5 DONE 0.7s

#4 [1/2] FROM docker.io/library/openjdk:11@sha256:99bac5bf83633e3c7399aed725c8415e7b569b54e03e4599e580fc9cdb7c21ab
#4 sha256:8c2fff55dee4aa503dfc85309a0c6a50b5d76e4b584cef86f8babc8f37841c8e
#4 CACHED

#6 [2/2] COPY target/*.jar app.jar
#6 sha256:eeec6734e03e8f906633ddda9ca35c05fe541f09c9378cf33df2e1c58a5a5491
#6 DONE 0.3s

#7 exporting to image
#7 sha256:e8c613e07b0b7ff33893b694f7759a10d42e180f2b4dc349fb57dc6b71dcab00
#7 exporting layers
#7 exporting layers 0.3s done
#7 writing image sha256:4bd2045ae13533a2ebc92c44e6778de786a861a5b16ccc2c121d698a0f51f3d7 done
#7 naming to docker.io/library/rnralbumart done
#7 DONE 0.3s
```

Note, because we want to SpringBoot app to connect with MySQL inside a Docker container on the same network we need to set the Datasource configuration in application.yml as below.

```yaml
spring:
  sql:
    init:
      mode: never
  datasource:
    driver-class-name:  com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://host.docker.internal:3307/rnralbumart
    username: root
    password: password
```


Start the container and check both are running

```yaml
leste@AQUARIUS MINGW64 ~/IdeaProjects/RnRAlbumArt (master)
$ docker run -d --name rnralbumart --net=rocknroll -p 8080:8080 rnralbumart:latest
3c270f3316eecff25a5dca8b965efa0671f2be86a1d78ec42d0af6691765606d

leste@AQUARIUS MINGW64 ~/IdeaProjects/RnRAlbumArt (master)
$ docker ps
CONTAINER ID   IMAGE                COMMAND                  CREATED          STATUS          PORTS                               NAMES
3c270f3316ee   rnralbumart:latest   "java -jar /app.jar"     8 seconds ago    Up 5 seconds    0.0.0.0:8080->8080/tcp              rnralbumart
9892fe2dbbff   mysql:latest         "docker-entrypoint.s…"   42 minutes ago   Up 42 minutes   33060/tcp, 0.0.0.0:3307->3306/tcp   mydockermysql
```

Finally, use POSTMAN to access REST endpoints and perform CRUD operations on the MySQL database inside the Docker container

## Testing

#### Unit Tests



#### Integration Tests

Make use of Testcontainers. Instantiate a disposable MySQL Docker container for the purpose of Integration tests.

```xml
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>1.18.3</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>mysql</artifactId>
    <version>1.17.6</version>
    <scope>test</scope>
</dependency>
```

In the Test Class configure the container

```java
@SpringBootTest(properties = {
        "spring.test.database.replace=NONE",
        "spring.datasource.url=jdbc:tc:mysql:latest://rnralbumart/",
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
        "spring.datasource.username=root",
        "spring.datasource.password=password"
})
@AutoConfigureMockMvc
public class RnRAlbumArtIntegrationTest
```

Integration Tests should make REST calls to the User RESTController endpoint and check the reponse. A helper method getUserRequest - to build Request Body

```java
@Test
void shouldCreateNewUser() throws Exception {
    UserRequestDTO userRequestDTO = getUserRequest();
    String userRequestDTOString = objectMapper.writeValueAsString(userRequestDTO);
    mockMvc.perform(MockMvcRequestBuilders.post("/rnr/user")
                    .contentType(MediaType.APPLICATION_JSON)
            .content(userRequestDTOString))
            .andExpect(jsonPath("$.status").value("CREATED"));
}
```


##### Build Stage

buildspec.yml - instructions on how to proceed with the build

```yaml
version: 0.2

phases:
  install:
    commands:
      - echo Executing Install Phase
  pre_build:
    commands:
      - mvn clean install
      - echo Logging in to Amazon ECR...
      - aws --version
      - docker version
      - aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 716945757783.dkr.ecr.us-east-1.amazonaws.com
      - REPOSITORY_URI=716945757783.dkr.ecr.us-east-1.amazonaws.com/aws-rnralbumart-ecr-repository
      - COMMIT_HASH=$(echo $CODEBUILD_RESOLVED_SOURCE_VERSION | cut -c 1-7)
      - IMAGE_TAG=build-$(echo $CODEBUILD_BUILD_ID | awk -F":" '{print $2}')
  build:
    commands:
      - echo Build started on `date`
      - echo Building the Docker image...
      - docker build -t $REPOSITORY_URI:latest .
      - docker tag $REPOSITORY_URI:latest $REPOSITORY_URI:$IMAGE_TAG
  post_build:
    commands:
      - echo Build completed on `date`
      - echo Pushing the Docker images...
      - docker images
      - docker push $REPOSITORY_URI:latest
      - docker push $REPOSITORY_URI:$IMAGE_TAG
      - echo writing image definitions file...
      - printf '[{"name":"aws-springwebmvc","imageUri":"%s"}]' $REPOSITORY_URI:$IMAGE_TAG > imagedefinitions.json
      - cat imagedefinitions.json
artifacts:
  files:
    - target/rnralbumart.jar
    - imagedefinitions.json
```


Dockerfile - instructions on how to build the container

```plaintext
FROM openjdk:11
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```
