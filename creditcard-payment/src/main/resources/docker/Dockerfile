FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    MOIP_SLEEP=0 \
    JAVA_OPTS=""

ADD *.war /app.war

EXPOSE 8081
CMD echo "The application will start in ${MOIP_SLEEP}s..." && \
    sleep ${MOIP_SLEEP} && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.war
