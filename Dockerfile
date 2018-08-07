FROM tomcat:7.0.90-jre8
COPY target/files-app-0.1.war /usr/local/tomcat/webapps/files-app.war

EXPOSE 8080