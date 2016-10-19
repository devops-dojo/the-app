#!/bin/sh
set -v

dcos kubectl create -f redis.yml
dcos kubectl create -f redis-service.yml
dcos kubectl create -f cart.yml
dcos kubectl create -f cart-service.yml
dcos kubectl create -f mongodb.yml
dcos kubectl create -f mongodb-service.yml
dcos kubectl create -f product.yml
dcos kubectl create -f product-service.yml
dcos kubectl create -f navigation.yml
dcos kubectl create -f navigation-service.yml
dcos kubectl create -f shop.yml
dcos kubectl create -f shop-service.yml
dcos kubectl create -f catalog.yml
dcos kubectl create -f catalog-service.yml
