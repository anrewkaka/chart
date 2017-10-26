# create image from java
FROM java:alpine

EXPOSE 80 443
VOLUME ["/opt/chart", "/opt/script", "/bin/bash"]

# add jar to docker
ADD ./script /opt/script

# set environment variable
ENV JAVA_OPTS=""

USER daemon

# setting entrypoint
# ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /opt/chart/app.jar

CM sudo chown -R daemon /bin/bash
