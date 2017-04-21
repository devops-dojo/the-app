#!/bin/bash

# This script is used by Nagios to post alerts to Hubot
# Then Hubot can decide to notify Slack channels
#
# All variables that start with ICINGA_ are provided by Nagios as
# environment variables when an notification is generated.
# A list of the env variables is available here:
#   http://nagios.sourceforge.net/docs/3_0/macrolist.html
#

#Modify these variables for your environment
MY_ICINGA_HOSTNAME="monitoring.microservice.io"
HUBOT_HOOK="http://ci-node:8888/publish"
HUBOT_ADAPTER=slack

#Set the message icon based on Nagios service state
if [ "$ICINGA_SERVICESTATE" = "CRITICAL" ]
then
    ICON=":exclamation:"
    COLOR="#D5573B"
elif [ "$ICINGA_SERVICESTATE" = "WARNING" ]
then
    ICON=":warning:"
    COLOR="#FFB20F"
elif [ "$ICINGA_SERVICESTATE" = "OK" ]
then
    ICON=":white_check_mark:"
    COLOR="#78BC61"
elif [ "$ICINGA_SERVICESTATE" = "UNKNOWN" ]
then
    ICON=":question:"
    COLOR="#2E86AB"
else
    ICON=":white_medium_square:"
    COLOR="#2E86AB"
fi

#Send message to Hubot pubsub

if [ "$HUBOT_ADAPTER" = "slack" ]
then
curl -H "Content-Type: application/json" -X POST -d '{"event":"nagios","data":"'\
'{\"text\":\"'"${ICON} ${ICINGA_SERVICEDISPLAYNAME}"' is in '"${ICINGA_SERVICESTATE}"' condition. More details <http://monitoring.microservice.io/cgi-bin/icinga/status.cgi?host='"${ICINGA_HOSTNAME}"'|here>\",'\
'\"attachments\":'\
'[{ \"title\": \"Details\", \"fields\":[{\"title\":\"Host\",\"value\":\"'${ICINGA_HOSTNAME}'\",\"short\":\"true\"},'\
'{\"title\":\"Service\",\"value\":\"'"${ICINGA_SERVICEDISPLAYNAME}"'\",\"short\":\"true\"}'\
'], \"text\": \"'"${ICINGA_SERVICEOUTPUT}"'\", \"color\":\"'"${COLOR}"'\" }'\
']'\
'}"}' \
${HUBOT_HOOK}
else
curl -H "Content-Type: application/json" -X POST -d '{"event":"nagios","data":"'"${ICON} ${ICINGA_SERVICEDISPLAYNAME}"' is in '"${ICINGA_SERVICESTATE}"' condition. More details <http://monitoring.microservice.io/cgi-bin/icinga/status.cgi?host='"${ICINGA_HOSTNAME}"'>" }' \
${HUBOT_HOOK}
fi
