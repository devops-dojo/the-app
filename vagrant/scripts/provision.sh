#!/bin/bash

if [ ! -f /usr/bin/ansible-playbook ]
    then
    sudo -E apt-get install -y software-properties-common
    sudo -E apt-key install ansible.key.txt
    sudo -E apt-add-repository -y ppa:ansible/ansible
    sudo -E apt-get update
    sudo -E apt-get install -y --allow-unauthenticated ansible
fi

# This directory is synced by vagrant
cd /provision

ls -alrt

echo "RUNNING ansible-playbook -c local " $@

ansible-playbook -c local "$@"
