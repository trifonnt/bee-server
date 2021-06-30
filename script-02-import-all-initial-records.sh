#!/bin/bash

token=`cat .token.txt`
export SERVER_AUTH_HEADER="Bearer $token"

cd shell-scripts/initial

./import-currency.sh
./import-country.sh

./import-externalSystem.sh

cd ../..
