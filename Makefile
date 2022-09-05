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

# the name of the container registry repository
REPO_NAME=repository.gitlab.com/southwinds/images

# the name of the application
APP_NAME=interlink

# the version of the application
APP_VER = 1.0.0

# get old images that are left without a name from new image builds (i.e. dangling images)
DANGLING_IMS = $(shell docker images -f dangling=true -q)

# produce a new version tag
set-version:
	sh version.sh $(APP_VER)

# run the integration tests
test:
	sh start_backend.sh
	export INTERLINK_EVENTS_ENABLED=true; mvn test;
	#sh stop_backend.sh

# build the interlink docker image
snapshot-image:
	docker build -t $(REPO_NAME)/$(APP_NAME)-snapshot:$(shell cat src/main/resources/version) .
	docker tag $(REPO_NAME)/$(APP_NAME)-snapshot:$(shell cat src/main/resources/version) $(REPO_NAME)/$(APP_NAME)-snapshot:latest

snapshot-push:
	docker push $(REPO_NAME)/$(APP_NAME)-snapshot:$(shell cat src/main/resources/version)
	docker push $(REPO_NAME)/$(APP_NAME)-snapshot:latest

# build the interlink docker image
release-image:
	docker build -t $(REPO_NAME)/$(APP_NAME):$(shell cat src/main/resources/version) .
	docker tag $(REPO_NAME)/$(APP_NAME):$(shell cat src/main/resources/version) $(REPO_NAME)/$(APP_NAME):latest

release-push:
	docker push $(REPO_NAME)/$(APP_NAME):$(shell cat src/main/resources/version)
	docker push $(REPO_NAME)/$(APP_NAME):latest

# deletes dangling images
clean:
	docker rmi $(DANGLING_IMS) -f