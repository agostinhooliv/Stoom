# For Java 11, try this
FROM openjdk:15.0.2

# Refer to Maven build -> finalName
ARG JAR_FILE=out/artifacts/stoom_jar/stoom.jar

# cd /opt/app
WORKDIR /opt/app

# cp out/artifacts/stoom_jar/stoom.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]