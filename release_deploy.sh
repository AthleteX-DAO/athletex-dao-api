#!/bin/bash

cd "$SSH_PROJECT_FOLDER" || exit
echo " * PULLING NEW IMAGES"
git fetch
git checkout release/1.0
git pull
docker-compose pull
docker-compose stop
echo " * CLEANING OLD IMAGES"
docker-compose rm -f
docker-compose up --build
