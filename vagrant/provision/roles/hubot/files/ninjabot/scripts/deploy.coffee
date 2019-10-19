# Description:
#  Deploy: deploy latest to test or production
#
# Commands:
#   hubot deploy [test|pro] - deploy latest to test or production. Default is test.
#

JENKINS_URL = "http://ci.microservice.io:8080"
module.exports = (robot) ->
  robot.respond /deploy (test|pro)/i, (msg) ->
    environment = msg.match[1] or "test"

    switch environment
      when 'test'
        msg.send "Deploying latest to :arrow_forward:TEST:arrow_backward: environment. Check status at #{JENKINS_URL}/view/Test%20Deployment/"
        robot.http("#{JENKINS_URL}/job/shop-monolitic-test-deployment/build")
          .post(null) (err, res, body) ->
            if err
              res.send "Encountered an error #{err}"
              return
        robot.http("#{JENKINS_URL}/job/shop-microservice-test-deployment/build")
          .post(null) (err, res, body) ->
            if err
              res.send "Encountered an error #{err}"
              return
      when 'pro'
        if environment is "pro"
          user = robot.brain.userForId(msg.envelope.user.id, null)
          unless robot.auth.hasRole(user, "ops")
            msg.send ":grin: Access denied. You must have 'ops' role to use this command in production"
            return
        msg.send "Deploying latest to :arrow_forward:PRODUCTION:arrow_backward: environment. Check status at #{JENKINS_URL}/view/Production%20Deployment/"
        robot.http("#{JENKINS_URL}/job/shop-monolitic-production-deployment/build")
          .post(null) (err, res, body) ->
            if err
              res.send "Encountered an error #{err}"
              return
        robot.http("#{JENKINS_URL}/job/shop-microservice-production-deployment/build")
          .post(null) (err, res, body) ->
            if err
              res.send "Encountered an error #{err}"
              return
