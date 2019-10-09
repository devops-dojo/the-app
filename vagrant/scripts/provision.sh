#!/bin/bash -x

# Local provisioning mode (host is Ansible controller)
if [ $1 = "--local" ]; then
  if [ ! -d "~/the-app" ]; then
    git clone https://github.com/devops-dojo/the-app.git ~/the-app
  fi

  cd ~/the-app
  # Force reset local GIT repo to match Github
  git fetch --all
  git reset --hard origin/master

  # Exclude global variables from rsync, but create if it doesn't exist
  if [ ! -f "/provision/vars/default.yml" ]; then
    cp vagrant/provision/vars/default.yml /provision/vars/default.yml
  fi

  # Stick to branch in vars/default.yml
  github_branch=`awk '/github_branch/ {printf "%s",$2;exit}' /provision/vars/default.yml`
  
  # Clean local tags if they changed on Github
  if [ github_branch != "master" ]; then
    github_tag=`echo ${github_branch} | awk -F "/" '/tags/ {print $3}'`
    git tag -d ${github_tag}
    git fetch origin --tags
  fi
  
  git checkout ${github_branch}

  sudo rsync -aHAXvh --update --exclude 'vagrant/provision/vars/default.yml' vagrant/provision /
  shift
fi

# This directory is synced by vagrant or copied with above code
cd /provision
ls -alrt

# Are we behind web proxy?
if grep -Fxq "with_proxy: true" vars/default.yml; then
   proxy_host=`awk '/http_proxy_host/ {printf "%s",$2;exit}' vars/default.yml`
   proxy_port=`awk '/http_proxy_port/ {printf "%s",$2;exit}' vars/default.yml`
   export http_proxy=http://${proxy_host}:${proxy_port}
   export https_proxy=http://${proxy_host}:${proxy_port}
fi

# Install or update ansible if not there
if ! command -v ansible >/dev/null 2>&1; then
  sudo -E apt-key add ansible.key.txt
  sudo -E apt-add-repository -y ppa:ansible/ansible
  sudo -E apt-get update
  sudo -E apt-get install -y --allow-unauthenticated ansible software-properties-common
fi

echo "RUNNING ansible-playbook -c local --inventory-file=hosts --extra-vars='ansible_ssh_user=vagrant' --user=vagrant " $@

ansible-playbook -c local --inventory-file=hosts --extra-vars='ansible_ssh_user=vagrant' --user=vagrant "$@"
