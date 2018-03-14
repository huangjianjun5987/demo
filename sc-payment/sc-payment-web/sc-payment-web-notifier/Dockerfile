FROM docker.yatang.cn/sy/centos6.8:1.7
#pull down centos image
#FROM centos
#指定本地centos镜像
MAINTAINER suyang@yatang.cn
##同步宿主机时间
RUN ln -sf /usr/share/zoneinfo/Asia/Chongqing  /etc/localtime
RUN mkdir -p /opt/tomcat
##修改项目名称
ADD ./apache-tomcat-7.0.75.tar.gz /opt/tomcat/

RUN cd /opt/tomcat && mv apache-tomcat-7.0.75 sc-payment-web-notifier

RUN mkdir -p /opt/www/sc-payment-web-notifier

RUN cd /opt/tomcat/sc-payment-web-notifier/conf && \
	mv server.xml server.xml.bak
	
COPY ./server.xml /opt/tomcat/sc-payment-web-notifier/conf/

ADD ./target/*.war /opt/www/sc-payment-web-notifier

RUN cd /opt/www/sc-payment-web-notifier && jar -xvf *.war

ADD ./run.sh /opt
RUN cd /opt/ && chmod 755 run.sh
CMD ["/opt/run.sh", "run"]
