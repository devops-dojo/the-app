# ninjabot

Ninjabot is a chat bot for DevOps Dojo.

## install

You need to connect ninjabot to your Slack channel or Flowdock flow.

- For Slack: get a Slack bot token at: https://my.slack.com/services/new/bot
- For Flowdock: get a personal API token at: https://www.flowdock.com/account/tokens
- From build server: `cd /var/lib/jenkins/ninjabot`
- `cp .env.template .env`
- Fill in token in `HUBOT_SLACK_TOKEN` or `HUBOT_FLOWDOCK_API_TOKEN` environment variables
- restart hubot: `sudo service hubot restart`
