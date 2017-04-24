# Description:
#   Update Hubot from Github
#
# Commands:
#   hubot update me - Update hubot with latest from Github
#   hubot update host <monitoring|cinode|cirepo|db|appserver1|appserver2|appserver3|appserver4>
#

module.exports = (robot) ->
  robot.respond /update me/i, (msg) ->
    user = robot.brain.userForId(msg.envelope.user.id, null)
    unless robot.auth.hasRole(user, "ops")
      msg.send ":grin: Access denied. You must have 'ops' role to use this command"
      return

    msg.send "Updating myself. Thanks for your patience..."

    @exec = require('child_process').exec
    @exec "wget -nv -O /tmp/hubot.$$.zip https://github.com/devops-dojo/the-app/archive/master.zip && unzip -o /tmp/hubot.$$.zip 'the-app-master/vagrant/provision/roles/hubot/files/ninjabot/*' -d /tmp/hubot.$$ && cp -R /tmp/hubot.$$/the-app-master/vagrant/provision/roles/hubot/files/ninjabot /home/hubot && rm -fR /tmp/hubot.$$ && rm /tmp/hubot.$$.zip", (error, stdout, stderr) ->
      if stdout? && stdout != ''
        msg.send ":tada: Update done! Restarting myself..."
        setTimeout () ->
          process.exit(0)
        , 5 * 1000

  robot.respond /update host (monitoring|cinode|cirepo|db|appserver1|appserver2|appserver3|appserver4|appserver5)/i, (msg) ->
    user = robot.brain.userForId(msg.envelope.user.id, null)
    unless robot.auth.hasRole(user, "admin")
      msg.send ":grin: Access denied. You must have 'admin' role to use this command"
      return
    host = msg.match[1]
    switch host
      when 'monitoring'
        server='monitoring-node'
        command="sh ./provision.sh --local --limit=#{server} monitoringserver.yml"
      when 'cinode'
        server='ci-node'
        command="sh ./provision.sh --local --limit=#{server} buildserver.yml"
      when 'cirepo'
        server='ci-repo'
        command="sh ./provision.sh --local --limit=#{server} reposerver.yml"
      when 'db'
        server='mongodb-node'
        command="sh ./provision.sh --local --limit=#{server} databaseserver.yml"
      when 'appserver1'
        server='app-server-node-1'
        command="sh ./provision.sh --local --limit=#{server} monolitic_appserver.yml"
      when 'appserver2'
        server='app-server-node-2'
        command="sh ./provision.sh --local --limit=#{server} monolitic_appserver.yml"
      when 'appserver3'
        server='app-server-node-3'
        command="sh ./provision.sh --local --limit=#{server} micro_appserver.yml"
      when 'appserver4'
        server='app-server-node-4'
        command="sh ./provision.sh --local --limit=#{server} micro_appserver.yml"
      when 'appserver5'
        server='app-server-node-5'
        command="sh ./provision.sh --local --limit=#{server} micro_appserver.yml"

    msg.send "Reprovisioning host with the latest on Github..."

    @exec = require('child_process').exec
    @exec "ssh -o StrictHostKeyChecking=no vagrant@#{server} 'cd the-app && git fetch --all && git reset --hard origin/master && cd vagrant/scripts && #{command} | tee -a /tmp/reprovision.log'", (error, stdout, stderr) ->
      if ! error
        msg.send ":tada: Update done! For details have a look in /tmp/reprovision.log for server #{server}"
      else
        msg.send ":thunder_cloud_and_rain: Damned, it failed!!! \nLook \n#{stderr}\nFor details have a look on #{server} in /tmp/reprovision.log"
