#!/bin/bash

if [ ! -f /usr/bin/ansible-playbook ]
    then
    apt-get install software-properties-common
    apt-add-repository ppa:ansible/ansible
    apt-get update
    apt-get install -y ansible
fi

# Clone repository
git clone https://github.com/devops-dojo/the-app.git
cd the-app/vagrant/provision

echo "RUNNING ansible-playbook -c local " $@

ansible-playbook -c local "$@"
