# Description:
#   Update Hubot from Github
#
# Commands:
#   hubot update - Update hubot with latest from Github
#

module.exports = (robot) ->
  robot.respond /update/i, (msg) ->
    msg.send "Updating myself. Thanks for your patience..."

    @exec = require('child_process').exec
    @exec "wget -nv -O /tmp/hubot.$$.zip https://github.com/devops-dojo/the-app/archive/master.zip && unzip -o /tmp/hubot.$$.zip 'the-app-master/vagrant/provision/roles/hubot/files/ninjabot/*' -d /tmp/hubot.$$ && cp -R /tmp/hubot.$$/the-app-master/vagrant/provision/roles/hubot/files/ninjabot . && rm -fR /tmp/hubot.$$ && rm /tmp/hubot.$$.zip", (error, stdout, stderr) ->
      if stdout? && stdout != ''
        msg.send ":tada: Update done! Restarting myself..."
        setTimeout () ->
          process.exit(0)
        , 5 * 1000
