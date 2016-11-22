#!/bin/bash

if [ ! -f /usr/bin/ansible-playbook ]
    then
    sudo apt-get install software-properties-common
    sudo apt-add-repository ppa:ansible/ansible
    sudo apt-get update
    sudo apt-get install -y ansible
fi

# Clone repository
git clone https://github.com/devops-dojo/the-app.git
cd the-app/vagrant

ls -alrt

echo "RUNNING ansible-playbook -c local " $@

ansible-playbook -c local "$@"
