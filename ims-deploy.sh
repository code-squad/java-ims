#!/bin/bash

REPOSITORY_DIR=~/java-ims
TOMCAT_DIR=~/tomcat

pkill -9 -ef 'java -jar java-ims-1.0.0.jar'

cd $REPOSITORY_DIR

git pull
gradle clean build -x test

java -jar $REPOSITORY_DIR/build/libs/java-ims-1.0.0.jar &
