#!/bin/bash
ip=`hostname -i`
echo "LANG=en_US.UTF-8" >> /etc/profile
source /etc/profile

yum install mkfontscale fontconfig -y
cd /usr/share/fonts/pdf/ 
mkfontscale
mkfontdir
fc-list :lang=zh
/opt/tomcat/*/bin/startup.sh
tail -F /opt/tomcat/*/logs/catalina.out
