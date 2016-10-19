AppStash
========

ansible all -m ping -i hosts -u vagrant --private-key ~/.vagrant.d/insecure_private_key

ansible-playbook buildserver.yml -i hosts -u vagrant --private-key ~/.vagrant.d/insecure_private_key