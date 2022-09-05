#
#    Interlink Configuration Management Database
#    © 2018-Present - SouthWinds Tech Ltd - www.southwinds.io
#
#    Licensed under the Apache License, Version 2.0 (the "License");
#    you may not use this file except in compliance with the License.
#    You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
#
#    Contributors to this project, hereby assign copyright in this code to the project,
#    to be licensed under the same terms as the rest of the code.
#

# This dockerfile encapsulates the build process for Interlink Web API servicde
# The builder container is transient and downloads and install maven, package the Java app and extracts the
# Springboot uberjar files to improve startup times
# The release image copy the prepared app files from the builder image

# the builder transient container
FROM amazoncorretto:18-alpine-jdk as builder

RUN apk add unzip && rm -rf /var/cache/apk/*

ENV MAVEN_VERSION 3.8.6
ENV MAVEN_HOME /usr/lib/mvn
ENV PATH $MAVEN_HOME/bin:$PATH

# download and install maven in the build container
RUN wget http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  tar -zxvf apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  mv apache-maven-$MAVEN_VERSION /usr/lib/mvn

# define a working folder within the build container
WORKDIR /app

# copy the java project into the /app folder
COPY . .

# 1. package the app skipping the cucumber integration tests
#   (as it requires connectovity to database, that is not available within this process)
# 2. unzip the sprinboot uberjar into a /tmp folder ready to be copied by the release image
RUN mvn -Dmaven.test.skip=true -f pom.xml package && unzip -o ./target/*.jar -d /tmp

# the final release image
# uses universal base image rhel 8 minimal with OpenJ9 JVM and Open JDK 14
FROM registry.gitlab.com/southwinds/images/openjdk:14-j9-ubi8-min

MAINTAINER admin <admin@southwinds.io>

LABEL author="southwinds.io"

# copy the unzipped application files to the
COPY --from=builder /tmp/BOOT-INF/lib /app/lib
COPY --from=builder /tmp/META-INF /app/META-INF
COPY --from=builder /tmp/BOOT-INF/classes /app

USER 20

ENTRYPOINT ["java","-cp","app:app/lib/*","io/southwinds/interlink/App"]
