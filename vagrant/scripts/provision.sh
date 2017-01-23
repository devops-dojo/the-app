#!/bin/bash -x

# Refresh local copy if requested
if [ $1 = "-local" ]; then
  if [ ! -d "~/the-app" ]; then
    git clone https://github.com/devops-dojo/the-app.git ~/the-app
  fi

  cd ~/the-app
  # Force reset local GIT repo to match Github
  git fetch --all
  git reset --hard origin/master
  sudo rsync -aHAXvh --update vagrant/provision /
  shift
fi

# This directory is synced by vagrant
cd /provision
ls -alrt

# Are we behind web proxy?
if grep -Fxq "with_proxy: true" vars/default.yml; then
   proxy_host=`awk '/http_proxy_host/ {printf "%s",$2;exit}' vars/default.yml`
   proxy_port=`awk '/http_proxy_port/ {printf "%s",$2;exit}' vars/default.yml`
   export http_proxy=http://${proxy_host}:${proxy_port}
   export https_proxy=http://${proxy_host}:${proxy_port}
fi

# Install or update ansible
sudo -E apt-get update
sudo -E apt-get install -y software-properties-common
sudo -E apt-key add ansible.key.txt
sudo -E apt-add-repository -y ppa:ansible/ansible
sudo -E apt-get update
sudo -E apt-get install -y --allow-unauthenticated ansible

echo "RUNNING ansible-playbook -c local --inventory-file=hosts --extra-vars='ansible_ssh_user=vagrant' --user=vagrant " $@

ansible-playbook -c local --inventory-file=hosts --extra-vars='ansible_ssh_user=vagrant' --user=vagrant "$@"
