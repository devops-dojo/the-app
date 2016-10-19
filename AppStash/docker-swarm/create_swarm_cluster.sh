#!/bin/sh
set -e

docker-machine create -d virtualbox local
eval "$(docker-machine env local)"
CLUSTER_ID=$(docker run --rm swarm create)
echo "Cluster ID: $CLUSTER_ID"

docker-machine create \
    -d virtualbox \
    --swarm \
    --swarm-master \
    --swarm-discovery token://$CLUSTER_ID \
    swarm-master

docker-machine create \
    -d virtualbox \
    --swarm \
    --swarm-discovery token://$CLUSTER_ID \
    swarm-agent-00
docker-machine create \
    -d virtualbox \
    --swarm \
    --swarm-discovery token://$CLUSTER_ID \
    swarm-agent-01
docker-machine create \
    -d virtualbox \
    --swarm \
    --swarm-discovery token://$CLUSTER_ID \
    swarm-agent-02

eval $(docker-machine env --swarm swarm-master)
docker info
