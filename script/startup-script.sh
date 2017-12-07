#!/bin/bash
if [ ! -d "~/market-client" ]; then
  git clone https://github.com/anrewkaka/market-client.git -b 'develop' ~/market-client
fi

if [ ! -d "~/market-client-autoconfig" ]; then
  git clone https://github.com/anrewkaka/market-client-autoconfig.git -b 'develop' ~/market-client-autoconfig
fi

if [ ! -d "~/chart" ]; then
  git clone https://github.com/anrewkaka/chart.git -b 'develop' ~/chart
fi

# publish market-client library
cd ~/market-client
git reset --hard
git clean -f
git checkout develop
git pull
chmod +x gradlew
./gradlew clean publishToMavenLocal

# publish market-client library
cd ~/market-client-autoconfig
git reset --hard
git clean -f
git checkout develop
git pull
chmod +x gradlew
./gradlew clean publishToMavenLocal

# build application
cd ~/chart
git reset --hard
git clean -f
git checkout develop
git pull
chmod +x gradlew
./gradlew clean build

export COMPOSE_FILE=./docker/docker-compose.yml
docker-compose down
docker-compose up -d
