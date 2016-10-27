#!/bin/bash
echo "Copy Jenkins Configuration"
cp -f /var/lib/jenkins/config.xml /provision/roles/jenkins/files/config.xml
cp -f /var/lib/jenkins/hudson.tasks.Maven.xml /provision/roles/jenkins/files/hudson.tasks.Maven.xml
cp -f /var/lib/jenkins/jenkins.mvn.GlobalMavenConfig.xml /provision/roles/jenkins/files/jenkins.mvn.GlobalMavenConfig.xml
cp -f /var/lib/jenkins/org.jenkinsci.plugins.xvfb.Xvfb.xml /provision/roles/jenkins/files/org.jenkinsci.plugins.xvfb.Xvfb.xml
cp -f /var/lib/jenkins/org.jenkins.ci.plugins.xframe_filter.XFrameFilterPageDecorator.xml /provision/roles/jenkins/files/org.jenkins.ci.plugins.xframe_filter.XFrameFilterPageDecorator.xml

echo "Download Jenkins Cli"
wget http://localhost:8080/jnlpJars/jenkins-cli.jar -O /tmp/jenkins-cli.jar

echo "Save Jenkins Jobs"
java -jar /tmp/jenkins-cli.jar -s http://localhost:8080 list-jobs &> /provision/roles/jenkins/files/jobs.txt
for BUILD in $(cat /provision/roles/jenkins/files/jobs.txt)
do
	java -jar /tmp/jenkins-cli.jar -s http://localhost:8080 get-job $BUILD &> /provision/roles/jenkins/files/$BUILD.xml
done
echo "All Jenkins Jobs are saved"
