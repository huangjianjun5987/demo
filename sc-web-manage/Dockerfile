FROM  docker.yatang.cn/sy/centos6.8:1.7
#pull down centos image
#FROM centos
#指定本地centos镜像
MAINTAINER suyang@yatang.cn
##同步宿主机时间
RUN ln -sf /usr/share/zoneinfo/Asia/Chongqing  /etc/localtime
RUN mkdir -p /opt/tomcat
##修改项目名称
ADD ./apache-tomcat-7.0.75.tar.gz /opt/tomcat/
RUN mkdir -p /usr/share/fonts/pdf/
COPY ./WenQuanYiMicroHei.ttf /usr/share/fonts/pdf/
RUN cd /opt/tomcat && mv apache-tomcat-7.0.75 sc-web-manage
RUN mkdir -p /opt/www/sc-web-manage
RUN cd /opt/tomcat/sc-web-manage/conf && \
	mv server.xml server.xml.bak
	
COPY ./server.xml /opt/tomcat/sc-web-manage/conf/

ADD ./target/*.war /opt/www/sc-web-manage

RUN cd /opt/www/sc-web-manage && jar -xvf *.war

COPY ./run.sh /opt
RUN cd /opt/ && chmod 755 run.sh
CMD ["/opt/run.sh", "run"]
