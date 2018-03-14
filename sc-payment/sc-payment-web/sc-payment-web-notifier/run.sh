#!/bin/bash
ip=`hostname -i`
echo "LANG=en_US.UTF-8" >> /etc/profile
source /etc/profile
cd /opt/www/*/WEB-INF/classes

#sed -i 's/^dubbo.zookeeper.host/#&/g' server.properties
#sed -i '/#dubbo.zookeeper.host/a\dubbo.zookeeper.host=zookeeper://sit.zk.com:2181?backup=sit.zk.com:2182,sit.zk.com:2183' server.properties

#sed -i 's/^dubbo.monitor/#&/g' server.properties
#sed -i '/#dubbo.monitor/a\dubbo.monitor=sitk8s.zk.com:7070' server.properties

#sed -i 's/^sso.redis.host/#&/g' server.properties
#sed -i '/#sso.redis.host/a\sso.redis.host=redis-sit' server.properties

#sed -i 's/^redis.host/#&/g' server.properties
#sed -i '/#redis.host/a\redis.host=redis-sit' server.properties

#sed -i 's/^imageServer.domain/#&/g' server.properties
#sed -i '/#imageServer.domain/a\imageServer.domain=http://images-sit' server.properties

/gotty -a 0.0.0.0 -p 8000 -w tmux 1>access.log 2>error.log &

/opt/tomcat/*/bin/startup.sh
tail -F /opt/tomcat/*/logs/catalina.out
