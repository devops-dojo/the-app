#!/bin/bash -x

# This directory is synced by vagrant
cd /provision
ls -alrt
# Are we behind web proxy?
if grep -Fxq "with_proxy: true" vars/default.yml; then
   proxy_host=`awk '/http_proxy_host/ {printf "%s",$2;exit}' vars/default.yml`
   proxy_port=`awk '/http_proxy_port/ {printf "%s",$2;exit}' vars/default.yml`
   export http_proxy=http://${proxy_host}:${proxy_port}
fi

if [ ! -f /usr/bin/ansible-playbook ]
    then
    sudo -E apt-get update
    sudo -E apt-get install -y software-properties-common
    sudo -E apt-key add ansible.key.txt
    sudo -E apt-add-repository -y ppa:ansible/ansible
    sudo -E apt-get update
    sudo -E apt-get install -y --allow-unauthenticated ansible
fi


echo "RUNNING ansible-playbook -c local " $@

ansible-playbook -c local "$@"
