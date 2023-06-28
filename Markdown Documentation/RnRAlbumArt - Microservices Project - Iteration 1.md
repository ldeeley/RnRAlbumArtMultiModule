# RnRAlbumArt - Microservices Project

The high-level objective of this project is to build and deploy a Microservices application to the public cloud (AWS) and in the process demonstrate
* key concepts
* Microservices Architecture concerns/possible solutions
* best practices


The RnRAlbumArt - Micrososervices Project will be built over a number of iterations with each iteration highlighting and addressing a particular concern.

## Iteration  - 1 - Objectives

* 12-Factor App [ 1. One Codebase tracked in revision control, many deploys]
* 12-Factor App [ 5. Build, Release, Run]

At the end of this Iteration, should be able to :

* make a code change on the developer desktop
* Developer Push code to GitHub
* AWS CodePipeline Auto detects a new commit
* AWS compiles new code, builds new Container image
* Deploys Container image to production
* Go to browser, access Rest Endpoint of App 

This will need refinement (later) but is first step.
It establishes one source of code, change control and a pipeline to the cloud.

To establish a working environment for the build, do the following

![](Images/pipeline%20flow.png)

![](Images/spring%20boot.png) - The Spring Boot app at this stage is nothing but a simple Spring-Web, RestController, single endpoint "Rock n Roll" message

![](Images/git.png) - The GitHub repo is [https://github.com/ldeeley/RnRAlbumArt](https://github.com/ldeeley/RnRAlbumArt)


## Getting started with CodePipeline

### Source Stage

Build a Codepipeline in AWS. Configure the Source Stage of the pipeline so that it is hooked into the GitHub repository. In this way, every time a code change is pushed to the GitHub repository, the Codepipeline recognises this and triggers a new build/deploy.

### Build Stage

buildspec.yml - instructions on how to proceed with the build inside the pipeline.
It uses Maven to compile the code, build a Docker image and then Push the resulting Docker image to the AWS ECR prior to deployment


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

### Deploy Stage

