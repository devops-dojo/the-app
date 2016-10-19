#!/usr/bin/env bash
export DEPLOYMENT_NAME=$(curl -s http://192.168.99.100:8080/api/v1/deployments | gunzip - | jq '.[0].name' | sed -e 's/^"//'  -e 's/"$//')
echo "DEPLOYMENT_NAME=$DEPLOYMENT_NAME"

curl -v -X PUT --data-binary @shop-ab-test.yml -H "Content-Type: application/x-yaml" http://192.168.99.100:8080/api/v1/deployments/$DEPLOYMENT_NAME | gunzip -