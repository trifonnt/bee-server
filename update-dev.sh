#!/bin/bash

cd /home/vagrant/workspace-sts-4.6.2/<%= dasherizedBaseName %>

kill $(jps -l | grep <%= dasherizedBaseName %>- | awk '{print $1}')

git pull

#/opt/maven/3.5.2/bin/mvn clean compile package -Pprod,swagger -DskipTests=true
./mvnw clean compile package -Pprod,swagger -DskipTests=true

java -Xms1024k -Xmx4G -jar ./target/<%= dasherizedBaseName %>-*.jar > trifon-<%= dasherizedBaseName %>.log &
