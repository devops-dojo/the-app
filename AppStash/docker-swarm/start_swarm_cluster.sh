#!/bin/sh
set -e

docker-machine up local
eval "$(docker-machine env local)"
CLUSTER_ID=$(docker run --rm swarm create)
echo "Cluster ID: $CLUSTER_ID"

eval $(docker-machine env --swarm swarm-master)
docker info
