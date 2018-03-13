#!/bin/bash
ip=`hostname -i`
echo "LANG=en_US.UTF-8" >> /etc/profile
source /etc/profile
cd /opt/dubbo/*/conf

#sed -i 's/^dubbo.registry.address/#&/g' dubbo.properties
#sed -i '/#dubbo.registry.address/a\dubbo.registry.address=sit.zk.com:2181,sit.zk.com:2182,sit.zk.com:21831' dubbo.properties

/gotty -a 0.0.0.0 -p 8000 -w tmux 1>access.log 2>error.log &

/opt/dubbo/*/bin/start.sh
tail -F /opt/dubbo/*/logs/error.log
