#!/bin/sh

. ./.env
./bin/hubot --adapter $HUBOT_ADAPTER 2>&1 | tee -a hubot.log
