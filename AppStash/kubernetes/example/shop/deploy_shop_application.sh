#!/bin/sh
set -v

kubectl create -f redis.yml
kubectl create -f redis-service.yml
kubectl create -f cart.yml
kubectl create -f cart-service.yml
kubectl create -f mongodb.yml
kubectl create -f mongodb-service.yml
kubectl create -f product.yml
kubectl create -f product-service.yml
kubectl create -f navigation.yml
kubectl create -f navigation-service.yml
kubectl create -f shop.yml
kubectl create -f shop-service.yml
kubectl create -f catalog.yml
kubectl create -f catalog-service.yml
