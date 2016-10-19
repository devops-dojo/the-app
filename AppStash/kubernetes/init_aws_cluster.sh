#!/bin/sh
export KUBE_AWS_ZONE=eu-west-1c
export INSTANCE_PREFIX=cc
export MINION_SIZE=t2.medium
export MASTER_SIZE=t2.small
export KUBERNETES_PROVIDER=aws
wget -q -O - https://get.k8s.io | bash