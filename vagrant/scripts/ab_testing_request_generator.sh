#/bin/bash

if [ "$1" = "test" ]
then
   SERVER="test-shop"
else
   SERVER="shop"
fi

let iCount=0
echo "Shooting 80 requests"
while [ $iCount -lt 80 ]
do
  printf "."
  curl http://${SERVER}.microservice.io/partials/cart.html > /dev/null
  let iCount++
  sleep 0.5
done
