# ninjabot

Ninjabot is a chat bot for DevOps Dojo.

## install

You need to connect ninjabot to your Slack channel.

- Get a Slack bot token at: https://my.slack.com/services/new/bot
- From build server: `cd /var/lib/jenkins/ninjabot`
- `cp .env.template .env`
- Fill in token in `HUBOT_SLACK_TOKEN` environment variables
- restart hubot: `sudo service hubot restart`
