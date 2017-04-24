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

      if (environment is "pro" && (command is "stop" || command is "restart"))
        user = robot.brain.userForId(msg.envelope.user.id, null)
        unless robot.auth.hasRole(user, "ops")
          msg.send ":grin: Access denied. You must have 'ops' role to use this command in production"
          return

      switch service
        when 'undefined'
          msg.send "Please specify a service amongst all, cart, product, navigation or monolith."
        when 'all'
          msg.send "Running on all services..."
          for service in all_micro_services
            runCommand msg, "ssh -o StrictHostKeyChecking=no vagrant@#{micro_host} 'sudo service #{service} #{command}'"
          runCommand msg, "ssh -o StrictHostKeyChecking=no vagrant@#{mono_host} 'sudo service tomcat7 #{command}'"
        when 'monolith'
          msg.send "Executing #{command} for monolith on #{mono_host}..."
          runCommand msg, "ssh -o StrictHostKeyChecking=no vagrant@#{mono_host} 'sudo service tomcat7 #{command}'"
        else
          msg.send "Executing #{command} on #{service} on #{micro_host}..."
          runCommand msg, "ssh -o StrictHostKeyChecking=no vagrant@#{micro_host} 'sudo service #{service} #{command}'"

respond = (msg, str, prefix, wrap = '```') ->
  len = 3000
  _size = Math.ceil(str.length / len)
  _ret = new Array(_size)
  _offset = undefined
  _i = 0
  while _i < _size
    _offset = _i * len
    _ret[_i] = str.substring(_offset, _offset + len)
    _i++
  msg.send "#{prefix}#{wrap}#{_ret[0]}#{wrap}"
  unless _ret.length == 1
    x = 1
    setInterval (->
      msg.send "#{prefix}#{wrap}#{x+1} of #{_ret.length}\n#{_ret[x]}#{wrap}"
      if _ret.length == x+1
        clearInterval this
      else
        x++
    ), 8000

# Run a shell command
runCommand = (msg, cmd) ->
  @exec = require('child_process').exec
  @exec cmd, (error, stdout, stderr) ->
    if stdout? && stdout != ''
      stdout = stdout.replace /-classpath.*\n/, ""
      status = ""
      if stdout.match(/Active: active \(running\)/gi)
        status = ":white_check_mark:"
      else
        status = ":red_circle:"
      respond msg, stdout, status
    if stderr? && stderr != ''
      respond msg, stderr, ":red_circle:"
