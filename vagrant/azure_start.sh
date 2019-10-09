#!/bin/sh

. ./.env

# Use DNS suffix set by env variable
if [ -z "${LAB_SUFFIX}" ]; then
    LAB_SUFFIX='team1'
fi

LAB_SUFFIX=${LAB_SUFFIX} vagrant up --provider=azure --no-parallel 2>&1 | ts | tee -a vagrant.log
