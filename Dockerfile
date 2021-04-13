# Docker for java sei-report

# Base image oracle jdk8
FROM registry.cn-hangzhou.aliyuncs.com/brianchou/openjdk:8-jre-alpine

# Author
LABEL maintainer="brianhsiung@outlook.com"

# Environment 此处需要根据实际情况修改APP_NAME
ENV JAVA_OPTS="" APP_NAME="sei-report"

# Application
ADD /build/libs/$APP_NAME.jar $APP_NAME.jar

# Port
EXPOSE 8080

# Launch the application
ENTRYPOINT ["sh","-c","java -server  -XX:InitialRAMPercentage=75.0 -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC $JAVA_OPTS -jar $APP_NAME.jar --server.servlet.context-path=/$APP_NAME --server.port=8080"]