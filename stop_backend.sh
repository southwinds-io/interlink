#!/usr/bin/env bash
#
#    Interlink Configuration Management Database
#    © 2018-Present - SouthWinds Tech Ltd - www.southwinds.io
#
#    Licensed under the Apache License, Version 2.0 (the "License");
#    you may not use this file except in compliance with the License.
#    You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
#    Unless required by applicable law or agreed to in writing, software distributed under
#    the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
#    either express or implied.
#    See the License for the specific language governing permissions and limitations under the License.
#
#    Contributors to this project, hereby assign copyright in this code to the project,
#    to be licensed under the same terms as the rest of the code.
#
#  Usage: sh stop_backend.sh
#  Description: removes 2 containers using Docker in the local manchine:
#   - PostgreSQL (TCP 5432) backend database for Onix Web API
#   - Artemis (TCP 8161) backend AMQP broker for Onix Web API
#

echo delete existing backend containers
docker rm -f ilmsg
docker rm -f ildb