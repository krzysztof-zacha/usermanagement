FROM openjdk:8u191-jdk-alpine3.9

COPY target/user-managemet-*-SNAPSHOT.jar app.jar
CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]