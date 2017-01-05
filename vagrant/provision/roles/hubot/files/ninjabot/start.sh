#!/bin/sh

. ./.env
./bin/hubot --adapter slack 2>&1 | tee -a hubot.log
