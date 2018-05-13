- HaProxy sticky session sur header GEO-CLIENT
- avant le login on fait un Get /csrf qui defini le cookie 'GEO-CLIENT' (a partir de cette requette, les suivantes seront redirigées sur le meme noeud)
- 


Netscaler
- download trial 90 days on https://www.citrix.com/lp/try/netscaler-vpx-platinum.html#/login click on NetScaler VPX on VMware (ZIP, 403MB)
- License code receive by email on mon 7th 2018
- run in VBox NSVPX-ESX-12.0-53.13_nc.ovf 


## (pre-requisite) Tweak VM for virtual box
http://www.virtues.it/2016/08/howto-netscaler-vpx-on-virtualbox/

cd /Applications/VirtualBox.app/Contents/Resources/VirtualBoxVM.app/Contents/MacOS/;
VBoxManage setextradata NSVPX-ESX "VBoxInternal/Devices/pcbios/0/Config/DmiBIOSVendor" "Phoenix Technologies LTD";
VBoxManage setextradata NSVPX-ESX "VBoxInternal/Devices/pcbios/0/Config/DmiBIOSVersion" "6.00";
VBoxManage setextradata NSVPX-ESX "VBoxInternal/Devices/pcbios/0/Config/DmiBIOSReleaseDate" "07/31/2013";
VBoxManage setextradata NSVPX-ESX "VBoxInternal/Devices/pcbios/0/Config/DmiBIOSReleaseMajor" 6;
VBoxManage setextradata NSVPX-ESX "VBoxInternal/Devices/pcbios/0/Config/DmiBIOSReleaseMinor" 0;
VBoxManage setextradata NSVPX-ESX "VBoxInternal/Devices/pcbios/0/Config/DmiBIOSFirmwareMajor" 6;
VBoxManage setextradata NSVPX-ESX "VBoxInternal/Devices/pcbios/0/Config/DmiBIOSFirmwareMinor" 0;
VBoxManage setextradata NSVPX-ESX "VBoxInternal/Devices/pcbios/0/Config/DmiSystemVendor" "VMware, Inc.";
VBoxManage setextradata NSVPX-ESX "VBoxInternal/Devices/pcbios/0/Config/DmiSystemProduct" "VMware Virtual Platform";

## Sur la console de la VM:
Netscaler Ipv4 192.168.0.30
Netmask 255.255.255.0
Gateway ipv4 adresss: 192.168.0.1

## Go to Http 192.168.0.30 login nsroot/nsroot
https://www.informatiweb-pro.net/virtualisation/1-citrix/18--citrix-netscaler-gateway-installation-configuration-et-integration-avec-xenapp-xendesktop.html
https://discussions.citrix.com/topic/280194-netscaler-vpx-express-platinum-evaluation-license/
- config SubnetIP 192.168.0.31  (255.255.255.0)
- config VIP: hostname (nsgw.xdz.ddns.net) + dns(192.168.0.35)
- get MAC shell -> lmutil lmhostid 
- license mac 08002773127D sur https://www.citrix.com/account/#/manage-licenses puis DL licence + upload file FID__53e98171_162fd50bdc7_12a8.lic  + reboot
- ssh nsroot@192.168.0.30 -> shell -> ntpdate 91.189.91.157 (mettre la date a jour sinon 22 sep 2017)
- sur la web gui: reboot WARM (sinon perte de la date)


## config du lb netscaler
- lb method TOKEN with rule HTTP.REQ.COOKIE.NAME_VALUE("GEO_CLIENT")
https://docs.citrix.com/en-us/netscaler/11/traffic-management/datastream/configure-token-method-for-datastream.html
lb method TOKEN ou persistence avec RULE ?  la persistence permet de def un timeout
