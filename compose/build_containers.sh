#!/bin/sh

../gradlew -p ../monolithic/ui packageToContainer
../gradlew -p ../microservice/frontend/catalog packageToContainer
../gradlew -p ../microservice/service/cart distDocker
../gradlew -p ../microservice/service/product distDocker
../gradlew -p ../microservice/service/product-go packageToContainer
../gradlew -p ../microservice/service/navigation distDocker
