#!/bin/sh
export KUBERNETES_PROVIDER=vagrant
export NUM_NODES=3
wget -q -O - https://get.k8s.io | bash