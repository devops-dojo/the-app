#!/bin/sh
dcos kubectl delete rc,pods,service --all --namespace=default
