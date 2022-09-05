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

FROM registry.access.redhat.com/ubi8/ubi-minimal

MAINTAINER skipper <skipper@southwinds.io>

LABEL author="southwinds.io"

ARG JAVA_HOME

ADD jdk $JAVA_HOME
ADD configureJDK.sh ./
RUN sh configureJDK.sh $JAVA_HOME
