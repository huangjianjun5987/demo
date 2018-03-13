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

RUN cd /opt/tomcat && mv apache-tomcat-7.0.75 sc-glink-web

RUN mkdir -p /opt/www/sc-glink-web

RUN cd /opt/tomcat/sc-glink-web/conf && \
	mv server.xml server.xml.bak
	
COPY ./server.xml /opt/tomcat/sc-glink-web/conf/

ADD ./target/*.war /opt/www/sc-glink-web

RUN cd /opt/www/sc-glink-web && jar -xvf *.war

ADD ./run.sh /opt
RUN cd /opt/ && chmod 755 run.sh
CMD ["/opt/run.sh", "run"]
