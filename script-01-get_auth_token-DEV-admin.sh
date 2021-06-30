#!/bin/bash

token="$(curl -s curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"username": "admin", "password": "admin", "rememberMe": true}' 'http://localhost:8080/api/authenticate' | python -c "import sys, json; print json.load(sys.stdin)['id_token']")\""

echo $token > .token.txt
