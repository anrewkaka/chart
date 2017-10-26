# create image from open jdk
FROM openjdk:8-jdk-alpine

# create tmp
VOLUME /tmp

# add jar to docker
ADD build/libs/chart-0.0.1-SNAPSHOT.jar app.jar

# set environment variable
ENV JAVA_OPTS=""

# setting entrypoint
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
