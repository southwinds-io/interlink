export REPO_NAME=registry.gitlab.com/southwinds/images
export APP_NAME=interlink
docker build -t $REPO_NAME/$APP_NAME-snapshot:$(cat src/main/resources/version) .
docker tag $REPO_NAME/$APP_NAME-snapshot:$(cat src/main/resources/version) $REPO_NAME/$APP_NAME-snapshot:latest
