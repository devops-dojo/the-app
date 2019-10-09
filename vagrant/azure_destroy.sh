#!/bin/sh


. ./.env

vagrant -f destroy 2>&1 | tee -a vagrant.log
