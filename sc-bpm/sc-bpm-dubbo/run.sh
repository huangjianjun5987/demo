#!/bin/bash
ip=`hostname -i`
cd /opt/dubbo/*/conf

#sed -i 's/^dubbo.registry.address/#&/g' dubbo.properties
#sed -i '/#dubbo.registry.address/a\dubbo.registry.address=sit.zk.com:2181,sit.zk.com:2182,sit.zk.com:2183' dubbo.properties

/opt/dubbo/*/bin/start.sh
tail -F /opt/dubbo/*/logs/error.log
