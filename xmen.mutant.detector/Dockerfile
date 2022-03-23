FROM adoptopenjdk:11-jre-hotspot
MAINTAINER brian.castillo.h@gmail.com
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} xmen.mutant.detector-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/xmen.mutant.detector-0.0.1-SNAPSHOT.jar"]