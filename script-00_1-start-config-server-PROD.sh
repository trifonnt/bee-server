#!/bin/bash

export <%= dasherizedBaseName %>_KEY=`cat /root/.ssh/id_rsa-<%= dasherizedBaseName %>`
docker-compose -f src/main/docker/jhipster-registry.yml up -d