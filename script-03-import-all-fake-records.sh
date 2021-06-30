#!/bin/bash

token=`cat .token.txt`
export SERVER_AUTH_HEADER="Bearer $token"

cd shell-scripts/fake

./import-currency.sh
./import-country.sh

./import-externalSystem.sh

cd ../..
