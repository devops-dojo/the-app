# Description:
#   Execute service actions (stop/start/restart/status)
#
# Commands:
#   hubot service stop|start|restart|status [all|cart|product|navigation|monolith] [test|pro]
#

module.exports = (robot) ->
  robot.respond /service (start|stop|restart|status)\s*(all|cart|product|navigation|monolith)?\s*(test|pro)?/i, (msg) ->

    all_micro_services= ['cart', 'product', 'navigation']
    command     = msg.match[1] or "undefined"
    service     = msg.match[2] or "undefined"
    environment = msg.match[3] or "pro"

    if command isnt "undefined"
      switch environment
        when 'pro'
          micro_host='app-server-node-4'
          mono_host ='app-server-node-2'
        when 'test'
          micro_host='app-server-node-3'
          mono_host ='app-server-node-1'

      switch service
        when 'undefined'
          msg.send "Please specify a service amongst all, cart, product, navigation or monolith."
        when 'all'
          for service in all_micro_services
            msg.send "Executing #{command} on #{service} on #{micro_host}..."
            runCommand msg, "ssh -o StrictHostKeyChecking=no vagrant@#{micro_host} 'sudo /etc/init.d/#{service} #{command}'"
          msg.send "Executing #{command} for monolith on #{mono_host}..."
          runCommand msg, "ssh -o StrictHostKeyChecking=no vagrant@#{mono_host} 'sudo service tomcat7 #{command}'"
        when 'monolith'
          msg.send "Executing #{command} for monolith on #{mono_host}..."
          runCommand msg, "ssh -o StrictHostKeyChecking=no vagrant@#{mono_host} 'sudo service tomcat7 #{command}'"
        else
          msg.send "Executing #{command} on #{service} on #{micro_host}..."
          runCommand msg, "ssh -o StrictHostKeyChecking=no vagrant@#{micro_host} 'sudo /etc/init.d/#{service} #{command}'"


# Run a shell command
runCommand = (msg, cmd) ->
  @exec = require('child_process').exec
  @exec cmd, (error, stdout, stderr) ->
    if error
      msg.send error
      msg.send stderr
    else
      if !!stdout
        msg.send '```' + stdout + '```'
