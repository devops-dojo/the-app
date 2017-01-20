# Description:
#   Add chaos: randomly kill/stop services in production, during business hours
#
# Commands:
#   hubot chaos stop|start - start/stop chaos monkey
#

CHAOS_MIN_TIME = 1
CHAOS_MAX_TIME = 5
NODE = "app-server-node-4"

# List of commands to create chaos
commands = ["sudo kill -9 \\\$(ps aux | grep '[j]ava' | grep 'io.github.zutherb.appstash.shop.service.cart.Boot' | awk '{print \\\$2}')",
            "sudo kill -9 \\\$(ps aux | grep '[j]ava' | grep 'io.github.zutherb.appstash.shop.service.navigation.Boot' | awk '{print \\\$2}')",
            "sudo kill -9 \\\$(ps aux | grep '[j]ava' | grep 'io.github.zutherb.appstash.shop.service.product.Boot' | awk '{print \\\$2}')"]

module.exports = (robot) ->
  chaosIntervalId = null

  robot.respond /chaos (start|stop)/i, (msg) ->
    command = msg.match[1] or "undefined"

    if command isnt "undefined"
      switch command
        when 'start'
          randomInterval = randomMinutes(CHAOS_MAX_TIME, CHAOS_MIN_TIME)
          msg.send ":monkey_face: said \"_Chaos will hit every #{randomInterval / 60 / 1000} minutes!_\""
          if chaosIntervalId
            chaos(msg)
          chaosIntervalId = setInterval () ->
            chaos(msg)
          , randomInterval

        when 'stop'
          if chaosIntervalId
            msg.send ":monkey_face: said \"_What kills you makes you stronger_\""
            clearInterval(chaosIntervalId)
            chaosIntervalId = null
          else
            msg.send ":monkey_face: said \"_Wasn't me!_\""


chaos = (msg) ->
  random = Math.floor(Math.random() * commands.length)
  runCommand(msg, "ssh -o StrictHostKeyChecking=no vagrant@#{NODE} \"" + commands[random] + "\"")


randomMinutes = (max,min=0) ->
  return Math.floor(Math.random() * (max - min) + min) * 60 * 1000

# Run a shell command
runCommand = (msg, cmd) ->
  @exec = require('child_process').exec
  msg.send ":monkey_face: said \"_Shaking the tree!_\", command: `#{cmd}`"
  @exec cmd, (error, stdout, stderr) ->
    if stdout? && stdout != ''
      msg.send msg, stdout
    if stderr? && stderr != ''
      msg.send msg, stderr
