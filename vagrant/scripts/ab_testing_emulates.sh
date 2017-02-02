#/bin/bash

let iCount=0
while [ $iCount -lt 80 ]
do
  curl http://shop.microservice.io/partials/cart.html
  let iCount++
done
