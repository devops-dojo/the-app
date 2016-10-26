#!/bin/sh

docker login
docker push zutherb/monolithic-shop
docker push zutherb/catalog-frontend
docker push zutherb/navigation-service
docker push zutherb/product-service
docker push zutherb/product-service:latest-go
docker push zutherb/cart-service