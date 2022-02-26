#!/bin/bash

cd "$SSH_PROJECT_FOLDER" || exit
echo " * PULLING NEW IMAGES"
git fetch
echo " * PULL LATEST RELEASE"
git pull
docker-compose pull
docker-compose stop
echo " * CLEANING OLD IMAGES"
docker-compose rm -f
echo " * RESTART CONTAINER WITH LATEST IMAGE"
docker-compose up -d
echo " * PRUNE OLD IMAGES"
docker image prune -af
