FROM docker.yatang.cn/sy/centos6.8:1.7
MAINTAINER suyang@yatang.cn
RUN ln -sf /usr/share/zoneinfo/Asia/Chongqing  /etc/localtime
RUN mkdir -p /opt/dubbo
RUN echo "172.30.10.224 sit.zk.com" >> /etc/hosts
ADD ./target/*-release.zip /opt/dubbo
RUN cd /opt/dubbo/ && unzip ./*-release.zip
RUN chmod 755 -R /opt/dubbo/sc-bpm-dubbo/bin

ADD ./run.sh /opt
RUN cd /opt/ && chmod 755 run.sh
CMD ["/opt/run.sh", "run"]
