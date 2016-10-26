#!/bin/sh
kubectl replace --force -f redis.yml
kubectl replace --force -f redis-service.yml
kubectl replace --force -f cart.yml
kubectl replace --force -f cart-service.yml
kubectl replace --force -f mongodb.yml
kubectl replace --force -f mongodb-service.yml
kubectl replace --force -f product.yml
kubectl replace --force -f product-service.yml
kubectl replace --force -f navigation.yml
kubectl replace --force -f navigation-service.yml
kubectl replace --force -f shop.yml
kubectl replace --force -f shop-service.yml
kubectl replace --force -f catalog.yml
kubectl replace --force -f catalog-service.yml
