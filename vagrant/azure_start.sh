#!/bin/sh

. ./.env

vagrant up --provider=azure --no-parallel 2>&1 | ts | tee -a vagrant.log
