#!/bin/bash
ip=`hostname -i`
echo "LANG=en_US.UTF-8" >> /etc/profile
source /etc/profile

/opt/tomcat/*/bin/startup.sh
tail -F /opt/tomcat/*/logs/catalina.out
