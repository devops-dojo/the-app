#!/bin/sh

# Provision Azure pre-requisites:
# - Resource group 'DojoLabs'
# - A VNet in resource group, peered with the 
#   provisioning machine's resource group VNet

. ../.env

# Delete previous peering in Dojo resource group
azure network vnet peering delete Dojo Dojo-vnet ToDojoLabsVNet
# Deploy resource group
azure group create DojoLabs centralus
# Create VNet1 in new resource group
azure group deployment create --resource-group DojoLabs \
     --parameters-file params.json \
     --template-file ./VNet1_to_VNet2.json
# Create peering from VNet2 to VNet1
azure group deployment create --resource-group Dojo \
     --parameters-file params.json \
     --template-file ./VNet2_to_VNet1.json
