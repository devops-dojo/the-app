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
        msg.send "Deploying latest to **test environment**. See [this link](#{JENKINS_URL}/view/Test%20Deployment/) for status"
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
        msg.send "Deploying latest to **production environment**. See [this link](#{JENKINS_URL}/view/Production%20Deployment/) for status"
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
