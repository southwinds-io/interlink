#!/usr/bin/env bash
#
# Interlink CMDB - © 2018-Present - SouthWinds Tech Ltd - www.southwinds.io
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
#
# Contributors to this project, hereby assign copyright in this code to the project,
# to be licensed under the same terms as the rest of the code.
#
JAVA_URL=$1
JAVA_SHA256=$2
JAVA_HOME=$3

apk add gzip tar curl
rm -rf /var/cache/apk/*

set -eux;
curl -fL -o /openjdk.tgz "$JAVA_URL"
echo "$JAVA_SHA256 */openjdk.tgz" | sha256sum -c -
mkdir -p "$JAVA_HOME"
tar --extract --file /openjdk.tgz --directory "$JAVA_HOME" --strip-components 1
rm /openjdk.tgz
