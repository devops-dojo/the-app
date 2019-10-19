#/bin/bash

if [ "$1" = "test" ]
then
   SERVER="test-shop"
else
    if [ "$1" = "pro" ]
    then
       SERVER="shop"
    else
        echo "This script generates about 500 requests to test or pro systems, specifically for variants/cart*.html
If you want to send them to test, use $0 test
I you want to send them to pro"
    fi
fi

declare -A FEATURES
VERSIONS=(A B C D E F)

function is_version()
{
 data="$1"
 let iVersion=0
 for i in "${FEATURES[@]}"
 do
   if [ "$data" = "$i" ]
   then
      printf ${VERSIONS[$iVersion]}
      return
   fi
   let iVersion++
 done
 FEATURES[$iVersion]="$data"
 printf .${VERSIONS[$iVersion]}
}

let iCount=0
echo "Shooting 500 requests"
while [ $iCount -lt 500 ]
do
  data="$(curl -s http://${SERVER}.microservice.io/partials/cart.html)"
  is_version "$data"
  let iCount++
  sleep 0.1
done
echo "
done"
