import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin","dojoadmin")
instance.setSecurityRealm(hudsonRealm)
println("== basic-security.groovy -> admin account added.")

def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(Jenkins.ADMINISTER, "admin")
strategy.add(Permission.READ, "anonymous")
instance.setAuthorizationStrategy(strategy)


instance.save()
