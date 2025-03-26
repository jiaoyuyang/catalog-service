#stage 1
#Start with a base image containing Java runtime
FROM openjdk:17 as build

# Add Maintainer Info
LABEL maintainer="jiaoyuyang <jiaoyuyang@live.com>"

# The application's jar file
ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} app.jar

#execute the application
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]