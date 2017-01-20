# Description:
#   Update Hubot from Github
#
# Commands:
#   hubot update me - Update hubot with latest from Github
#   hubot update host [monitoring|cinode|cirepo|db|appserver1|appserver2|appserver3|appserver4] - reprovision with latest from Github
#

module.exports = (robot) ->
  robot.respond /update me/i, (msg) ->
    msg.send "Updating myself. Thanks for your patience..."

    @exec = require('child_process').exec
    @exec "wget -nv -O /tmp/hubot.$$.zip https://github.com/devops-dojo/the-app/archive/master.zip && unzip -o /tmp/hubot.$$.zip 'the-app-master/vagrant/provision/roles/hubot/files/ninjabot/*' -d /tmp/hubot.$$ && cp -R /tmp/hubot.$$/the-app-master/vagrant/provision/roles/hubot/files/ninjabot /home/hubot && rm -fR /tmp/hubot.$$ && rm /tmp/hubot.$$.zip", (error, stdout, stderr) ->
      if stdout? && stdout != ''
        msg.send ":tada: Update done! Restarting myself..."
        setTimeout () ->
          process.exit(0)
        , 5 * 1000

  robot.respond /update host (monitoring|cinode|cirepo|db|appserver1|appserver2|appserver3|appserver4|)/i, (msg) ->
    host = msg.match[1]
    switch host
      when 'monitoring'
        server='monitoring-node'
        command='sh ./provision.sh --limit=monitoring-node monitoringserver.yml'
      when 'cinode'
        server='ci-node'
        command='sh ./provision.sh --limit=ci-node buildserver.yml'
      when 'cirepo'
        server='ci-repo'
        command='sh ./provision.sh --limit=ci-repo reposerver.yml'
      when 'db'
        server='mongodb-node'
        command='sh ./provision.sh --limit=mongodb-node databaseserver.yml'
      when 'appserver1'
        server='app-server-node-1'
        command='sh ./provision.sh --limit=app-server-node-1 monolitic_appserver.yml'
      when 'appserver2'
        server='app-server-node-2'
        command='sh ./provision.sh --limit=app-server-node-2 monolitic_appserver.yml'
      when 'appserver3'
        server='app-server-node-3'
        command='sh ./provision.sh --limit=app-server-node-3 micro_appserver.yml'
      when 'appserver4'
        server='app-server-node-4'
        command='sh ./provision.sh --limit=app-server-node-4 micro_appserver.yml'

    msg.send "Reprovisioning host #{server} with the latest on Github..."

    spawn = require('child_process').spawn
    ssh = spawn "ssh", ["-o", "StrictHostKeyChecking=no", "vagrant@#{server}", "cd the-app/vagrant/scripts && #{command}"]
    ssh.stdout.on "data", (data) ->
       respond msg, "#{server}", data.toString()
    ssh.stderr.on "data", (data) ->
       respond msg, "#{server}", data.toString()
    ssh.on "exit", (code) ->
      if code is 0
        respond msg, "#{server}", ":tada: command successful!"

# Limit size of output (would like to buffer it too!)
respond = (msg, server, str, wrap = '```') ->
  len = 3000
  _size = Math.ceil(str.length / len)
  _ret = new Array(_size)
  _offset = undefined
  _i = 0
  while _i < _size
    _offset = _i * len
    _ret[_i] = str.substring(_offset, _offset + len)
    _i++
  msg.send({
    attachments: [{
      pretext: "`#{server}`",
      thumb_url: "http://docs.ansible.com/images/logo.png",
      text: "#{wrap}#{_ret[0]}#{wrap}",
      mrkdwn_in: ["text", "pretext"]
    }],
    username: process.env.HUBOT_SLACK_BOTNAME,
    as_user: true,
  });
  unless _ret.length == 1
    x = 1
    setInterval (->
      msg.send({
        attachments: [{
          pretext: "`#{server}`",
          thumb_url: "http://docs.ansible.com/images/logo.png",
          text: "#{wrap}#{_ret[x]}#{wrap}",
          mrkdwn_in: ["text", "pretext"]
        }],
        username: process.env.HUBOT_SLACK_BOTNAME,
        as_user: true,
      });
      if _ret.length == x+1
        clearInterval this
      else
        x++
    ), 5000
