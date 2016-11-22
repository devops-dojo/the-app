#!/bin/sh
#echo "Download Jenkins Cli"
#wget http://localhost:8080/jnlpJars/jenkins-cli.jar -O /tmp/jenkins-cli.jar

echo "restore Jobs"
for BUILD in $(cat /provision/roles/jenkins/files/jobs.txt)
do
	cat "/provision/roles/jenkins/files/$BUILD.xml" | java -jar /tmp/jenkins-cli.jar -s http://localhost:8080/ create-job "$BUILD"
	if [ "$?" -ne "0" ]; then
	    cat "/provision/roles/jenkins/files/$BUILD.xml" | java -jar /tmp/jenkins-cli.jar -s http://localhost:8080/ update-job "$BUILD"
    fi
done
