#/bin/bash

if [ "$1" = "test" ]
then
   SERVER="test-shop"
else
   SERVER="shop"
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
echo "Shooting 80 requests"
while [ $iCount -lt 80 ]
do
  data="$(curl -s http://${SERVER}.microservice.io/partials/cart.html)"
  is_version "$data"
  let iCount++
  sleep 0.5
done
echo "
done"
